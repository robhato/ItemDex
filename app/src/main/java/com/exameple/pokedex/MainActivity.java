package com.exameple.pokedex;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.exameple.pokedex.api.pokeapi;
import com.exameple.pokedex.pokemon.PokemonList;
import com.exameple.pokedex.pokemon.Pokemon;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements ListAdapter.onNoteListener {

    private RecyclerView recyclerView;
    private ListAdapter listAdapt;

    private Retrofit retrofit;
    private static final String TAG = "POKEDEX";

    private int offset;
    private boolean load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.item_info);

        recyclerView = (RecyclerView) findViewById((R.id.not_an_int));
        listAdapt = new ListAdapter(this, this);
        recyclerView.setAdapter(listAdapt);
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if(load) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            Log.i(TAG, " final");

                            load = false;
                            offset += 20;
                            getData(offset);
                        }
                    }
                }
            }
    });
        retrofit = new Retrofit.Builder().baseUrl("https://pokeapi.co/api/v2/")
                    .addConverterFactory(GsonConverterFactory.create()).build();
        load = true;
        offset = 0;
        getData(offset);
    }
    private void getData(int offset) {
        pokeapi api = retrofit.create(pokeapi.class);
        Call<PokemonList> request = api.getList(20, offset);

        request.enqueue(new Callback<PokemonList>() {
            @Override
            public void onResponse(Call<PokemonList> call, Response<PokemonList> response) {
                load = true;
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
                load = true;
                Log.e(TAG, " onFailure " + t.getMessage());
            }
        });
    }

    @Override
    public void onNoteClick(int position) {
        ArrayList<Pokemon> data = listAdapt.data;
        Intent intent = new Intent(this, ItemInfo.class);
        String name = data.get(position).getName();
        String image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/" + data.get(position).getName() +  ".png";
        intent.putExtra("names", name);
        intent.putExtra("image", image);
        startActivity(intent);
    }
}
