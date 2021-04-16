package com.example.moodtracker;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import java.util.List;

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

    public LiveData<List<Entry>> getAllEntries() {
        return allEntries;
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
}
