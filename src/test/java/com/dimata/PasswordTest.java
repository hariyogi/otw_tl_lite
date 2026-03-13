package com.dimata;

import com.dimata.util.BcryptHashImpl;
import io.quarkus.logging.Log;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class PasswordTest {

    @Inject
    BcryptHashImpl  bcryptHash;

    @Test
    void newPassword() {
        var password = "123456";
        Log.info(bcryptHash.hashPasword(password));
    }
}
