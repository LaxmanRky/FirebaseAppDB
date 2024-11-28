package com.example.firebaseapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private List<Movie> movieList;

    public MovieAdapter(List<Movie> movieList) {
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.titleTextView.setText(movie.title);
        holder.studioTextView.setText(movie.studio);
        holder.criticsRatingTextView.setText(movie.criticsRating);
        holder.favoriteCheckBox.setChecked(movie.isFavorite);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, studioTextView, criticsRatingTextView;
        CheckBox favoriteCheckBox;

        MovieViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            studioTextView = itemView.findViewById(R.id.studioTextView);
            criticsRatingTextView = itemView.findViewById(R.id.criticsRatingTextView);
            favoriteCheckBox = itemView.findViewById(R.id.favoriteCheckBox);
        }
    }
}
