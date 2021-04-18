package com.example.moodtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class ChooseMoodActivity extends AppCompatActivity {

    private EntryViewModel entryViewModel;
    private Entry newEntry;

    private ImageButton selectedButton;
    private Button nextButton;

    private SwitchCompat breakfastSwitch;
    private SwitchCompat lunchSwitch;
    private SwitchCompat dinnerSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_mood);
        setTitle("Choose your mood");

        newEntry = new Entry();
        entryViewModel = new ViewModelProvider(this).get(EntryViewModel.class);
        nextButton = findViewById(R.id.button_next);
    }

    public void angryMoodSelected(View view) {
        newEntry.setMood(Mood.Angry);
        select((ImageButton) view);
    }

    public void sadMoodSelected(View view) {
        newEntry.setMood(Mood.Sad);
        select((ImageButton) view);
    }

    public void mehMoodSelected(View view) {
        newEntry.setMood(Mood.Meh);
        select((ImageButton) view);
    }

    public void happyMoodSelected(View view) {
        newEntry.setMood(Mood.Happy);
        select((ImageButton) view);
    }

    public void cheerfulMoodSelected(View view) {
        newEntry.setMood(Mood.Cheerful);
        select((ImageButton) view);
    }

    public void gotToAppetiteView(View view) {
        setContentView(R.layout.input_appetite);
        setTitle("Appetite");

        initializeAppetiteView();
    }

    public void goToSleepView(View view) {
        setContentView(R.layout.input_sleep);
        setTitle("Sleep");

        initializeSleepView();
    }

    private void select(ImageButton button) {
        if (selectedButton != null) {
            selectedButton.setSelected(false);
        }
        button.setSelected(true);
        selectedButton = button;

        nextButton.setEnabled(true);
    }

    private void initializeAppetiteView() {
        breakfastSwitch = findViewById(R.id.switch_breakfast);
        lunchSwitch = findViewById(R.id.switch_lunch);
        dinnerSwitch = findViewById(R.id.switch_dinner);

        breakfastSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            newEntry.setHadBreakfast(isChecked);
            logEntry();
        });

        lunchSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            newEntry.setHadLunch(isChecked);
            logEntry();
        });

        dinnerSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            newEntry.setHadDinner(isChecked);
            logEntry();
        });
    }

    private void initializeSleepView() {
        logEntry();
    }

    public void addEntry(View view) {
        newEntry.setSleepDuration(4.5f);
        newEntry.setDate(Calendar.getInstance().getTime());
        entryViewModel.insert(newEntry);

        //Snackbar.make(findViewById(R.id.choose_mood_view).getRootView(), "New entry created", Snackbar.LENGTH_LONG).show();
    }


    private void logEntry() {
        Log.d("Mood", "mood " + newEntry.getMood().toString());
        Log.d("Appetite", "breakfast " + newEntry.isHadBreakfast());
        Log.d("Appetite", "lunch " + newEntry.isHadLunch());
        Log.d("Appetite", "dinner " + newEntry.isHadDinner());
    }
}