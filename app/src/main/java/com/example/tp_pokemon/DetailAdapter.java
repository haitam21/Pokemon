package com.example.tp_pokemon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp_pokemon.Model.Pokemon;

import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder>  {
    private final List<Pokemon> pokemonList;
    private final Context context;

    public DetailAdapter(List<Pokemon> pokemonList, Context context) {
        this.pokemonList = pokemonList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        View pokemonView= inflater.inflate(R.layout.item_details,parent,false);
        return new DetailAdapter.ViewHolder(pokemonView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pokemon pokemon=pokemonList.get(position);

        TextView description = holder.description;
        TextView taille = holder.taille;
        TextView poid = holder.poid;
        TextView type = holder.type;

        description.setText(pokemon.getDescription());
        taille.setText(taille.getText()+" "+pokemon.getHeight());
        poid.setText(poid.getText()+" "+pokemon.getWeight());

        String types="";
        for(String pokType : pokemon.getTypes()){
            types+="-"+pokType+"\n";
        }

        type.setText(types);

    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView description;
        TextView taille;
        TextView poid;
        TextView type;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            description=itemView.findViewById(R.id.description);
            taille=itemView.findViewById(R.id.taille);
            poid=itemView.findViewById(R.id.poid);
            type=itemView.findViewById(R.id.types);
        }
    }
}
