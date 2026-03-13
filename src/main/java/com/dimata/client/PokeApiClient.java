package com.dimata.client;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.RestPath;

@RegisterRestClient(baseUri = "https://pokeapi.co/api/v2/")
public interface PokeApiClient {

    @GET
    @Path("/pokemon/{name}")
    public Pokemon getPokemon(@RestPath String name);
}
