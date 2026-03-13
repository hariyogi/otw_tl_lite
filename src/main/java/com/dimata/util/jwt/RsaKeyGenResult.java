package com.dimata.util.jwt;

import java.security.KeyPair;
import java.util.Base64;

public record RsaKeyGenResult(
        String publicKey,
        String privateKey
) {
    public RsaKeyGenResult(KeyPair keyPair) {
        this(
                Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()),
                Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded())
        );
    }

    public String publicKey() {
        return "-----BEGIN PUBLIC KEY-----" + publicKey + "-----END PUBLIC KEY-----";
    }

    public String privateKey() {
        return "-----BEGIN PRIVATE KEY-----" + privateKey + "-----END PRIVATE KEY-----";
    }
}
