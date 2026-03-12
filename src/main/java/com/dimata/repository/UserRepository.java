package com.dimata.repository;

import static com.dimata.gen.Tables.USERS;

import java.util.List;

import org.jooq.DSLContext;

import com.dimata.gen.tables.records.UsersRecord;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped

public class UserRepository {

    @Inject
    DSLContext jooq;

    public List<UsersRecord> getAllUser() {
        return jooq
            .selectFrom(USERS)
            .fetch();
    }
    
}
