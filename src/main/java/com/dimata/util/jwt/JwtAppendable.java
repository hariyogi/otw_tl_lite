package com.dimata.util.jwt;

import io.jsonwebtoken.JwtBuilder;

/**
 * Object ini bisa di append di JWT builder
 */
public interface JwtAppendable {

    /**
     * Tambahkan klaim pada builder. Audience dan issuer sudah terinput pada builder.
     * Jika ingin mengganti itu, bisa di set ulang.
     *
     * @param builder jwtBuilder
     * @return builder ini lagi.
     */
    JwtBuilder appendToBuilder(JwtBuilder builder);
}
