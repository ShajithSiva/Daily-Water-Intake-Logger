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
    private TextView tvTodayProgress;
    private ProgressBar progressBarWater;

    private Database database;
    private SessionManager sessionManager;

    private final int MAX_GLASSES = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_water);

        database = new Database(this);
        sessionManager = new SessionManager(this);

        if (!sessionManager.isLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        etGlasses = findViewById(R.id.etGlasses);
        tvTodayProgress = findViewById(R.id.tvTodayProgress);
        progressBarWater = findViewById(R.id.progressBarWater);

        Button btnSave = findViewById(R.id.btnSave);
        Button btnRemove = findViewById(R.id.btnRemove);
        Button btnHistory = findViewById(R.id.btnHistory);
        Button btnLogout = findViewById(R.id.btnLogout);

        progressBarWater.setMax(100);

        updateProgress();

        btnSave.setOnClickListener(v -> saveLog());

        btnRemove.setOnClickListener(v -> removeGlass());

        btnHistory.setOnClickListener(v -> {
            Intent intent = new Intent(this, HistoryActivity.class);
            startActivity(intent);
        });

        btnLogout.setOnClickListener(v -> {

            sessionManager.logout();

            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();

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
        int todayTotal = database.getTodayIntake(userId);

        tvTodayProgress.setText("Today: " + todayTotal + " / " + MAX_GLASSES + " glasses");

        int progress = (todayTotal * 100) / MAX_GLASSES;

        progressBarWater.setProgress(Math.min(progress, 100));
    }

    private void saveLog() {

        String glassesStr = etGlasses.getText().toString().trim();

        if (TextUtils.isEmpty(glassesStr)) {
            etGlasses.setError("Enter number of glasses");
            return;
        }

        int glasses = Integer.parseInt(glassesStr);

        if (glasses <= 0 || glasses > 50) {
            etGlasses.setError("Enter value between 1 and 50");
            return;
        }

        int userId = sessionManager.getUserId();
        int todayTotal = database.getTodayIntake(userId);

        if (todayTotal + glasses > MAX_GLASSES) {

            int remaining = MAX_GLASSES - todayTotal;

            Toast.makeText(
                    this,
                    "⚠ You can only add " + remaining + " more glasses today",
                    Toast.LENGTH_LONG
            ).show();

            return;
        }

        database.addWater(userId, glasses);

        Toast.makeText(this, glasses + " glass(es) added!", Toast.LENGTH_SHORT).show();

        updateProgress();

        etGlasses.setText("");
    }

    private void removeGlass() {

        String glassesStr = etGlasses.getText().toString().trim();

        if (TextUtils.isEmpty(glassesStr)) {
            etGlasses.setError("Enter number of glasses");
            return;
        }

        int glasses = Integer.parseInt(glassesStr);

        int userId = sessionManager.getUserId();
        int todayTotal = database.getTodayIntake(userId);

        if (glasses > todayTotal) {
            Toast.makeText(this, "Cannot remove more than today's total", Toast.LENGTH_SHORT).show();
            return;
        }

        database.addWater(userId, -glasses);

        Toast.makeText(this, glasses + " glass(es) removed!", Toast.LENGTH_SHORT).show();

        updateProgress();

        etGlasses.setText("");
    }
}