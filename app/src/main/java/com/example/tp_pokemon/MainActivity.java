package com.example.tp_pokemon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.tp_pokemon.Model.PokeResult;
import com.example.tp_pokemon.Model.PokemonResponse;
import com.example.tp_pokemon.Retrofit.PokedexAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PokedexAPI pokedexAPI=retrofit.create(PokedexAPI.class);


        Call<PokeResult> call=pokedexAPI.getPokemons(0,898);
        call.enqueue(new Callback<PokeResult>() {
            @Override
            public void onResponse(Call<PokeResult> call, Response<PokeResult> response) {
                if(!response.isSuccessful()){
                    Log.e("resp", String.valueOf(response.code()));
                    return;
                }
                ArrayList<Integer> ids=new ArrayList<>();
                ArrayList<String> names=new ArrayList<>();
                ArrayList<String> urls=new ArrayList<>();
                int i=0;
                PokeResult pokeResult=response.body();
                for(PokemonResponse pokemon:pokeResult.getPokemonList()){
                    ids.add(pokemon.getNumber());
                    names.add(pokemon.getName());
                    urls.add(pokemon.getUrl());
                }
                Intent intent=new Intent(MainActivity.this,HomeActivity.class);
                intent.putExtra("ids",ids);
                intent.putExtra("names",names);
                intent.putExtra("urls",urls);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<PokeResult> call, Throwable t) {

            }

        });
    }
}