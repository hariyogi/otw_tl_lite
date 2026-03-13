package com.dimata.util;

import org.jose4j.base64url.internal.apache.commons.codec.binary.Base64;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Util untuk handle private dan public key.
 *
 * @author Hariyogi
 */
public interface KeyUtil {

    static PrivateKey buildRsaPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        var temp = privateKey
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PRIVATE KEY-----", "");
        var encoded = Base64.decodeBase64(temp);
        var privateSpec = new PKCS8EncodedKeySpec(encoded);
        var factory = KeyFactory.getInstance("RSA");
        return factory.generatePrivate(privateSpec);
    }

    static PublicKey buildRsaPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        var temp = publicKey
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PUBLIC KEY-----", "");
        var encoded = Base64.decodeBase64(temp);
        var factory = KeyFactory.getInstance("RSA");
        var keySpec = new X509EncodedKeySpec(encoded);
        return factory.generatePublic(keySpec);
    }

    static KeyPair buildRsaKeyPair(String privateKey, String publicKey) {
        try {
            return new KeyPair(buildRsaPublicKey(publicKey), buildRsaPrivateKey(privateKey));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
}
