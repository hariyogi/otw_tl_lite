package com.dimata.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

import org.jooq.DSLContext;


import com.dimata.gen.tables.records.OrdersRecord;

import static com.dimata.gen.Tables.ORDERS;


@ApplicationScoped
public class OrderRepository {

    @Inject
    DSLContext jooq;

    public List<OrdersRecord> getAllOrder() {
        return jooq
                .selectFrom(ORDERS)
                .fetch();
    }
    
}
