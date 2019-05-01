package com.exameple.pokedex;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import retrofit2.Retrofit;


public class ItemInfo extends AppCompatActivity {

    private Retrofit retrofit;
    private static final String TAG = "POKEDEX";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_info);
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
        String url = extras.getString("url");
        textView.setText(name);

        Glide.with(this)
                .load(img)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }
}
