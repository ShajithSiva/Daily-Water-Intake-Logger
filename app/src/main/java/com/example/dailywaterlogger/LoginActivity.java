package com.example.dailywaterlogger;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;
    private TextView tvRegister;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set layout
        setContentView(R.layout.activity_login);

        // Initialize views
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);

        // Initialize database
        databaseHelper = new DatabaseHelper(this);

        // Login button click
        btnLogin.setOnClickListener(v -> handleLogin());

        // Register text click → go to Register screen
        tvRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void handleLogin() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Input validation
        if (username.isEmpty()) {
            etUsername.setError("Username required");
            return;
        }

        if (password.isEmpty()) {
            etPassword.setError("Password required");
            return;
        }

        // Check credentials from database
        int userId = databaseHelper.loginUser(username, password);

        if (userId != -1) {
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();

            // Go to Home screen
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.putExtra("USER_ID", userId);
            startActivity(intent);
            finish();

        } else {
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }
}
