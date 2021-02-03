package com.example.tp_pokemon.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PokeResult {
    @SerializedName("results")
    List<PokemonResponse> pokemonList;

    public List<PokemonResponse> getPokemonList() {
        return pokemonList;
    }

    public void setPokemonList(List<PokemonResponse> pokemonList) {
        this.pokemonList = pokemonList;
    }
}
