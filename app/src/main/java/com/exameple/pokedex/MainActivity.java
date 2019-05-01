package com.exameple.pokedex;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import com.exameple.pokedex.api.pokeapi;
import com.exameple.pokedex.pokemon.PokemonList;
import com.exameple.pokedex.pokemon.Pokemon;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListAdapter listAdapt;

    private Retrofit retrofit;
    private static final String TAG = "POKEDEX";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        listAdapt = new ListAdapter();
        recyclerView.setAdapter(listAdapt);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);

        retrofit = new Retrofit.Builder().baseUrl("https://pokeapi.co/api/v2/")
                    .addConverterFactory(GsonConverterFactory.create()).build();
        getData();
    }
    private void getData() {
        pokeapi api = retrofit.create(pokeapi.class);
        Call<PokemonList> request = api.getList();

        request.enqueue(new Callback<PokemonList>() {
            @Override
            public void onResponse(Call<PokemonList> call, Response<PokemonList> response) {
                if (response.isSuccessful()) {
                    PokemonList pokemonList = response.body();
                    ArrayList<Pokemon> pokemon = pokemonList.getResults();

                    listAdapt.morePokemon(pokemon);
                } else {
                    Log.e(TAG, "onResponse" + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<PokemonList> call, Throwable t) {
                Log.e(TAG, " onFailure " + t.getMessage());
            }
        });
    }
}
