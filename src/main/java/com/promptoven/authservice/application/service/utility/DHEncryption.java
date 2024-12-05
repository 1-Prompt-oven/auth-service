package com.promptoven.authservice.application.service.utility;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public class DHEncryption {
    private static final String ALGORITHM = "AES";
    private static final String CIPHER_TRANSFORM = "AES/CBC/PKCS5Padding";
    private SecretKey secretKey;
    private byte[] iv;

    public DHEncryption(byte[] sharedSecret) throws Exception {
        // Use SHA-256 to derive a proper-length key from the shared secret
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encKeyBytes = digest.digest(sharedSecret);
        this.secretKey = new SecretKeySpec(encKeyBytes, ALGORITHM);
        
        // Generate random IV
        SecureRandom random = new SecureRandom();
        this.iv = new byte[16];
        random.nextBytes(this.iv);
    }

    public String encrypt(String plaintext) throws Exception {
        Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORM);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
        
        byte[] encrypted = cipher.doFinal(plaintext.getBytes());
        // Combine IV and encrypted data
        byte[] combined = new byte[iv.length + encrypted.length];
        System.arraycopy(iv, 0, combined, 0, iv.length);
        System.arraycopy(encrypted, 0, combined, iv.length, encrypted.length);
        
        return Base64.getEncoder().encodeToString(combined);
    }

    public String decrypt(String encryptedText) throws Exception {
        byte[] combined = Base64.getDecoder().decode(encryptedText);
        
        // Extract IV and encrypted data
        byte[] extractedIv = Arrays.copyOfRange(combined, 0, 16);
        byte[] encrypted = Arrays.copyOfRange(combined, 16, combined.length);
        
        Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORM);
        IvParameterSpec ivSpec = new IvParameterSpec(extractedIv);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
        
        byte[] decrypted = cipher.doFinal(encrypted);
        return new String(decrypted);
    }

    // Getter for IV if needed
    public byte[] getIv() {
        return iv.clone(); // Return a clone for security
    }
} 