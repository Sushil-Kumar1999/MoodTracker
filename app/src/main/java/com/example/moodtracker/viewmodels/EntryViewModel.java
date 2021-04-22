package com.example.moodtracker.viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.moodtracker.data.Entry;
import com.example.moodtracker.data.EntryRepository;
import com.example.moodtracker.data.Mood;

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

    public List<Entry> findEntriesByMood(Mood mood) {
        return entryRepository.findEntriesByMood(mood);
    }

    public List<Entry> findEntriesByBreakfast(boolean hadBreakfast) {
       return entryRepository.findEntriesByBreakfast(hadBreakfast);
    }

    public List<Entry> findEntriesByLunch(boolean hadLunch) {
        return entryRepository.findEntriesByLunch(hadLunch);
    }

    public List<Entry> findEntriesByDinner(boolean hadDinner) {
        return entryRepository.findEntriesByDinner(hadDinner);
    }
}
