package com.promptoven.authservice.application.service.utility;

// this utility will implement Diffie-Hellman Key exchanger
// this utility will use on transporting in Password
// this scenario assume as unsafe internet communication devices, like old router or and so on
// in practical in Korea, Naver use password encrypt before client side

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.KeyAgreement;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.DHPublicKeySpec;

public class DHkeyExchanger {
	private KeyPair keyPair;
	private KeyAgreement keyAgreement;

	// Fixed DH parameters (these values should be secure for DH)
	private static final String P_HEX = 
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
		"15728E5A8AACAA68FFFFFFFFFFFFFFFF";
	private static final String G_HEX = "02";

	public DHkeyExchanger() throws
		NoSuchAlgorithmException,
		InvalidAlgorithmParameterException,
		InvalidKeyException {
		// Convert hex parameters to BigInteger
		BigInteger p = new BigInteger(P_HEX, 16);
		BigInteger g = new BigInteger(G_HEX, 16);
		
		// Create DH parameters
		DHParameterSpec dhParams = new DHParameterSpec(p, g);
		
		// Initialize key pair generator with the parameters
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DH");
		keyPairGenerator.initialize(dhParams);

		// Generate the key pair
		this.keyPair = keyPairGenerator.generateKeyPair();

		// Initialize the key agreement
		this.keyAgreement = KeyAgreement.getInstance("DH");
		this.keyAgreement.init(keyPair.getPrivate());
	}

	// Get public key to share with the other party
	public byte[] getPublicKey() {
		return keyPair.getPublic().getEncoded();
	}

	// Generate shared secret using other party's public key
	public byte[] generateSharedSecret(byte[] receivedPublicKey)
		throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
		try {
			// Extract the actual key value from DER encoding
			byte[] keyBytes = extractKeyBytesFromDER(receivedPublicKey);
			
			KeyFactory keyFactory = KeyFactory.getInstance("DH");
			DHPublicKeySpec keySpec = new DHPublicKeySpec(
				new BigInteger(1, keyBytes), // Force positive BigInteger
				new BigInteger(P_HEX, 16),   // Use our known P value
				new BigInteger(G_HEX, 16)    // Use our known G value
			);
			
			PublicKey theirPublicKey = keyFactory.generatePublic(keySpec);
			keyAgreement.doPhase(theirPublicKey, true);
			return keyAgreement.generateSecret();
		} catch (Exception e) {
			System.err.println("Failed to decode public key: " +
				Base64.getEncoder().encodeToString(receivedPublicKey));
			e.printStackTrace();
			throw e;
		}
	}

	private byte[] extractKeyBytesFromDER(byte[] derEncoded) {
		try {
			// Skip DER encoding header (usually starts after byte 11)
			// This is a simplified DER parser
			int offset = 11;
			while (offset < derEncoded.length && derEncoded[offset] == 0) {
				offset++;
			}
			byte[] result = new byte[derEncoded.length - offset];
			System.arraycopy(derEncoded, offset, result, 0, result.length);
			return result;
		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid DER encoding", e);
		}
	}
}