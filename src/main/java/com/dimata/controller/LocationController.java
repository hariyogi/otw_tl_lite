package com.dimata.controller;

import com.dimata.data.body.LocationBody;
import com.dimata.repository.LocationRepository;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import org.jboss.resteasy.reactive.RestPath;

import java.util.ArrayList;
import java.util.List;

@Path("/api/v1/location")
public class LocationController {

    @Inject
    LocationRepository locationRepository;

    public record Location(String id, String name, String address) {}


    // GET = Mengirim data (tidak ada bodynya)
    // POST = API yang melakukan manipulasi data, lebih banyak untuk membuat data baru atau insert
    // PUT = API yang melakukan manipulasi data, fokus ke update. Data yang ada diupdate
    // DELETE = Menghapus data (tidak ada bodynya)
    // PATCH = Sama dengan PUT, tapi update tidak banyak.

    @GET
    @Path("/list")
    @PermitAll
    public List<Location> getLocationName() {
        return locationRepository.getAllLocation()
                .stream()
                .map(loc -> new Location(loc.getId(), loc.getName(), loc.getAddress()))
                .toList();
    }
    
    @POST
    @Path("/add")
    @Transactional
    @Authenticated
    public String addNewLocation(LocationBody body) {
        var savedLocation = locationRepository.createNewLocation(body);
        return savedLocation.getId();
    }

    @PUT
    @Path("/update/{id}")
    @Transactional
    @RolesAllowed("admin")
    public void updateLocation(LocationBody body, @RestPath String id) {
        locationRepository.updateLocation(id, body);
    }

    @DELETE
    @Path("/delete/{id}")
    @Transactional
    @RolesAllowed("admin")
    public void deleteLocation(@RestPath String id) {
        locationRepository.deleteLocation(id);
    }

}
