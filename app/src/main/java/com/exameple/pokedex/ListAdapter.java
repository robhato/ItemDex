package com.exameple.pokedex;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.exameple.pokedex.pokemon.Pokemon;

import java.util.ArrayList;
import android.view.View;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    public ArrayList<Pokemon> data;
    private Context getCon;
    private onNoteListener mOnNoteListener;

    public ListAdapter(Context context, onNoteListener onNoteListener) {
        this.mOnNoteListener = onNoteListener;
        this.getCon = context;
        data = new ArrayList<>();
    }

    public void morePokemon(ArrayList<Pokemon> pokemon) {
        data.addAll(pokemon);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_pokemon, viewGroup, false);
        return new ViewHolder(view, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Pokemon individual = data.get(i);
        viewHolder.textView.setText(individual.getName());
        Glide.with(getCon)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/" + individual.getName() +  ".png")
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageView;
        private TextView textView;
        onNoteListener onNoteListener;
        public ViewHolder(View itemView, onNoteListener onNoteListener) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById((R.id.textView));
            this.onNoteListener = onNoteListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface onNoteListener{
        void onNoteClick(int position);
    }
}
