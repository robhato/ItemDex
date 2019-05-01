package com.exameple.pokedex.api;

import com.exameple.pokedex.pokemon.PokemonList;
import retrofit2.Call;
import retrofit2.http.GET;

public interface pokeapi {
    @GET("pokemon")
    Call<PokemonList> getList();
}

