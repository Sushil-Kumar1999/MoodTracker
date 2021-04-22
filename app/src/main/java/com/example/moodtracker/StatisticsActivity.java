package com.example.moodtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class StatisticsActivity extends AppCompatActivity {

    private Spinner moodSpinner;
    private String selectedMood;
    private Spinner mealSpinner;
    private String selectedMeal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        setTitle("Statistics");

        setupMoodSpinner();
        setupMealSpinner();
    }

    public void launchSleepStatsActivity(View view) {
        if (selectedMood.equals("All")) {
            launchSleepStatsAllActivity();
        }
        else {
            launchSleepStatsMoodActivity();
        }
    }

    public void launchMoodCountActivity(View view) {
        startActivity(new Intent(getApplicationContext(), MoodCountActivity.class));
    }

    public void launchMoodVariationActivity(View view) {
        startActivity(new Intent(getApplicationContext(), MoodVariationActivity.class));
    }

    public void launchMoodVariationAppetiteActivity(View view) {
        Intent intent = new Intent(getApplicationContext(), MoodVariationAppetiteActivity.class);
        intent.putExtra("Meal", selectedMeal);
        startActivity(intent);
    }

    private void launchSleepStatsAllActivity() {
        startActivity(new Intent(getApplicationContext(), SleepStatsAllActivity.class));
    }

    private void launchSleepStatsMoodActivity() {
        Intent intent = new Intent(getApplicationContext(), SleepStatsMoodActivity.class);
        intent.putExtra("Mood", selectedMood);
        startActivity(intent);
    }

    private void setupMoodSpinner() {
        moodSpinner = findViewById(R.id.spinner_sleep_stats_mood);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.moods, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        moodSpinner.setAdapter(adapter);

        moodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedMood = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setupMealSpinner() {
        mealSpinner = findViewById(R.id.spinner_appetite_stats_mood);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.meals, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mealSpinner.setAdapter(adapter);

        mealSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedMeal = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}