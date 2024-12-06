package com.promptoven.authservice.application.service.utility;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DHEncryption {
    private static final Logger logger = LoggerFactory.getLogger(DHEncryption.class);
    private static final String ALGORITHM = "AES";
    private static final String CIPHER_TRANSFORM = "AES/CBC/PKCS5Padding";
    private final SecretKey secretKey;

    public DHEncryption(byte[] sharedSecret) throws Exception {
        logger.debug("[DH] Initializing with shared secret length: {}", sharedSecret.length);
        logger.debug("[DH] Shared secret (hex): {}", bytesToHex(sharedSecret));
        
        // Use SHA-256 to derive a proper-length key from the shared secret
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encKeyBytes = digest.digest(sharedSecret);
        logger.debug("[DH] Derived key length: {}", encKeyBytes.length);
        logger.debug("[DH] Derived key (hex): {}", bytesToHex(encKeyBytes));
        
        this.secretKey = new SecretKeySpec(encKeyBytes, ALGORITHM);
    }

    public String encrypt(String plaintext) throws Exception {
        logger.debug("[Encrypt] Input text length: {}", plaintext.length());
        logger.debug("[Encrypt] Input text bytes: {}", bytesToHex(plaintext.getBytes()));
        
        // Generate random IV
        SecureRandom random = new SecureRandom();
        byte[] iv = new byte[16];
        random.nextBytes(iv);
        logger.debug("[Encrypt] Generated IV (hex): {}", bytesToHex(iv));
        
        Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORM);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
        
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
        byte[] extractedIv = Arrays.copyOfRange(combined, 0, 16);
        byte[] encrypted = Arrays.copyOfRange(combined, 16, combined.length);
        logger.debug("[Decrypt] Extracted IV (hex): {}", bytesToHex(extractedIv));
        logger.debug("[Decrypt] Encrypted data length: {}", encrypted.length);
        logger.debug("[Decrypt] Encrypted data (hex): {}", bytesToHex(encrypted));
        
        Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORM);
        IvParameterSpec ivSpec = new IvParameterSpec(extractedIv);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
        
        byte[] decrypted = cipher.doFinal(encrypted);
        logger.debug("[Decrypt] Decrypted length: {}", decrypted.length);
        logger.debug("[Decrypt] Decrypted (hex): {}", bytesToHex(decrypted));
        
        String result = new String(decrypted);
        logger.debug("[Decrypt] Final text length: {}", result.length());
        return result;
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
} 