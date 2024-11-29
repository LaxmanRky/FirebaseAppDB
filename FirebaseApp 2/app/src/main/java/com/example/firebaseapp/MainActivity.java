package com.example.firebaseapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private EditText movieTitleEditText, movieStudioEditText, criticsRatingEditText, thumbnailUrlEditText;
    private CheckBox favoriteCheckBox;
    private ImageView movieThumbnailImageView;
    private DatabaseReference databaseReference;
    private Movie movieToEdit;  // Used to store movie for editing

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Movies");

        // Initialize Views
        movieTitleEditText = findViewById(R.id.movieTitleEditText);
        movieStudioEditText = findViewById(R.id.movieStudioEditText);
        criticsRatingEditText = findViewById(R.id.criticsRatingEditText);
        thumbnailUrlEditText = findViewById(R.id.thumbnailUrlEditText);
        favoriteCheckBox = findViewById(R.id.favoriteCheckBox);
        movieThumbnailImageView = findViewById(R.id.movieThumbnailImageView);

        // Check if we are editing a movie
        Intent intent = getIntent();
        if (intent.hasExtra("movie")) {
            movieToEdit = (Movie) intent.getSerializableExtra("movie");
            if (movieToEdit != null) {
                populateFields(movieToEdit);
            }
        }
    }

    // Populate fields with movie data if editing
    private void populateFields(Movie movie) {
        movieTitleEditText.setText(movie.getTitle());
        movieStudioEditText.setText(movie.getStudio());
        criticsRatingEditText.setText(movie.getCriticsRating());
        thumbnailUrlEditText.setText(movie.getThumbnailUrl());
        favoriteCheckBox.setChecked(movie.isFavorite());

        // Load the image thumbnail using Glide
        Glide.with(this)
                .load(movie.getThumbnailUrl())
                .into(movieThumbnailImageView);
    }

    // Save movie (either add new or update existing)
    public void saveMovie(View view) {
        String title = movieTitleEditText.getText().toString().trim();
        String studio = movieStudioEditText.getText().toString().trim();
        String criticsRating = criticsRatingEditText.getText().toString().trim();
        String thumbnailUrl = thumbnailUrlEditText.getText().toString().trim();
        boolean isFavorite = favoriteCheckBox.isChecked();

        if (title.isEmpty() || studio.isEmpty() || criticsRating.isEmpty() || thumbnailUrl.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String movieId = (movieToEdit != null) ? movieToEdit.getId() : databaseReference.push().getKey();
        Movie movie = new Movie(movieId, title, studio, thumbnailUrl, criticsRating, isFavorite);

        // Save movie to Firebase
        databaseReference.child(movieId).setValue(movie)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(MainActivity.this, "Movie saved successfully!", Toast.LENGTH_SHORT).show();

                    // After saving, redirect to ListMoviesActivity
                    Intent intent = new Intent(MainActivity.this, ListMoviesActivity.class);
                    startActivity(intent);  // Start ListMoviesActivity
                    finish();  // Close the current activity
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(MainActivity.this, "Failed to save movie.", Toast.LENGTH_SHORT).show();
                });
    }

    // Handle the "Add Movie" button click
    public void addMovie(View view) {
        Intent intent = new Intent(MainActivity.this, ListMoviesActivity.class);
        startActivity(intent);  // Open the activity to add a new movie
    }


}
