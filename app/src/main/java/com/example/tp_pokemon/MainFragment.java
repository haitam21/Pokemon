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
    ArrayList<Integer> ids=new ArrayList<>();
    ArrayList<String> names=new ArrayList<>();
    ArrayList<String> urls=new ArrayList<>();

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

        ids=getArguments().getIntegerArrayList("ids");
        names=getArguments().getStringArrayList("names");
        urls=getArguments().getStringArrayList("urls");
        recyclerView=view.findViewById(R.id.rvPokemon);
        setparams(gen);
        adapter=new PokemonsAdapter( pokelist,view.getContext());

        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(),3));

        recyclerView.setAdapter(adapter);

        return view;
    }

    private void setparams(int gen) {
        List<Integer> params=new ArrayList<>();
        pokelist.clear();
        switch (gen){
            case 1:
                for(int i=0;i<151;i++){
                    PokemonResponse pokemonResponse=new PokemonResponse();
                    pokemonResponse.setName(names.get(i));
                    pokemonResponse.setUrl(urls.get(i));
                    pokelist.add(pokemonResponse);
                }
                break;
            case 2:
                for(int i=151;i<251;i++){
                    PokemonResponse pokemonResponse=new PokemonResponse();
                    pokemonResponse.setName(names.get(i));
                    pokemonResponse.setUrl(urls.get(i));
                    pokelist.add(pokemonResponse);
                }
                break;
            case 3:
                for(int i=251;i<386;i++){
                    PokemonResponse pokemonResponse=new PokemonResponse();
                    pokemonResponse.setName(names.get(i));
                    pokemonResponse.setUrl(urls.get(i));
                    pokelist.add(pokemonResponse);
                }
                break;
            case 4:
                for(int i=386;i<493;i++){
                    PokemonResponse pokemonResponse=new PokemonResponse();
                    pokemonResponse.setName(names.get(i));
                    pokemonResponse.setUrl(urls.get(i));
                    pokelist.add(pokemonResponse);
                }
                break;
            case 5:
                for(int i=493;i<649;i++){
                    PokemonResponse pokemonResponse=new PokemonResponse();
                    pokemonResponse.setName(names.get(i));
                    pokemonResponse.setUrl(urls.get(i));
                    pokelist.add(pokemonResponse);
                }
                break;
            case 6:
                for(int i=649;i<721;i++){
                    PokemonResponse pokemonResponse=new PokemonResponse();
                    pokemonResponse.setName(names.get(i));
                    pokemonResponse.setUrl(urls.get(i));
                    pokelist.add(pokemonResponse);
                }
                break;
            case 7:
                for(int i=721;i<809;i++){
                    PokemonResponse pokemonResponse=new PokemonResponse();
                    pokemonResponse.setName(names.get(i));
                    pokemonResponse.setUrl(urls.get(i));
                    pokelist.add(pokemonResponse);
                }
                break;
            case 8:
                for(int i=809;i<898;i++){
                    PokemonResponse pokemonResponse=new PokemonResponse();
                    pokemonResponse.setName(names.get(i));
                    pokemonResponse.setUrl(urls.get(i));
                    pokelist.add(pokemonResponse);
                }
                break;
        }
    }
}