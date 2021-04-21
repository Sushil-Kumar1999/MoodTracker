package com.example.moodtracker;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class EntryRepository {

    private EntryDao entryDao;
    private LiveData<List<Entry>> allEntries;

    public EntryRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        entryDao = appDatabase.entryDao();
        allEntries = entryDao.getAll();
    }

    public void insert(Entry entry) {
        new InsertEntryAsyncTask(entryDao).execute(entry);
    }

    public void delete(Entry entry) {
        new DeleteEntryAsyncTask(entryDao).execute(entry);
    }

    public LiveData<List<Entry>> getAllEntries() {
        return allEntries;
    }

    public List<Entry> findEntriesByMood(Mood mood) {

        try {
            return new FindEntriesByMoodAsyncTask(entryDao).execute(mood).get();
        }
        catch(ExecutionException e) {
            Log.e("ExecutionException", e.getMessage());
            return null;
        }
        catch ( InterruptedException i) {
            Log.e("InterruptedException", i.getMessage());
            return null;
        }
    }

    private static class InsertEntryAsyncTask extends AsyncTask<Entry, Void, Void> {

        private EntryDao entryDao;

        private InsertEntryAsyncTask(EntryDao entryDao) {
            this.entryDao = entryDao;
        }

        @Override
        protected Void doInBackground(Entry... entries) {
            entryDao.insert(entries[0]);
            return null;
        }
    }

    private static class DeleteEntryAsyncTask extends AsyncTask<Entry, Void, Void> {

        private EntryDao entryDao;

        private DeleteEntryAsyncTask(EntryDao entryDao) {
            this.entryDao = entryDao;
        }

        @Override
        protected Void doInBackground(Entry... entries) {
            entryDao.delete(entries[0]);
            return null;
        }
    }

    private class FindEntriesByMoodAsyncTask extends AsyncTask<Mood, Void,List<Entry>> {
        private EntryDao entryDao;

        private FindEntriesByMoodAsyncTask(EntryDao entryDao) {
            this.entryDao = entryDao;
        }

        @Override
        protected List<Entry> doInBackground(Mood... moods) {
            return entryDao.findEntriesByMood(moods[0]);
        }
    }
}
