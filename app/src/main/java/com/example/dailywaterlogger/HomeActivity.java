package com.example.dailywaterlogger;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    SessionManager sessionManager;
    Button btnLogout, btnAddWater;
    TextView txtWaterCount;
    ProgressBar progressBar;

    int currentGlasses = 5;   // starting value
    int maxGlasses = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sessionManager = new SessionManager(this);

        // Initialize views
        btnLogout = findViewById(R.id.btnLogout);
        btnAddWater = findViewById(R.id.btnAddWater);
        txtWaterCount = findViewById(R.id.txtWaterCount);
        progressBar = findViewById(R.id.progressBar);

        updateUI();

        // Add Water button logic
        btnAddWater.setOnClickListener(v -> {
            if (currentGlasses < maxGlasses) {
                currentGlasses++;
                updateUI();
            }
        });

        // Logout logic (your original code)
        btnLogout.setOnClickListener(v -> {
            sessionManager.logout();
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void updateUI() {
        txtWaterCount.setText(currentGlasses + " / " + maxGlasses + " glasses");
        int progress = (currentGlasses * 100) / maxGlasses;
        progressBar.setProgress(progress);
    }
}
