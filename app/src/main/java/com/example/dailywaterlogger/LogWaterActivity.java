package com.example.dailywaterlogger;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LogWaterActivity extends AppCompatActivity {

    private EditText etGlasses;
    private DatabaseHelper databaseHelper;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_water);

        // Using teammate's exact classes — no changes needed to their code
        databaseHelper = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);

        etGlasses = findViewById(R.id.etGlasses);
        Button btnSave   = findViewById(R.id.btnSave);
        Button btnCancel = findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(v -> saveLog());
        btnCancel.setOnClickListener(v -> finish());
    }

    private void saveLog() {
        String glassesStr = etGlasses.getText().toString().trim();

        // Validation
        if (TextUtils.isEmpty(glassesStr)) {
            etGlasses.setError("Please enter number of glasses");
            return;
        }

        int glasses = Integer.parseInt(glassesStr);

        if (glasses <= 0 || glasses > 20) {
            etGlasses.setError("Enter a number between 1 and 20");
            return;
        }

        // Get logged-in user's ID using teammate's SessionManager
        int userId = sessionManager.getUserId();

        // Save using teammate's DatabaseHelper method
        databaseHelper.addWater(userId, glasses);

        Toast.makeText(this, "✅ " + glasses + " glass(es) logged!", Toast.LENGTH_SHORT).show();
        finish();
    }
}