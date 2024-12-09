package com.promptoven.authservice.application.service.utility;

import javax.crypto.KeyAgreement;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import java.security.*;
import java.security.spec.*;
import java.math.BigInteger;
import java.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DHkeyExchanger {
    private static final Logger logger = LoggerFactory.getLogger(DHkeyExchanger.class);
    private final KeyPair keyPair;
    private final KeyAgreement keyAgreement;
    
    // RFC 3526 MODP Group 14 (2048-bit)
    private static final BigInteger P = new BigInteger(
        "FFFFFFFFFFFFFFFFC90FDAA22168C234C4C6628B80DC1CD1" +
        "29024E088A67CC74020BBEA63B139B22514A08798E3404DD" +
        "EF9519B3CD3A431B302B0A6DF25F14374FE1356D6D51C245" +
        "E485B576625E7EC6F44C42E9A637ED6B0BFF5CB6F406B7ED" +
        "EE386BFB5A899FA5AE9F24117C4B1FE649286651ECE45B3D" +
        "C2007CB8A163BF0598DA48361C55D39A69163FA8FD24CF5F" +
        "83655D23DCA3AD961C62F356208552BB9ED529077096966D" +
        "670C354E4ABC9804F1746C08CA18217C32905E462E36CE3B" +
        "E39E772C180E86039B2783A2EC07A28FB5C55DF06F4C52C9" +
        "DE2BCBF6955817183995497CEA956AE515D2261898FA0510" +
        "15728E5A8AACAA68FFFFFFFFFFFFFFFF", 16);
    
    private static final BigInteger G = BigInteger.valueOf(2);
    private static final int KEY_SIZE = 2048; // Standard key size

    public DHkeyExchanger() throws GeneralSecurityException {
        try {
            // Create DH parameters with exact 2048-bit size
            DHParameterSpec dhParams = new DHParameterSpec(P, G, KEY_SIZE);

            // Generate key pair
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DH");
            keyGen.initialize(dhParams);
            this.keyPair = keyGen.generateKeyPair();

            // Initialize key agreement
            this.keyAgreement = KeyAgreement.getInstance("DH");
            this.keyAgreement.init(keyPair.getPrivate());
            
            logger.debug("DH key exchanger initialized with {} bit prime", KEY_SIZE);
        } catch (Exception e) {
            logger.error("Failed to initialize DH key exchanger", e);
            throw e;
        }
    }

    public byte[] getPublicKey() {
        return keyPair.getPublic().getEncoded();
    }

    public byte[] generateSharedSecret(byte[] otherPublicKeyBytes) throws GeneralSecurityException {
        try {
            // Convert received bytes to public key
            KeyFactory keyFactory = KeyFactory.getInstance("DH");
            
            // Log the received bytes for debugging
            logger.debug("Received public key bytes length: {}", otherPublicKeyBytes.length);
            logger.debug("Received public key bytes (hex): {}", bytesToHex(otherPublicKeyBytes));
            
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(otherPublicKeyBytes);
            PublicKey otherPublicKey = keyFactory.generatePublic(keySpec);

            // Validate the other party's public key
            validatePublicKey((DHPublicKey) otherPublicKey);

            // Generate shared secret
            keyAgreement.doPhase(otherPublicKey, true);
            byte[] secret = keyAgreement.generateSecret();
            
            logger.debug("Generated shared secret length: {}", secret.length);
            logger.debug("Generated shared secret (hex): {}", bytesToHex(secret));
            
            return secret;
        } catch (Exception e) {
            logger.error("Failed to generate shared secret", e);
            throw new GeneralSecurityException("Failed to generate shared secret", e);
        }
    }

    private void validatePublicKey(DHPublicKey publicKey) throws InvalidKeyException {
        // Verify the key uses our parameters
        DHParameterSpec params = publicKey.getParams();
        if (!params.getP().equals(P) || !params.getG().equals(G)) {
            throw new InvalidKeyException("Invalid DH parameters");
        }

        // Verify the public key value is in range
        BigInteger y = publicKey.getY();
        if (y.compareTo(BigInteger.ONE) <= 0 || y.compareTo(P.subtract(BigInteger.ONE)) >= 0) {
            throw new InvalidKeyException("Invalid DH public key value");
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}