package com.example.moodtracker.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.moodtracker.data.Entry;
import com.example.moodtracker.viewmodels.EntryViewModel;
import com.example.moodtracker.data.Mood;
import com.example.moodtracker.R;

import java.util.Calendar;

public class CreateEntryActivity extends AppCompatActivity {

    private EntryViewModel entryViewModel;
    private Entry newEntry;

    private ImageButton selectedButton;
    private Button nextButton;
    private EditText noteEditText;
    private AlertDialog noteDialog;

    private SwitchCompat breakfastSwitch;
    private SwitchCompat lunchSwitch;
    private SwitchCompat dinnerSwitch;

    private EditText editTextTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_mood);
        setTitle("Choose your mood");

        newEntry = new Entry();
        newEntry.setNote("");
        entryViewModel = new ViewModelProvider(this).get(EntryViewModel.class);
        nextButton = findViewById(R.id.button_next);

        noteEditText = new EditText(this);
        noteDialog = new AlertDialog.Builder(this)
                .setTitle("Add Note")
                .setMessage("Add an optional note to give context to your mood")
                .setView(noteEditText)
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    newEntry.setNote(noteEditText.getText().toString());
                })
                .setNegativeButton("Cancel", null)
                .create();

    }

    public void angryMoodSelected(View view) {
        newEntry.setMood(Mood.Angry);
        Toast.makeText(this, "Angry selected", Toast.LENGTH_SHORT).show();
        select((ImageButton) view);
    }

    public void sadMoodSelected(View view) {
        newEntry.setMood(Mood.Sad);
        Toast.makeText(this, "Sad selected", Toast.LENGTH_SHORT).show();
        select((ImageButton) view);
    }

    public void mehMoodSelected(View view) {
        newEntry.setMood(Mood.Meh);
        Toast.makeText(this, "Meh selected", Toast.LENGTH_SHORT).show();
        select((ImageButton) view);
    }

    public void happyMoodSelected(View view) {
        newEntry.setMood(Mood.Happy);
        Toast.makeText(this, "Happy selected", Toast.LENGTH_SHORT).show();
        select((ImageButton) view);
    }

    public void cheerfulMoodSelected(View view) {
        newEntry.setMood(Mood.Cheerful);
        Toast.makeText(this, "Cheerful selected", Toast.LENGTH_SHORT).show();
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
        });

        lunchSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            newEntry.setHadLunch(isChecked);
        });

        dinnerSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            newEntry.setHadDinner(isChecked);
        });
    }

    private void initializeSleepView() {
        editTextTime = findViewById(R.id.editText_sleep_duration);
    }

    public void addEntry(View view) {
        float sleepDuration = Float.parseFloat(editTextTime.getText().toString());
        newEntry.setSleepDuration(sleepDuration);
        newEntry.setDate(Calendar.getInstance().getTime());
        entryViewModel.insert(newEntry);

        Intent intent = new Intent(this, DashboardActivity.class);
        intent.putExtra("New entry created", true);
        startActivity(intent);
    }

    public void openNoteDialog(View view) {
        noteDialog.show();
    }

    private void logEntry() {
        Log.d("Mood", "mood " + newEntry.getMood().toString());
        Log.d("Appetite", "breakfast " + newEntry.isHadBreakfast());
        Log.d("Appetite", "lunch " + newEntry.isHadLunch());
        Log.d("Appetite", "dinner " + newEntry.isHadDinner());
    }

}