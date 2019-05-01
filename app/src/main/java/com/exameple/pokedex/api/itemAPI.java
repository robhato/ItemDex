package com.exameple.pokedex.api;

import com.exameple.pokedex.pokemon.PokemonList;

import retrofit2.http.GET;
import retrofit2.Call;
import retrofit2.http.Query;

public interface itemAPI {
    @GET("number")
    Call<PokemonList> getList(@Query("limit") int limit, @Query("offset") int offset);
}
