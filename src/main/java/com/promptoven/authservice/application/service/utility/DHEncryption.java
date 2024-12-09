package com.promptoven.authservice.application.service.utility;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DHEncryption {
    private static final Logger logger = LoggerFactory.getLogger(DHEncryption.class);
    private static final String ALGORITHM = "AES";
    private static final String CIPHER_TRANSFORM = "AES/GCM/NoPadding";
    private static final int GCM_TAG_LENGTH = 128;
    private static final int GCM_IV_LENGTH = 12;
    
    private final SecretKey secretKey;

    public DHEncryption(byte[] sharedSecret) throws Exception {
        // Use SHA-256 to derive a proper-length key
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] keyBytes = digest.digest(sharedSecret);
        this.secretKey = new SecretKeySpec(keyBytes, ALGORITHM);
        
        logger.debug("Encryption initialized with key length: {} bits", keyBytes.length * 8);
    }

    public String encrypt(String plaintext) throws Exception {
        // Generate random nonce
        byte[] iv = new byte[GCM_IV_LENGTH];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);

        // Initialize cipher
        Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORM);
        GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec);

        // Encrypt
        byte[] encrypted = cipher.doFinal(plaintext.getBytes());
        logger.debug("[Encrypt] Encrypted data length: {}", encrypted.length);
        logger.debug("[Encrypt] Encrypted data (hex): {}", bytesToHex(encrypted));

        // Combine IV and encrypted data
        byte[] combined = new byte[iv.length + encrypted.length];
        System.arraycopy(iv, 0, combined, 0, iv.length);
        System.arraycopy(encrypted, 0, combined, iv.length, encrypted.length);
        logger.debug("[Encrypt] Combined length: {}", combined.length);
        
        String result = Base64.getEncoder().encodeToString(combined);
        logger.debug("[Encrypt] Final base64 length: {}", result.length());
        logger.debug("[Encrypt] Final base64: {}", result);
        return result;
    }

    public String decrypt(String encryptedText) throws Exception {
        logger.debug("[Decrypt] Input base64 length: {}", encryptedText.length());
        logger.debug("[Decrypt] Input base64: {}", encryptedText);
        
        byte[] combined = Base64.getDecoder().decode(encryptedText);
        logger.debug("[Decrypt] Decoded combined length: {}", combined.length);
        
        // Extract IV and encrypted data
        byte[] extractedIv = Arrays.copyOfRange(combined, 0, GCM_IV_LENGTH);
        byte[] encrypted = Arrays.copyOfRange(combined, GCM_IV_LENGTH, combined.length);
        logger.debug("[Decrypt] Extracted IV (hex): {}", bytesToHex(extractedIv));
        logger.debug("[Decrypt] Encrypted data length: {}", encrypted.length);
        logger.debug("[Decrypt] Encrypted data (hex): {}", bytesToHex(encrypted));
        
        // Initialize cipher for decryption
        Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORM);
        GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, extractedIv);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, spec);
        
        try {
            byte[] decrypted = cipher.doFinal(encrypted);
            logger.debug("[Decrypt] Decrypted length: {}", decrypted.length);
            logger.debug("[Decrypt] Decrypted (hex): {}", bytesToHex(decrypted));
            
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (AEADBadTagException e) {
            logger.error("[Decrypt] Authentication failed: {}", e.getMessage());
            throw new SecurityException("Password decryption failed: authentication tag mismatch");
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