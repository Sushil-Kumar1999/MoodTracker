package com.example.moodtracker;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ChooseMoodActivity extends AppCompatActivity {

    private ImageButton selectedButton;
    private Button nextButton;
    private Entry newEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_mood);
        setTitle("Choose your mood");

        newEntry = new Entry();
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
        //Toast.makeText(this, newEntry.getMood().toString(), Toast.LENGTH_SHORT).show();
        setContentView(R.layout.input_appetite);
        setTitle("Appetite");
    }

    public void goToSleepView(View view) {
        setContentView(R.layout.input_sleep);
        setTitle("Sleep");
    }

    private void select(ImageButton button) {
        if (selectedButton != null) {
            selectedButton.setSelected(false);
        }
        button.setSelected(true);
        selectedButton = button;

        nextButton.setEnabled(true);
    }
}