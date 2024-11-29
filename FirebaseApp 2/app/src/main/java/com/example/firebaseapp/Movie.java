package com.example.firebaseapp;

import java.io.Serializable;

public class Movie implements Serializable {
    private String id;
    private String title;
    private String studio;
    private String thumbnailUrl;
    private String criticsRating;
    private boolean isFavorite;

    public Movie() {
        // Default constructor required for Firebase
    }

    public Movie(String id, String title, String studio, String thumbnailUrl, String criticsRating, boolean isFavorite) {
        this.id = id;
        this.title = title;
        this.studio = studio;
        this.thumbnailUrl = thumbnailUrl;
        this.criticsRating = criticsRating;
        this.isFavorite = isFavorite;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getStudio() {
        return studio;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getCriticsRating() {
        return criticsRating;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public void setCriticsRating(String criticsRating) {
        this.criticsRating = criticsRating;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
