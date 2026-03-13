package com.dimata.util.jwt;


import com.dimata.util.TimeUtil;
import io.jsonwebtoken.JwtBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Akses token dengan Microprofile JWT spec, gunakan claims ini agar mudah intregasi dengan
 * quarkus JWT authentication.
 * <br>
 * Ingpo lebih lanjut.
 * <a href="https://download.eclipse.org/microprofile/microprofile-jwt-auth-2.1/microprofile-jwt-auth-spec-2.1.html#_recommendations_for_interoperability">
 *     Microprofile JWT
 * </a>
 * @param issuer Yang membuat token
 * @param issuedAt Tanggal token dibuat
 * @param expiredAt Kapan token kadarluarsa
 * @param jwtId Id token, untuk refresh token
 * @param subject User pemilik token,  userInfoId jika user, serviceId jika service
 * @param roles role dari subject
 *
 * @author Hariyogi
 */
public record AccessTokenClaims(
        String issuer,
        LocalDateTime issuedAt,
        LocalDateTime expiredAt,
        String jwtId,
        String subject,
        List<String> roles,
        Map<String, ?> anotherClaims
) implements JwtAppendable {

    @Override
    public JwtBuilder appendToBuilder(JwtBuilder builder) {
        return builder
                .issuer(issuer)
                .expiration(TimeUtil.toDate(expiredAt))
                .issuedAt(TimeUtil.toDate(issuedAt))
                .id(jwtId)
                .subject(subject)
                .claim("unp", subject)
                .claim("groups", roles)
                .claims(Optional.ofNullable(anotherClaims).orElse(Map.of()));
    }

    public Long diffTime() {
        var issuedMillis = TimeUtil.toDate(issuedAt).getTime();
        var expiredMillis = TimeUtil.toDate(expiredAt).getTime();

        return expiredMillis - issuedMillis;
    }
}
