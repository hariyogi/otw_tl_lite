package com.dimata.data.body;

import jakarta.validation.constraints.NotBlank;

public record LoginBody(
        @NotBlank String email,
        @NotBlank String password
) {
}
