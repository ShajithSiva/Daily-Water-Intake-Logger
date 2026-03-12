package com.example.dailywaterlogger;

import android.os.Bundle;
import android.database.Cursor;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private Database database;
    private SessionManager sessionManager;

    private ListView listViewHistory;
    private TextView tvEmpty;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // Initialize database and session
        database = new Database(this);
        sessionManager = new SessionManager(this);

        // Check login session
        if (!sessionManager.isLoggedIn()) {
            finish();
            return;
        }

        // UI Components
        listViewHistory = findViewById(R.id.listViewHistory);
        tvEmpty = findViewById(R.id.tvEmpty);
        btnBack = findViewById(R.id.btnBack);

        // Back button → return to previous screen
        btnBack.setOnClickListener(v -> finish());

        // Load history data
        loadHistory();
    }

    private void loadHistory() {

        int userId = sessionManager.getUserId();
        Cursor cursor = database.getWaterHistory(userId);

        List<String> historyList = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {

            do {
                String date = cursor.getString(0);
                int glasses = cursor.getInt(1);

                String record = "📅 " + date + "   💧 " + glasses + " glass(es)";
                historyList.add(record);

            } while (cursor.moveToNext());

            cursor.close();
            tvEmpty.setVisibility(View.GONE);

        } else {
            tvEmpty.setVisibility(View.VISIBLE);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                historyList
        );

        listViewHistory.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}