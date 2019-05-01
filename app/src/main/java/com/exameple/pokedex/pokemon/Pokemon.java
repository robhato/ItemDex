package com.exameple.pokedex.pokemon;

import java.util.ArrayList;

public class Pokemon {
    private int number;
    private String name;
    private String url;
    private ArrayList<String> effect_entries;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getNumber() {
        String[] partsOfUrl = url.split("/");
        return Integer.parseInt(partsOfUrl[partsOfUrl.length - 1]);
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public ArrayList<String> getEffect_entries() {
        return effect_entries;
    }

    public void setEffect_entries(ArrayList<String> effect_entries) {
        this.effect_entries = effect_entries;
    }
}
