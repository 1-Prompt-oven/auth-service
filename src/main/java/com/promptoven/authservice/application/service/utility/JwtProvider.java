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

	public static class TokenInfo {
		private final JWTClaimsSet claims;

		private TokenInfo(JWTClaimsSet claims) {
			this.claims = claims;
		}

		public String getRole() {
			try {
				return claims.getStringClaim("role");
			} catch (ParseException e) {
				return null;
			}
		}

		public String getUserId() {
			return claims.getSubject();
		}

		public String getClaim(String claimName) {
			try {
				Object claim = claims.getClaim(claimName);
				return claim != null ? claim.toString() : null;
			} catch (Exception e) {
				return null;
			}
		}

		public Date getExpirationTime() {
			return claims.getExpirationTime();
		}
	}

	/**
	 * Decrypts the token and returns the claims set
	 */
	private JWTClaimsSet decryptToken(String token) throws ParseException, JOSEException {
		EncryptedJWT jwt = EncryptedJWT.parse(token);
		jwt.decrypt(decrypter);
		return jwt.getJWTClaimsSet();
	}

	/**
	 * Validates the basic token claims (issuer, audience, expiration)
	 */
	private boolean validateClaims(JWTClaimsSet claims) {
		try {
			Date now = new Date();
			return claims.getIssuer().equals(JWT_ISSUER) &&
				   claims.getAudience().equals(JWT_AUDIENCE) &&
				   now.before(claims.getExpirationTime()) &&
				   now.after(claims.getNotBeforeTime());
		} catch (Exception e) {
			log.error("Token validation failed: {}", e.getMessage());
			return false;
		}
	}

	/**
	 * Decrypts and validates token, returns TokenInfo if valid
	 */
	public TokenInfo validateAndDecryptToken(String token) {
		try {
			JWTClaimsSet claims = decryptToken(token);
			return validateClaims(claims) ? new TokenInfo(claims) : null;
		} catch (Exception e) {
			log.error("Token processing failed: {}", e.getMessage());
			return null;
		}
	}

	/**
	 * Only decrypts token and returns claims without validation
	 */
	public TokenInfo decryptTokenOnly(String token) {
		try {
			JWTClaimsSet claims = decryptToken(token);
			return new TokenInfo(claims);
		} catch (Exception e) {
			log.error("Token decryption failed: {}", e.getMessage());
			return null;
		}
	}
}

