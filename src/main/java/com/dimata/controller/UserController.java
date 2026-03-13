package com.dimata.controller;

import com.dimata.data.body.LoginBody;
import com.dimata.data.response.LoginResponse;
import com.dimata.gen.tables.records.UsersRecord;
import com.dimata.repository.UserRepository;
import com.dimata.util.BcryptHashImpl;
import com.dimata.util.jwt.AccessTokenClaims;
import com.dimata.util.jwt.JwtHandler;
import io.quarkus.security.UnauthorizedException;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Path("/api/v1/login")
public class UserController {

    @Inject
    BcryptHashImpl  bcryptHash;
    @Inject
    UserRepository userRepository;
    @Inject
    JwtHandler jwtHandler;

    @POST
    @Path("/email")
    @PermitAll
    public LoginResponse login(@Valid LoginBody body) {
        var user = userRepository.getUserByEmail(body.email());
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        var isPassMatch = bcryptHash.isPasswordMatch(body.password(), user.getPassword());
        if (!isPassMatch) {
            throw new IllegalArgumentException("Wrong password");
        }

        var accToken = buildAccessToken(user);
        return new LoginResponse(
                user.getName(),
                user.getEmail(),
                accToken
        );
    }

    private String buildAccessToken(UsersRecord user) {
        var accClaim = new AccessTokenClaims(
                "https://otw.dimata.com",
                LocalDateTime.now(),
                LocalDateTime.now().plusYears(1),
                UUID.randomUUID().toString(),
                user.getId(),
                List.of(),
                Map.of()
        );

        return jwtHandler.signJwt(accClaim);
    }
}
