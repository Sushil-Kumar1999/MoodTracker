package com.example.moodtracker.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.moodtracker.R;
import com.github.mikephil.charting.charts.LineChart;

public class SleepDurationAllMoodsActivity extends AppCompatActivity {

    private LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_duration_all_moods);
        setTitle("Sleep duration for all moods");

        lineChart = findViewById(R.id.chart_sleep_duration_all_moods);
    }
}