package com.example.firebaseapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.AuthResult;

public class SignUpActivity extends AppCompatActivity {

    private EditText editTextName, editTextEmail, editTextPhoneNumber, editTextPassword, editTextReenterPassword;
    private Button buttonSignUp, buttonGoogleSignUp;
    private TextView textViewLogin;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextReenterPassword = findViewById(R.id.editTextReenterPassword);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        buttonGoogleSignUp = findViewById(R.id.buttonGoogleSignUp);
        textViewLogin = findViewById(R.id.textViewLogin);

        mAuth = FirebaseAuth.getInstance();

        // Google Sign-In Setup
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))  // Replace with your actual web client ID
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Sign Up with Email and Password
        buttonSignUp.setOnClickListener(v -> signUpWithEmail());

        // Sign Up with Google
        buttonGoogleSignUp.setOnClickListener(v -> signInWithGoogle());

        // Go to Login Screen
        textViewLogin.setOnClickListener(v -> startActivity(new Intent(SignUpActivity.this, LoginActivity.class)));
    }

    private void signUpWithEmail() {
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String phone = editTextPhoneNumber.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String reenterPassword = editTextReenterPassword.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || reenterPassword.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(reenterPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(SignUpActivity.this, "Sign-Up Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                    } else {
                        Toast.makeText(SignUpActivity.this, "Sign-Up Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void signInWithGoogle() {
        // Sign out the current account before starting the sign-in process
        mGoogleSignInClient.signOut().addOnCompleteListener(this, task -> {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignIn.getSignedInAccountFromIntent(data)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            GoogleSignIn.getSignedInAccountFromIntent(data)
                                    .addOnSuccessListener(account -> {
                                        FirebaseAuth.getInstance().signInWithCredential(
                                                        GoogleAuthProvider.getCredential(account.getIdToken(), null))
                                                .addOnCompleteListener(SignUpActivity.this, authTask -> {
                                                    if (authTask.isSuccessful()) {
                                                        FirebaseUser user = mAuth.getCurrentUser();
                                                        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                                    } else {
                                                        Toast.makeText(SignUpActivity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    });
                        } else {
                            Toast.makeText(SignUpActivity.this, "Google Sign-In Failed.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
