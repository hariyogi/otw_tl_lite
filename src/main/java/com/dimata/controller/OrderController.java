package com.dimata.controller;

import java.util.List;

import com.dimata.repository.OrderRepository;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/api/v1/order")
public class OrderController {

    @Inject
    OrderRepository orderRepository;

    public record Order(String id, String user_id, String driver_id, String location_id, Enum status ) {}

    @GET
    @Path("/list")

    public List<Order> getOrderName() {
        return orderRepository.getAllOrder()
            .stream()
            .map(ord -> new Order(ord.getId(), ord.getUserId(), ord.getDriverId(), ord.getLocationId(), ord.getStatus()))
            .toList();
    }

    
}
