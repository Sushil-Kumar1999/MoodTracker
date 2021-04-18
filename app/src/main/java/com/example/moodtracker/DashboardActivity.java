package com.example.moodtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        setTitle("Dashboard");
    }

    public void launchSecondActivity(View view) {
        Intent intent = new Intent(this, ChooseMoodActivity.class);
        startActivity(intent);
    }

    public void launchEntryHistoryActivity(View view) {
        Intent intent = new Intent(this, EntryHistoryActivity.class);
        startActivity(intent);
    }
}