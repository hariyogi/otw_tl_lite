package com.dimata.controller;

import com.dimata.data.body.CreateUserRequest;
import com.dimata.data.response.UserResponse;
import com.dimata.repository.UserRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import org.jboss.resteasy.reactive.RestPath;

import java.util.List;

@Path("/api/v1/user")
public class UserController {

    @Inject
    UserRepository userRepository;

    @GET
    public List<UserResponse> getAll(){
        return userRepository.getAllUser()
                .stream()
                .map(user -> new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getRole().toString()))
                .toList();

    }

    @GET
    @Path("/{id}")
    public UserResponse getById(@RestPath String id){
        var user = userRepository.getById(id);
        if (user == null){
            throw new RuntimeException("user not found");
        }

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().toString()
        );
    }


    @POST
    @Transactional
    public UserResponse create(CreateUserRequest request){
        var savedUser = userRepository.createUser(request);
        return new UserResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getRole().toString()
        );
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public UserResponse update(@RestPath String id, CreateUserRequest request){
        var updatedUser = userRepository.updateUser(id, request);
        return new UserResponse(
                updatedUser.getId(),
                updatedUser.getName(),
                updatedUser.getEmail(),
                updatedUser.getRole().toString()
        );
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@RestPath String id){
        userRepository.deleteUser(id);
    }
}
