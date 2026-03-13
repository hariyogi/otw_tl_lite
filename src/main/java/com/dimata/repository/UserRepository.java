package com.dimata.repository;

import com.dimata.gen.Tables;
import com.dimata.gen.tables.records.UsersRecord;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jooq.DSLContext;

import static com.dimata.gen.Tables.USERS;

@ApplicationScoped
public class UserRepository {

    @Inject
    DSLContext jooq;

    public UsersRecord getUserByEmail(String email) {
        return jooq.selectFrom(USERS)
                .where(USERS.EMAIL.eq(email))
                .fetchOne();
    }
}
