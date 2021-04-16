package com.example.moodtracker;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class EntryViewModel extends AndroidViewModel {

    private EntryRepository entryRepository;
    private LiveData<List<Entry>> allEntries;

    public EntryViewModel(@NonNull Application application) {
        super(application);
        entryRepository = new EntryRepository(application);
        allEntries = entryRepository.getAllEntries();
    }

    public void insert(Entry entry) {
        entryRepository.insert(entry);
    }

    public void delete(Entry entry) {
        entryRepository.delete(entry);
    }

    public LiveData<List<Entry>> getAllEntries() {
        return allEntries;
    }
}
