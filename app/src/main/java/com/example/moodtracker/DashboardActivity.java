package com.example.moodtracker;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.google.android.material.snackbar.Snackbar;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        setTitle("Dashboard");

        boolean newEntryAdded = getIntent().getBooleanExtra("New entry created", false);
        if (newEntryAdded) {
            Snackbar.make(findViewById(R.id.dashboard_view), "New entry created", Snackbar.LENGTH_SHORT).show();
        }
    }

    public void launchCreateEntryActivity(View view) {
        Intent intent = new Intent(this, CreateEntryActivity.class);
        startActivity(intent);
    }

    public void launchEntryHistoryActivity(View view) {
        Intent intent = new Intent(this, EntryHistoryActivity.class);
        startActivity(intent);
    }

    public void launchStatisticsActivity(View view) {
        startActivity(new Intent(this, StatisticsActivity.class));
    }
}