package com.example.tp_pokemon.Retrofit;


import com.example.tp_pokemon.Model.PokeResult;
import com.example.tp_pokemon.Model.Pokemon;
import com.example.tp_pokemon.Model.PokemonResponse;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PokedexAPI {
    @GET("pokemon")
    Call<PokeResult> getPokemons(@Query("offset") int offset, @Query("limit") int limit);

    @GET("pokemon/{id}")
    Call<List<Pokemon>> getPokemonById(@Path("id") int pokeId);

    @GET("pokemon/{name}")
    Call<List<Pokemon>> getPokemonByName(@Path("name") String pokeNames);
}
