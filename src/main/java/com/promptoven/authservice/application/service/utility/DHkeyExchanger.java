package com.promptoven.authservice.application.service.utility;

import javax.crypto.KeyAgreement;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.DHPublicKeySpec;
import java.security.*;
import java.security.spec.*;
import java.math.BigInteger;
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
        try {
            DHPublicKey publicKey = (DHPublicKey) keyPair.getPublic();
            BigInteger y = publicKey.getY();
            
            // Create DER encoding
            byte[] yBytes = y.toByteArray();
            byte[] pBytes = P.toByteArray();
            
            // Calculate total length
            int totalLength = 0x144;  // Fixed size for 2048-bit key
            
            byte[] encoded = new byte[totalLength];
            int offset = 0;
            
            // SEQUENCE
            encoded[offset++] = 0x30;
            encoded[offset++] = (byte) 0x82;
            encoded[offset++] = 0x01;
            encoded[offset++] = 0x44;
            
            // SEQUENCE
            encoded[offset++] = 0x30;
            encoded[offset++] = (byte) 0x81;
            encoded[offset++] = (byte) 0x9F;
            
            // Object Identifier
            encoded[offset++] = 0x06;
            encoded[offset++] = 0x09;
            encoded[offset++] = 0x2A;
            encoded[offset++] = (byte) 0x86;
            encoded[offset++] = 0x48;
            encoded[offset++] = (byte) 0x86;
            encoded[offset++] = (byte) 0xF7;
            encoded[offset++] = 0x0D;
            encoded[offset++] = 0x01;
            encoded[offset++] = 0x03;
            encoded[offset++] = 0x01;
            
            // SEQUENCE
            encoded[offset++] = 0x30;
            encoded[offset++] = (byte) 0x81;
            encoded[offset++] = (byte) 0x91;
            
            // Prime INTEGER
            encoded[offset++] = 0x02;
            encoded[offset++] = (byte) 0x81;
            encoded[offset++] = (byte) 0x81;
            System.arraycopy(pBytes, 0, encoded, offset, pBytes.length);
            offset += pBytes.length;
            
            // Generator INTEGER
            encoded[offset++] = 0x02;
            encoded[offset++] = 0x01;
            encoded[offset++] = 0x02;
            
            // Public Key BIT STRING
            encoded[offset++] = 0x03;
            encoded[offset++] = (byte) 0x81;
            encoded[offset++] = (byte) 0x81;
            encoded[offset++] = 0x00;  // Leading zero
            System.arraycopy(yBytes, 0, encoded, offset, yBytes.length);
            
            return encoded;
        } catch (Exception e) {
            logger.error("Failed to encode public key", e);
            throw new RuntimeException("Failed to encode public key", e);
        }
    }

    public byte[] generateSharedSecret(byte[] otherPublicKeyBytes) throws GeneralSecurityException {
        try {
            logger.debug("[DH] Received public key bytes (hex): {}", bytesToHex(otherPublicKeyBytes));
            
            if (otherPublicKeyBytes.length < 7) {
                throw new InvalidKeySpecException("Invalid DER encoding");
            }
            
            int offset = 7;
            int yLength = otherPublicKeyBytes[6] & 0xff;
            
            if (offset + yLength > otherPublicKeyBytes.length) {
                throw new InvalidKeySpecException("Invalid key length");
            }
            
            byte[] yBytes = new byte[yLength];
            System.arraycopy(otherPublicKeyBytes, offset, yBytes, 0, yLength);
            BigInteger y = new BigInteger(1, yBytes);
            
            logger.debug("[DH] Extracted Y value (hex): {}", y.toString(16));
            
            DHPublicKeySpec keySpec = new DHPublicKeySpec(y, P, G);
            KeyFactory keyFactory = KeyFactory.getInstance("DH");
            PublicKey otherPublicKey = keyFactory.generatePublic(keySpec);
            
            validatePublicKey((DHPublicKey) otherPublicKey);
            keyAgreement.doPhase(otherPublicKey, true);
            byte[] secret = keyAgreement.generateSecret();
            
            logger.debug("[DH] Generated shared secret length: {}", secret.length);
            logger.debug("[DH] Generated shared secret (hex): {}", bytesToHex(secret));
            
            return secret;
        } catch (Exception e) {
            logger.error("[DH] Failed to generate shared secret", e);
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