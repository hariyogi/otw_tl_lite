package com.dimata.data.response;

public record UserResponse(
        String id,
        String name,
        String email,
        String role
) {
}
