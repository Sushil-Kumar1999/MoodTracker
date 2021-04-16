package com.example.moodtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class EntryHistoryActivity extends AppCompatActivity {

    private EntryViewModel entryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_history);

        setTitle("Entry History");

        RecyclerView recyclerView = findViewById(R.id.recycler_view_entries);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        EntryAdapter entryAdapter = new EntryAdapter();
        recyclerView.setAdapter(entryAdapter);

        entryViewModel = new ViewModelProvider(this).get(EntryViewModel.class);
        entryViewModel.getAllEntries().observe(this, entries -> {
            entryAdapter.setEntries(entries);
        });
    }
}