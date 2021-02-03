package com.example.tp_pokemon;

import android.opengl.GLES20;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tp_pokemon.Model.PokeResult;
import com.example.tp_pokemon.Model.Pokemon;
import com.example.tp_pokemon.Model.PokemonResponse;
import com.example.tp_pokemon.Retrofit.PokedexAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<PokemonResponse> pokelist=new ArrayList<>();
    PokemonsAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
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
        final View view=inflater.inflate(R.layout.fragment_main, container, false);
        String sTitle=getArguments().getString("title");
        int gen=getArguments().getInt("gene");
        recyclerView=view.findViewById(R.id.rvPokemon);

        adapter=new PokemonsAdapter( pokelist,view.getContext());

        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(),3));

        recyclerView.setAdapter(adapter);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PokedexAPI pokedexAPI=retrofit.create(PokedexAPI.class);
        List<Integer> params=new ArrayList<>();
        params=setparams(gen);
        Call<PokeResult> call=pokedexAPI.getPokemons(params.get(0),params.get(1));
        call.enqueue(new Callback<PokeResult>() {
            @Override
            public void onResponse(Call<PokeResult> call, Response<PokeResult> response) {
                if(!response.isSuccessful()){
                    Log.e("resp", String.valueOf(response.code()));
                    return;
                }
                Log.e("resp", "success");
                PokeResult pokemonResult=response.body();
                parseArray(pokemonResult,view);
            }

            @Override
            public void onFailure(Call<PokeResult> call, Throwable t) {

            }

        });
        return view;
    }

    private List<Integer> setparams(int gen) {
        List<Integer> params=new ArrayList<>();
        switch (gen){
            case 1:
                params.add(0);
                params.add(151);
                break;
            case 2:
                params.add(151);
                params.add(100);
                break;
            case 3:
                params.add(251);
                params.add(135);
                break;
            case 4:
                params.add(386);
                params.add(107);
                break;
            case 5:
                params.add(493);
                params.add(156);
                break;
            case 6:
                params.add(649);
                params.add(72);
                break;
            case 7:
                params.add(721);
                params.add(88);
                break;
            case 8:
                params.add(809);
                params.add(89);
                break;
        }
        return params;
    }

    private void parseArray(PokeResult pokemonResult, View view) {
        pokelist.clear();
        List<PokemonResponse> pokemons=pokemonResult.getPokemonList();
        for(int i=0;i<pokemons.size();i++){
            PokemonResponse pokemon=new PokemonResponse();
            pokemon.setName(pokemons.get(i).getName());
            pokemon.setUrl(pokemons.get(i).getUrl());
            pokelist.add(pokemon);
            adapter=new PokemonsAdapter(pokelist,view.getContext());
            recyclerView.setAdapter(adapter);
        }
    }
}