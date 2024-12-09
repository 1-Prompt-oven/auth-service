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
    
    // Standard 2048-bit DH parameters from RFC 3526
    private static final byte[] P_BYTES = Base64.getDecoder().decode(
        "/////////////////////////////////////////////////////////////////////"
        + "/////////////////////3///////////////////////AAAAAAAAAAAAAAAAAAAAAA"
        + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABgx"
        + "dHc5LupP51oXpaL0nKR8tuQQZAcsjlFsV/S+N/AC3kwe0gOjqYt5kFkYCdCXNM5r"
        + "qQe4DvZLGEYBWcqU61orGBmOkXC7+0oQzGKwOLZnztFP1AtTI6IZ0olHZGRNvZqF"
        + "P11Y7UAA5VtlcPD3GP1W6NYBC0j3y3BF1yqWHiXn16gqbhrh0+aa7xE="
    );
    private static final byte[] G_BYTES = Base64.getDecoder().decode("Ag=="); // Value 2

    public DHkeyExchanger() throws GeneralSecurityException {
        // Create DH parameters
        DHParameterSpec dhParams = new DHParameterSpec(
            new BigInteger(1, P_BYTES),
            new BigInteger(1, G_BYTES)
        );

        // Generate key pair
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DH");
        keyGen.initialize(dhParams);
        this.keyPair = keyGen.generateKeyPair();

        // Initialize key agreement
        this.keyAgreement = KeyAgreement.getInstance("DH");
        this.keyAgreement.init(keyPair.getPrivate());
        
        logger.debug("DH key exchanger initialized with {} bit prime", dhParams.getP().bitLength());
    }

    public byte[] getPublicKey() {
        return keyPair.getPublic().getEncoded();
    }

    public byte[] generateSharedSecret(byte[] otherPublicKeyBytes) throws GeneralSecurityException {
        try {
            // Convert received bytes to public key
            KeyFactory keyFactory = KeyFactory.getInstance("DH");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(otherPublicKeyBytes);
            PublicKey otherPublicKey = keyFactory.generatePublic(keySpec);

            // Validate the other party's public key
            validatePublicKey((DHPublicKey) otherPublicKey);

            // Generate shared secret
            keyAgreement.doPhase(otherPublicKey, true);
            return keyAgreement.generateSecret();
        } catch (Exception e) {
            logger.error("Failed to generate shared secret", e);
            throw new GeneralSecurityException("Failed to generate shared secret", e);
        }
    }

    private void validatePublicKey(DHPublicKey publicKey) throws InvalidKeyException {
        // Verify the key uses our parameters
        DHParameterSpec params = publicKey.getParams();
        if (!params.getP().equals(new BigInteger(1, P_BYTES)) ||
            !params.getG().equals(new BigInteger(1, G_BYTES))) {
            throw new InvalidKeyException("Invalid DH parameters");
        }

        // Verify the public key value is in range
        BigInteger y = publicKey.getY();
        BigInteger p = params.getP();
        if (y.compareTo(BigInteger.ONE) <= 0 || y.compareTo(p.subtract(BigInteger.ONE)) >= 0) {
            throw new InvalidKeyException("Invalid DH public key value");
        }
    }
}