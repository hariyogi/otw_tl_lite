package com.dimata.util.jwt;

import com.dimata.util.TimeUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;

import java.time.LocalDateTime;

public record RefreshTokenClaims(
        LocalDateTime issuedAt,
        LocalDateTime expiredAt,
        String jwtId
) implements JwtAppendable {

    public static RefreshTokenClaims fromClaims(Claims claim) {
        if (claim.get("refresh", Boolean.class) == null) {
            throw new IllegalArgumentException("Bukan refresh token");
        }
        return new RefreshTokenClaims(
                TimeUtil.toLocalDateTime(claim.getIssuedAt()),
                TimeUtil.toLocalDateTime(claim.getExpiration()),
                claim.getId()
        );
    }

    @Override
    public JwtBuilder appendToBuilder(JwtBuilder builder) {
        return builder
                .issuedAt(TimeUtil.toDate(issuedAt))
                .expiration(TimeUtil.toDate(expiredAt))
                .id(jwtId)
                .claim("refresh", true);
    }

    public Long diffTime() {
        var issuedMillis = TimeUtil.toDate(issuedAt).getTime();
        var expiredMillis = TimeUtil.toDate(expiredAt).getTime();

        return expiredMillis - issuedMillis;
    }
}
