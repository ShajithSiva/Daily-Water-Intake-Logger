package com.example.dailywaterlogger;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    // Session
    SessionManager sessionManager;

    // UI Components
    TextView txtUsername;
    TextView txtWaterCount;
    TextView txtPercentage;
    TextView txtSeeHistory;

    Button btnAddWater;
    ProgressBar progressBar;

    // Water values
    int currentGlasses = 0;   // Default starting value
    int maxGlasses = 12;       // Daily goal

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize SessionManager
        sessionManager = new SessionManager(this);

        // Bind UI
        txtUsername = findViewById(R.id.txtUsername);
        txtWaterCount = findViewById(R.id.txtWaterCount);
        txtPercentage = findViewById(R.id.txtPercentage);
        txtSeeHistory = findViewById(R.id.txtSeeHistory);

        progressBar = findViewById(R.id.progressBar);
        btnAddWater = findViewById(R.id.btnAddWater);

        // Set Username (Temporary static name)
        txtUsername.setText("John Doe");

        // Update UI initially
        updateUI();

        // Add Water Button Click
        btnAddWater.setOnClickListener(v -> {
            if (currentGlasses < maxGlasses) {
                currentGlasses++;
                updateUI();
            }
        });

        // See History Click
        txtSeeHistory.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, HistoryActivity.class);
            startActivity(intent);
        });
    }

    // Update UI Method
    private void updateUI() {

        // Update water text
        txtWaterCount.setText(currentGlasses + " / " + maxGlasses + " glasses");

        // Calculate percentage
        int progress = (currentGlasses * 100) / maxGlasses;

        // Update progress bar
        progressBar.setProgress(progress);

        // Update percentage text
        txtPercentage.setText("You're " + progress + "% of the way to your goal!");
    }
}