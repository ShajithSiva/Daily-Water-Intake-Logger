package com.example.dailywaterlogger;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    SessionManager sessionManager;

    Button btnAddWater;
    TextView txtWaterCount, txtPercentage, txtUsername;
    ProgressBar progressBar;

    int currentGlasses = 5;
    int maxGlasses = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sessionManager = new SessionManager(this);

        txtUsername = findViewById(R.id.txtUsername);
        txtWaterCount = findViewById(R.id.txtWaterCount);
        txtPercentage = findViewById(R.id.txtPercentage);
        progressBar = findViewById(R.id.progressBar);
        btnAddWater = findViewById(R.id.btnAddWater);

        txtUsername.setText("John Doe"); // Later connect to database

        updateUI();

        btnAddWater.setOnClickListener(v -> {
            if (currentGlasses < maxGlasses) {
                currentGlasses++;
                updateUI();
            }
        });
    }

    private void updateUI() {

        txtWaterCount.setText(currentGlasses + " / " + maxGlasses + " glasses");

        int progress = (currentGlasses * 100) / maxGlasses;
        progressBar.setProgress(progress);

        txtPercentage.setText("You're " + progress + "% of the way to your goal!");
    }
}
