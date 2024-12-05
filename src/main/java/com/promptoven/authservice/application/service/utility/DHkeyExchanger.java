package com.promptoven.authservice.application.service.utility;

// this utility will implement Diffie-Hellman Key exchanger
// this utility will use on transporting in Password
// this scenario assume as unsafe internet communication devices, like old router or and so on
// in practical in Korea, Naver use password encrypt before client side

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.security.KeyFactory;
import java.security.InvalidKeyException;
import javax.crypto.KeyAgreement;
import java.security.spec.InvalidKeySpecException;

public class DHkeyExchanger {
	private KeyPair keyPair;
	private KeyAgreement keyAgreement;
	private static final int KEY_SIZE = 2048;
	
	public DHkeyExchanger() throws NoSuchAlgorithmException {
		// Initialize key pair generator
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DH");
		keyPairGenerator.initialize(KEY_SIZE);
		
		// Generate the key pair
		this.keyPair = keyPairGenerator.generateKeyPair();
		
		// Initialize the key agreement
		this.keyAgreement = KeyAgreement.getInstance("DH");
		try {
			this.keyAgreement.init(keyPair.getPrivate());
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
	}
	
	// Get public key to share with the other party
	public byte[] getPublicKey() {
		return keyPair.getPublic().getEncoded();
	}
	
	// Generate shared secret using other party's public key
	public byte[] generateSharedSecret(byte[] receivedPublicKey) 
			throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
		// Convert received public key bytes to PublicKey object
		KeyFactory keyFactory = KeyFactory.getInstance("DH");
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(receivedPublicKey);
		PublicKey theirPublicKey = keyFactory.generatePublic(keySpec);
		
		// Generate shared secret
		keyAgreement.doPhase(theirPublicKey, true);
		return keyAgreement.generateSecret();
	}
}