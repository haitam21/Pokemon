package com.example.tp_pokemon;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tp_pokemon.Model.Family;
import com.example.tp_pokemon.Model.Pokemon;
import com.example.tp_pokemon.Model.PokemonResponse;
import com.example.tp_pokemon.Retrofit.PokedexAPI;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PokemonDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PokemonDetailFragment extends Fragment {
    RecyclerView recyclerViewPok, recyclerView;
    ArrayList<PokemonResponse> pokelist = new ArrayList<>();
    ArrayList<Pokemon> pokelistDet = new ArrayList<>();
    DetailAdapter adapter;
    PokemonsAdapter adapterPok;
    Family family = new Family();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PokemonDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PokemonDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PokemonDetailFragment newInstance(String param1, String param2) {
        PokemonDetailFragment fragment = new PokemonDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_pokemon_detail, container, false);
        String sTitle = getArguments().getString("title");
        String name = getArguments().getString("name");
        int id = getArguments().getInt("id");
        recyclerView = view.findViewById(R.id.rvPokemonDetails);

        adapter = new DetailAdapter(pokelistDet, view.getContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        recyclerView.setAdapter(adapter);
        recyclerViewPok = view.findViewById(R.id.rvPokemonEvo);
        adapterPok = new PokemonsAdapter(pokelist, view.getContext());
        recyclerViewPok.setLayoutManager(new GridLayoutManager(view.getContext(), 3));
        recyclerViewPok.setAdapter(adapterPok);

        if (sTitle == "Details") {
            getDetails(id);
        } else {
            getEvolution(name, id, view);
        }
        return view;
    }

    private void getEvolution(final String name, int id, final View view) {
        final ArrayList<String> evoList = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.glitch.me/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PokedexAPI pokedexAPI = retrofit.create(PokedexAPI.class);
        Call<List<Pokemon>> call = pokedexAPI.getPokemonById(id);
        call.enqueue(new Callback<List<Pokemon>>() {
            @Override
            public void onResponse(Call<List<Pokemon>> call, Response<List<Pokemon>> response) {
                if (!response.isSuccessful()) {
                    Log.e("noSuc", String.valueOf(response.code()));
                    return;
                }
                Log.e("resp", String.valueOf(response.code()));
                List<Pokemon> pokemons = response.body();
                parseArrayEvo(pokemons, name, view);
            }

            @Override
            public void onFailure(Call<List<Pokemon>> call, Throwable t) {

            }

        });
    }

    private void parseArrayEvo(List<Pokemon> pokemons, String name, final View view) {
        final ArrayList<PokemonResponse> pokemonResponses = new ArrayList<>();
        pokelist.clear();
        for (Pokemon pokemon : pokemons) {
            family = pokemon.getFamily();
        }

        for (int i = 0; i < family.getEvolutionLine().size(); i++) {
            if (!((name.toLowerCase()).equals(family.getEvolutionLine().get(i).toLowerCase()))) {
                Log.e("originName", name);
                Log.e("second", family.getEvolutionLine().get(i));
                final PokemonResponse pokemon = new PokemonResponse();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://pokeapi.glitch.me/v1/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                PokedexAPI pokedexAPI = retrofit.create(PokedexAPI.class);
                String nameEvo = family.getEvolutionLine().get(i);
                Call<List<Pokemon>> call = pokedexAPI.getPokemonByName(nameEvo);
                call.enqueue(new Callback<List<Pokemon>>() {
                    @Override
                    public void onResponse(Call<List<Pokemon>> call, Response<List<Pokemon>> response) {
                        if (!response.isSuccessful()) {
                            Log.e("noSuc", String.valueOf(response.code()));
                            return;
                        }
                        Log.e("resp", String.valueOf(response.code()));
                        List<Pokemon> pokemons = response.body();
                        pokemon.setApi_used(Integer.parseInt(pokemons.get(0).getNumber()));
                        pokemon.setName(pokemons.get(0).getName());
                        pokemon.setUrl(pokemons.get(0).getSprite());

                        Log.e("ImgUrl", pokemons.get(0).getSprite());
                        pokelist.add(pokemon);
                        adapterPok = new PokemonsAdapter(pokelist, view.getContext());
                        recyclerViewPok.setAdapter(adapterPok);
                    }

                    @Override
                    public void onFailure(Call<List<Pokemon>> call, Throwable t) {

                    }

                });

            }
        }
    }

    private void getDetails(int id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.glitch.me/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PokedexAPI pokedexAPI = retrofit.create(PokedexAPI.class);
        Call<List<Pokemon>> call = pokedexAPI.getPokemonById(id);
        call.enqueue(new Callback<List<Pokemon>>() {
            @Override
            public void onResponse(Call<List<Pokemon>> call, Response<List<Pokemon>> response) {
                if (!response.isSuccessful()) {
                    Log.e("resp", String.valueOf(response.code()));
                    return;
                }
                Log.e("resp", String.valueOf(response.body().get(0).getName()));
                List<Pokemon> pokemons = response.body();
                parseArrayDet(pokemons);
            }

            @Override
            public void onFailure(Call<List<Pokemon>> call, Throwable t) {

            }

        });
    }

    private void parseArrayDet(List<Pokemon> pokemons) {
        pokelistDet.clear();
        Activity activity = getActivity();
        CardView cardView;
        TabLayout tabLayout;
        cardView = (CardView) activity.findViewById(R.id.cardView);
        tabLayout = (TabLayout) activity.findViewById(R.id.tab_det);
        changeBackColor(pokemons.get(0).getTypes().get(0), cardView, tabLayout);
        for (int i = 0; i < pokemons.size(); i++) {
            Pokemon pokemon = new Pokemon();
            pokemon.setWeight(pokemons.get(i).getWeight());
            pokemon.setHeight(pokemons.get(i).getHeight());
            pokemon.setTypes(pokemons.get(i).getTypes());
            pokemon.setDescription(pokemons.get(i).getDescription());
            family = pokemons.get(i).getFamily();
            pokelistDet.add(pokemon);
            recyclerView.setAdapter(adapter);
        }
    }

    @SuppressLint("ResourceAsColor")
    private void changeBackColor(String s, CardView cardView, TabLayout tabLayout) {
        switch (s) {
            case "Fire":
                cardView.setCardBackgroundColor(Color.RED);
                tabLayout.setBackgroundColor(Color.RED);
                break;
            case "Grass":
                cardView.setCardBackgroundColor(Color.GREEN);
                tabLayout.setBackgroundColor(Color.GREEN);
                break;
            case "Poison":
                cardView.setCardBackgroundColor(Color.rgb(229, 28, 216));
                tabLayout.setBackgroundColor(Color.rgb(229, 28, 216));
                break;
            case "Ice":
                cardView.setCardBackgroundColor(Color.rgb(6, 214, 229));
                tabLayout.setBackgroundColor(Color.rgb(6, 214, 229));
                break;
            case "Electric":
                cardView.setCardBackgroundColor(Color.YELLOW);
                tabLayout.setBackgroundColor(Color.YELLOW);
                break;
            case "Normal":
                cardView.setCardBackgroundColor(Color.LTGRAY);
                tabLayout.setBackgroundColor(Color.LTGRAY);
                break;
            case "Ground":
                cardView.setCardBackgroundColor(Color.rgb(153, 76, 0));
                tabLayout.setBackgroundColor(Color.rgb(153, 76, 0));
                break;
            case "Fairy":
                cardView.setCardBackgroundColor(Color.rgb(255, 153, 255));
                tabLayout.setBackgroundColor(Color.rgb(255, 153, 255));
                break;
            case "Bug":
                cardView.setCardBackgroundColor(Color.rgb(204, 255, 204));
                tabLayout.setBackgroundColor(Color.rgb(204, 255, 204));
                break;
            case "Water":
                cardView.setCardBackgroundColor(Color.BLUE);
                tabLayout.setBackgroundColor(Color.BLUE);
                break;
            case "Psychic":
                cardView.setCardBackgroundColor(Color.rgb(153, 153, 0));
                tabLayout.setBackgroundColor(Color.rgb(153, 153, 0));
                break;
            case "Fighting":
                cardView.setCardBackgroundColor(Color.rgb(0, 102, 102));
                tabLayout.setBackgroundColor(Color.rgb(0, 102, 102));
                break;
            case "Ghost":
                cardView.setCardBackgroundColor(Color.rgb(224, 224, 224));
                tabLayout.setBackgroundColor(Color.rgb(224, 224, 224));
                break;
            case "Dragon":
                cardView.setCardBackgroundColor(Color.rgb(153, 0, 0));
                tabLayout.setBackgroundColor(Color.rgb(153, 0, 0));
                break;
            case "Shadow":
                cardView.setCardBackgroundColor(Color.rgb(52, 131, 110));
                tabLayout.setBackgroundColor(Color.rgb(52, 131, 110));
                break;
            case "Dark":
                cardView.setCardBackgroundColor(Color.GRAY);
                tabLayout.setBackgroundColor(Color.GRAY);
                break;
            case "Rock":
                cardView.setCardBackgroundColor(Color.rgb(131, 99, 52));
                tabLayout.setBackgroundColor(Color.rgb(131, 99, 52));
                break;
            case "Unkown":
                cardView.setCardBackgroundColor(Color.rgb(255, 204, 204));
                tabLayout.setBackgroundColor(Color.rgb(255, 204, 204));
                break;
            case "Flying":
                cardView.setCardBackgroundColor(Color.rgb(186, 216, 246));
                tabLayout.setBackgroundColor(Color.rgb(186, 216, 246));
                break;
            case "Steel":
                cardView.setCardBackgroundColor(Color.DKGRAY);
                tabLayout.setBackgroundColor(Color.DKGRAY);
                break;


        }
    }
}