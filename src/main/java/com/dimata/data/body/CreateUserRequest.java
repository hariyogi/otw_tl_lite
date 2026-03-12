package com.dimata.data.body;

import io.smallrye.common.constraint.NotNull;
import com.dimata.gen.enums.UsersRole;
public record CreateUserRequest(

        String id,
        String name,
        String email,
        String password,
        UsersRole role
) {

}
