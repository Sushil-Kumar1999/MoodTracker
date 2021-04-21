package com.example.moodtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;

public class MoodCountActivity extends AppCompatActivity {

    private PieChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_count);
        setTitle("Mood count");

        chart = findViewById(R.id.mood_count_chart);


    }
}