package com.exameple.pokedex;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import me.sargunvohra.lib.pokekotlin.client.PokeApi;
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient;
import me.sargunvohra.lib.pokekotlin.model.Item;
import me.sargunvohra.lib.pokekotlin.model.ItemCategory;
import me.sargunvohra.lib.pokekotlin.model.Pokemon;
import retrofit2.Retrofit;


public class ItemInfo extends AppCompatActivity {

    private Retrofit retrofit;
    private static final String TAG = "POKEDEX";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_info);
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Button backButton = (Button) findViewById(R.id.button);
        ImageView imageView = findViewById(R.id.imageView);
        TextView textView = findViewById(R.id.textView);
        TextView textView2 = findViewById(R.id.textView2);
        backButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(ItemInfo.this, MainActivity.class));
            }
        });
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String name = extras.getString("names");
        String img = extras.getString("image");
        int id = extras.getInt("id");
        PokeApi pokeApi = new PokeApiClient();
        Item item = pokeApi.getItem(id);
        textView.setText(name);
        textView2.setText(item.toString());

        Glide.with(this)
                .load(img)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }
}
