package com.promptoven.authservice.application.service.utility;

// this utility will implement Diffie-Hellman Key exchanger
// this utility will use on transporting in Password
// this scenario assume as unsafe internet communication devices, like old router or and so on
// in practical in Korea, Naver use password encrypt before client side

import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.KeyAgreement;
import javax.crypto.spec.DHParameterSpec;

public class DHkeyExchanger {
	private KeyPair keyPair;
	private KeyAgreement keyAgreement;
	private static final int KEY_SIZE = 2048;

	public DHkeyExchanger() throws
		NoSuchAlgorithmException,
		InvalidParameterSpecException,
		InvalidAlgorithmParameterException, InvalidKeyException {
		// Initialize key pair generator with specific parameters
		AlgorithmParameterGenerator paramGen = AlgorithmParameterGenerator.getInstance("DH");
		paramGen.init(KEY_SIZE);
		AlgorithmParameters params = paramGen.generateParameters();
		DHParameterSpec dhSpec = params.getParameterSpec(DHParameterSpec.class);

		// Initialize key pair generator with the parameters
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DH");
		keyPairGenerator.initialize(dhSpec);

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
			KeyFactory keyFactory = KeyFactory.getInstance("DH");
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(receivedPublicKey);
			PublicKey theirPublicKey = keyFactory.generatePublic(keySpec);

			keyAgreement.doPhase(theirPublicKey, true);
			return keyAgreement.generateSecret();
		} catch (InvalidKeySpecException e) {
			// Log the error details
			System.err.println("Failed to decode public key: " +
				Base64.getEncoder().encodeToString(receivedPublicKey));
			throw e;
		}
	}
}