package com.dimata.repository;

import com.dimata.data.body.LocationBody;
import com.dimata.data.param.LocationParam;
import com.dimata.gen.tables.records.LocationsRecord;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jooq.DSLContext;

import java.util.List;
import java.util.UUID;

import static com.dimata.gen.Tables.LOCATIONS;

@ApplicationScoped
public class LocationRepository {

    @Inject
    DSLContext jooq;

    public List<LocationsRecord> getAllLocation(LocationParam param) {
        var builder = jooq
                .selectFrom(LOCATIONS);

        if (param.name() != null) {
            builder.where(LOCATIONS.NAME.eq(param.name()));
        }

        return builder.fetch();
    }

    public LocationsRecord createNewLocation(LocationBody body) {
        var record = jooq.newRecord(LOCATIONS);
        record.setId(UUID.randomUUID().toString());
        record.setName(body.name());
        record.setAddress(body.address());

        record.store();

        return record;
    }

    public LocationsRecord updateLocation(String locationId, LocationBody body) {
        var currRecord = jooq.selectFrom(LOCATIONS)
                .where(LOCATIONS.ID.eq(locationId))
                .fetchOne();

        if (currRecord != null) {
            currRecord.setName(body.name());
            currRecord.setAddress(body.address());
            currRecord.store();
            return currRecord;
        } else {
            throw new RuntimeException("location not found");
        }
    }

    public void deleteLocation(String locationId) {
        jooq.deleteFrom(LOCATIONS).where(LOCATIONS.ID.eq(locationId)).execute();
    }
}
