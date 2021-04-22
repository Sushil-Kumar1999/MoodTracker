package com.example.moodtracker.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.moodtracker.R;

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

    public void launchSleepDurationVariationActivity(View view) {
        startActivity(new Intent(getApplicationContext(), SleepDurationVariationActivity.class));
    }

    public void viewSleepDurationMoodStats(View view) {
        if (selectedMood.equals("All")) {
            launchSleepDurationAllMoodsActivity();
        }
        else {
            launchSleepDurationSingleMoodActivity();
        }
    }

    public void launchMoodCountActivity(View view) {
        startActivity(new Intent(getApplicationContext(), MoodCountActivity.class));
    }

    public void launchMoodVariationActivity(View view) {
        startActivity(new Intent(getApplicationContext(), MoodVariationActivity.class));
    }

    public void launchMoodVariationSingleMealActivity(View view) {
        Intent intent = new Intent(getApplicationContext(), MoodVariationSingleMealActivity.class);
        intent.putExtra("Meal", selectedMeal);
        startActivity(intent);
    }

    private void launchSleepDurationAllMoodsActivity() {
        startActivity(new Intent(getApplicationContext(), SleepDurationAllMoodsActivity.class));
    }

    private void launchSleepDurationSingleMoodActivity() {
        Intent intent = new Intent(getApplicationContext(), SleepDurationSingleMoodActivity.class);
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