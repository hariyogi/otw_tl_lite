package com.dimata.repository;

import com.dimata.data.body.CreateUserRequest;
import com.dimata.gen.tables.records.UsersRecord;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jooq.DSLContext;

import java.util.List;
import java.util.UUID;

import static com.dimata.gen.Tables.USERS;

@ApplicationScoped
public class UserRepository {

    @Inject
    DSLContext jooq;

    public List<UsersRecord> getAllUser(){
        return jooq
                .selectFrom(USERS)
                .fetch();
    }

    public UsersRecord getById(String userId){
        return  jooq
                .selectFrom(USERS)
                .where(USERS.ID.eq(userId))
                .fetchOne();
    }


    public UsersRecord createUser(CreateUserRequest request){
        var record = jooq.newRecord(USERS);
        record.setId(UUID.randomUUID().toString());
        record.setName(request.name());
        record.setEmail(request.email());
        record.setPassword(request.password());
        record.setRole(request.role());

        record.store();
        return record;
    }

    public UsersRecord updateUser(String userId, CreateUserRequest update){
        var currRecord = jooq.selectFrom(USERS)
                .where(USERS.ID.eq(userId))
                .fetchOne();

        if(currRecord != null){
            currRecord.setName(update.name());
            currRecord.setEmail(update.email());
            currRecord.setPassword(update.password());
            currRecord.setRole(update.role());
            currRecord.store();

            return currRecord;
        }
        else {
            throw new RuntimeException("user not found");
        }
    }


    public void deleteUser(String userId){
        jooq.deleteFrom(USERS).where(USERS.ID.eq(userId)).execute();
    }
}