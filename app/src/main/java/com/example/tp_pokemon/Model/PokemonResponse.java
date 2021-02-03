package com.example.tp_pokemon.Model;

public class PokemonResponse {

    private String name;
    private String url;
    private int api_used=0;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getApi_used() {
        return api_used;
    }

    public void setApi_used(int api_used) {
        this.api_used = api_used;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getNumber(){
        int id;
        if(this.api_used==0){
            String[] urlParts=url.split("/");
            id=Integer.parseInt(urlParts[urlParts.length-1]);
        }else{
            id=this.api_used;
        }
        return id;
    }
}
