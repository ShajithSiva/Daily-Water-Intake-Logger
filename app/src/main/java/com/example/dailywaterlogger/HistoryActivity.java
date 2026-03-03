package com.example.dailywaterlogger;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        databaseHelper = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);

        ListView listViewHistory = findViewById(R.id.listViewHistory);
        TextView tvEmpty = findViewById(R.id.tvEmpty);

        int userId = sessionManager.getUserId();
        Cursor cursor = databaseHelper.getWaterHistory(userId);

        List<String> historyList = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String date = cursor.getString(0);
                int glasses = cursor.getInt(1);
                historyList.add("📅  " + date + "     💧  " + glasses + " glass(es)");
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
}