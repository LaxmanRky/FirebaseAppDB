package com.example.firebaseapp;

public class Movie {
    public String id, title, studio, thumbnailUrl, criticsRating;
    public boolean isFavorite;

    public Movie() {
        // Default constructor required for calls to DataSnapshot.getValue(Movie.class)
    }

    public Movie(String id, String title, String studio, String thumbnailUrl, String criticsRating, boolean isFavorite) {
        this.id = id;
        this.title = title;
        this.studio = studio;
        this.thumbnailUrl = thumbnailUrl;
        this.criticsRating = criticsRating;
        this.isFavorite = isFavorite;
    }
}
