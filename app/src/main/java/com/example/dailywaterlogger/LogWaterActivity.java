package com.example.dailywaterlogger;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LogWaterActivity extends AppCompatActivity {

    private EditText etGlasses;
    private DatabaseHelper databaseHelper;
    private SessionManager sessionManager;
    private TextView tvTodayProgress;
    private ProgressBar progressBarWater;
    private int maxGlasses = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_water);

        databaseHelper = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);

        etGlasses = findViewById(R.id.etGlasses);
        tvTodayProgress = findViewById(R.id.tvTodayProgress);
        progressBarWater = findViewById(R.id.progressBarWater);

        Button btnSave    = findViewById(R.id.btnSave);
        Button btnHistory = findViewById(R.id.btnHistory);
        Button btnLogout  = findViewById(R.id.btnLogout);

        // Show today's progress when screen opens
        updateProgress();

        btnSave.setOnClickListener(v -> saveLog());

        btnHistory.setOnClickListener(v ->
                startActivity(new Intent(this, HistoryActivity.class)));

        btnLogout.setOnClickListener(v -> {
            sessionManager.logout();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateProgress();
    }

    private void updateProgress() {
        int userId = sessionManager.getUserId();
        int todayTotal = databaseHelper.getTodayIntake(userId);
        tvTodayProgress.setText("Today: " + todayTotal + " / " + maxGlasses + " glasses");
        int progress = (todayTotal * 100) / maxGlasses;
        progressBarWater.setProgress(Math.min(progress, 100));
    }

    private void saveLog() {
        String glassesStr = etGlasses.getText().toString().trim();

        if (TextUtils.isEmpty(glassesStr)) {
            etGlasses.setError("Please enter number of glasses");
            return;
        }

        int glasses = Integer.parseInt(glassesStr);

        if (glasses <= 0 || glasses > 20) {
            etGlasses.setError("Enter a number between 1 and 20");
            return;
        }

        int userId = sessionManager.getUserId();
        databaseHelper.addWater(userId, glasses);

        Toast.makeText(this, "✅ " + glasses + " glass(es) logged!", Toast.LENGTH_SHORT).show();

        // Refresh progress after saving
        updateProgress();
        etGlasses.setText("");
    }
}