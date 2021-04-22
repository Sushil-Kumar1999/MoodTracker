package com.example.moodtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;

public class MoodVariationAppetiteActivity extends AppCompatActivity {

    private LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_variation_appetite);

        String selectedMeal =  getIntent().getStringExtra("Meal");
        setTitle(String.format("Mood variation for %s", selectedMeal));

        lineChart = findViewById(R.id.mood_variation_appetite_chart);
    }
}