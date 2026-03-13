package com.dimata;

import com.dimata.util.jwt.AccessTokenClaims;
import com.dimata.util.jwt.JwtHandler;
import io.quarkus.logging.Log;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@QuarkusTest
public class JwtHandlerTest {

    @Inject
    JwtHandler jwtHandler;

    @Test
    void buildAccessToken() {
        var accessTokenClaim = new AccessTokenClaims(
                "https://otw.dimata.com",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(1),
                UUID.randomUUID().toString(),
                "Panji",
                List.of("admin"),
                Map.of()
        );

        var accessToken = jwtHandler.signJwt(accessTokenClaim);

        Log.info("Access Token : " + accessToken);
    }
}
