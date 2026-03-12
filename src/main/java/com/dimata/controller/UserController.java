package com.dimata.controller;

import java.util.List;

import com.dimata.repository.UserRepository;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/api/v1/user")
public class UserController {

    @Inject
    UserRepository userRepository;

    public record Users(String id, String name, String email, Enum role) {}

    @GET
    @Path("/list")

    public List<Users> getUserName() {
        return userRepository.getAllUser()
            .stream()
            .map(usr -> new Users(usr.getId(), usr.getName(), usr.getEmail(), usr.getRole()))
            .toList();
    }

    
}
