package com.dimata.controller;

import com.dimata.client.PokeApiClient;
import com.dimata.client.Pokemon;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.RestPath;

@Path("/api/v1/pokemon")
public class PokemonController {

    @Inject
    @RestClient
    PokeApiClient pokeApiClient;

    @GET
    @Path("/name/{name}")
    @PermitAll
    public Pokemon getPokemon(@RestPath String name) {
        return pokeApiClient.getPokemon(name);
    }
}
