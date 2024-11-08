package com.promptoven.authservice.application.service.utility;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWTClaimsSet;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtProvider {

	private static final String JWT_ISSUER = "Prompt Oven Service development group";
	private static final List<String> JWT_AUDIENCE = List.of("prompt oven service");
	JWEHeader header = new JWEHeader.Builder(JWEAlgorithm.RSA_OAEP_512, EncryptionMethod.A256GCM)
		.contentType("JWT")
		.build();
	@Value("${jwt.expiration.refresh}")
	private long refreshExpiration;
	@Value("${jwt.expiration.access}")
	private long accessTokenExpiration;
	@Autowired
	private JwtSecret jwtSecret;
	private RSAEncrypter encrypter;
	private RSADecrypter decrypter;

	@PostConstruct
	public void init() {
		try {
			RSAPrivateKey privateKey = jwtSecret.getPrivateKey();
			RSAPublicKey publicKey = jwtSecret.getPublicKey();
			this.encrypter = new RSAEncrypter(publicKey);
			this.decrypter = new RSADecrypter(privateKey);
		} catch (Exception e) {
			log.error("Failed to initialize JWT provider", e);
			throw new RuntimeException("Failed to initialize JWT provider", e);
		}
	}

	public String issueRefresh(int requestedExpiration, String userUID) {

		Date now = new Date();

		//Create JWT claims
		JWTClaimsSet jwtClaims = new JWTClaimsSet.Builder()
			.issuer(JWT_ISSUER)
			.subject(userUID)
			.audience(JWT_AUDIENCE)
			.notBeforeTime(now)
			.issueTime(now)
			.expirationTime(new Date(now.getTime() +
				refreshExpiration * requestedExpiration))
			//Token is usable for user request days
			.jwtID(UUID.randomUUID().toString())
			.build();

		EncryptedJWT jwt = new EncryptedJWT(header, jwtClaims);

		try {
			jwt.encrypt(encrypter);
		} catch (JOSEException e) {
			throw new RuntimeException(e);
		}
		//return serialized jwt Token
		return jwt.serialize();
	}

	public String issueRefresh(String userUID) {

		Date now = new Date();
		//Create JWT claims
		JWTClaimsSet jwtClaims = new JWTClaimsSet.Builder()
			.issuer(JWT_ISSUER)
			.subject(userUID)
			.audience(JWT_AUDIENCE)
			.notBeforeTime(now)
			.issueTime(now)
			.expirationTime(new Date(now.getTime() +
				refreshExpiration))
			//Token is usable for user default refresh token life is 1 day
			.jwtID(UUID.randomUUID().toString())
			.build();

		EncryptedJWT jwt = new EncryptedJWT(header, jwtClaims);

		try {
			jwt.encrypt(encrypter);
		} catch (JOSEException e) {
			throw new RuntimeException(e);
		}
		//return serialized jwt Token
		return jwt.serialize();
	}

	public String issueJwt(String userUID, String role) {
		try {
			Date now = new Date();

			// Create JWT claims
			JWTClaimsSet claims = new JWTClaimsSet.Builder()
				.issuer(JWT_ISSUER)
				.subject(userUID)
				.audience(JWT_AUDIENCE)
				.notBeforeTime(now)
				.issueTime(now)
				.expirationTime(new Date(now.getTime() + accessTokenExpiration))
				.jwtID(UUID.randomUUID().toString())
				.claim("role", role)
				.build();

			// Create encrypted JWT
			EncryptedJWT jwt = new EncryptedJWT(header, claims);

			// Encrypt the JWT
			jwt.encrypt(encrypter);

			return jwt.serialize();
		} catch (JOSEException e) {
			log.error("Failed to issue JWT", e);
			throw new RuntimeException("Failed to issue JWT", e);
		}
	}

	// parse serialized token value to token object
	private EncryptedJWT parseAndDecrypt(String token) {
		try {
			EncryptedJWT jwt = EncryptedJWT.parse(token);
			jwt.decrypt(decrypter);
			return jwt;
		} catch (ParseException e) {
			log.error("Failed to parse JWT token", e);
			throw new RuntimeException("Invalid JWT format", e);
		} catch (JOSEException e) {
			log.error("Failed to decrypt JWT token", e);
			throw new RuntimeException("Failed to decrypt JWT", e);
		}
	}

	public boolean validateToken(String token) {
		try {
			EncryptedJWT jwt = parseAndDecrypt(token);
			JWTClaimsSet claims = jwt.getJWTClaimsSet();
			Date now = new Date();

			return claims.getIssuer().equals(JWT_ISSUER) &&
				claims.getAudience().equals(JWT_AUDIENCE) &&
				now.before(claims.getExpirationTime()) &&
				now.after(claims.getNotBeforeTime());

		} catch (Exception e) {
			log.error("Failed to validate token", e);
			return false;
		}
	}

	public String getClaimOfToken(String token, String typeOfClaim) {
		try {
			EncryptedJWT jwt = parseAndDecrypt(token);

			if (!validateToken(token)) {
				throw new RuntimeException("Invalid or expired token");
			}

			JWTClaimsSet claims = jwt.getJWTClaimsSet();
			Object claimValue = claims.getClaim(typeOfClaim);

			return claimValue != null ? claimValue.toString() : null;

		} catch (Exception e) {
			log.error("Failed to get claim from token", e);
			throw new RuntimeException("Failed to get claim from token", e);
		}
	}

	public Date getTokenExpiration(String token) {
		try {
			EncryptedJWT jwt = parseAndDecrypt(token);

			if (!validateToken(token)) {
				throw new RuntimeException("Invalid or expired token");
			}

			return jwt.getJWTClaimsSet().getExpirationTime();

		} catch (Exception e) {
			log.error("Failed to get token expiration", e);
			throw new RuntimeException("Failed to get token expiration", e);
		}
	}
}

