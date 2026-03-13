package com.dimata.data.response;

public record LoginResponse(
        String username,
        String email,
        String accessToken,
        String refreshToken
) {
}
