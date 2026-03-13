package com.dimata.controller;

import com.dimata.data.body.LoginBody;
import com.dimata.data.body.RefreshTokenBody;
import com.dimata.data.response.LoginResponse;
import com.dimata.gen.tables.records.UsersRecord;
import com.dimata.repository.UserRepository;
import com.dimata.util.BcryptHashImpl;
import com.dimata.util.jwt.AccessTokenClaims;
import com.dimata.util.jwt.JwtHandler;
import com.dimata.util.jwt.RefreshTokenClaims;
import io.quarkus.security.UnauthorizedException;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Path("/api/v1/login")
public class UserController {

    private final Map<String, UsersRecord> ACC_TOKEN = new HashMap<>();

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

        var tokenId = UUID.randomUUID().toString();
        var accToken = buildAccessToken(user, tokenId);
        ACC_TOKEN.put(tokenId, user);
        var refreshToken = buildRefreshToken(tokenId);
        return new LoginResponse(
                user.getName(),
                user.getEmail(),
                accToken,
                refreshToken
        );
    }

    @POST
    @Path("/refresh")
    @PermitAll
    public String getNewAccessToken(RefreshTokenBody refreshToken) {
        var claims = jwtHandler.readJws(refreshToken.refreshToken());
        var id = claims.getId();
        var user = ACC_TOKEN.get(id);

        if (user == null) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        return buildAccessToken(user, id);
    }

    private String buildAccessToken(UsersRecord user, String tokenId) {
        var accClaim = new AccessTokenClaims(
                "https://otw.dimata.com",
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(5),
                tokenId,
                user.getId(),
                List.of(),
                Map.of()
        );

        return jwtHandler.signJwt(accClaim);
    }

    private String buildRefreshToken(String tokenId) {
        var refreshToken = new RefreshTokenClaims(
                LocalDateTime.now(),
                LocalDateTime.now().plusYears(1),
                tokenId
        );

        return jwtHandler.signJwt(refreshToken);
    }
}
