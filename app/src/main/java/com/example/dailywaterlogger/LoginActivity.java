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
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize SessionManager
        sessionManager = new SessionManager(this);

        // If already logged in, skip login screen
        if (sessionManager.isLoggedIn()) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
            return;
        }

        // Bind UI elements
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);

        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);

        // Login button click
        btnLogin.setOnClickListener(v -> handleLogin());

        // Register text click → go to Register screen
        tvRegister.setOnClickListener(v ->
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class))
        );
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

        // 🔐 Hash the entered password
        String hashedPassword = PasswordUtils.hashPassword(password);

        if (hashedPassword == null) {
            Toast.makeText(this, "Error processing password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check credentials from database
        int userId = databaseHelper.loginUser(username, hashedPassword);

        if (userId != -1) {
            // Save session
            sessionManager.createLoginSession(userId);

            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();

            // Navigate to Home screen
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.putExtra("USER_ID", userId);
            startActivity(intent);
            finish();

        } else {
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }
}
