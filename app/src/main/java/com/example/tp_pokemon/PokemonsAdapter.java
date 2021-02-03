package com.example.tp_pokemon;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.tp_pokemon.Model.Pokemon;
import com.example.tp_pokemon.Model.PokemonResponse;

import java.util.List;

public class PokemonsAdapter extends RecyclerView.Adapter<PokemonsAdapter.ViewHolder> {

    private final List<PokemonResponse> pokemonList;
    private final Context context;

    public PokemonsAdapter(List<PokemonResponse> pokemonList, Context context) {
        this.pokemonList = pokemonList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        View pokemonView= inflater.inflate(R.layout.item_pokemon,parent,false);
        return new ViewHolder(pokemonView);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonsAdapter.ViewHolder holder, final int position) {
        PokemonResponse pokemon=pokemonList.get(position);

        ImageView imageViewPokemon= holder.imageView;
        Glide.with(context).load("https://cdn.traction.one/pokedex/pokemon/"+pokemon.getNumber()+".png")
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageViewPokemon);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,detail_pokemon.class);
                intent.putExtra("id",pokemonList.get(position).getNumber());
                intent.putExtra("name",pokemonList.get(position).getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.pokemonimg);
        }
    }
}
