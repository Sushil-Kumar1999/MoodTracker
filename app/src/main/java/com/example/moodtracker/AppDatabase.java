package com.example.moodtracker;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;
import java.util.GregorianCalendar;

@Database(entities = {Entry.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract EntryDao entryDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "app_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private EntryDao entryDao;

        private PopulateDbAsyncTask(AppDatabase appDatabase) {
            entryDao = appDatabase.entryDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            entryDao.insert(new Entry(Mood.Happy, 7, true, true, true, new GregorianCalendar(2021, 02, 11, 10, 23, 34).getTime()));
            entryDao.insert(new Entry(Mood.Happy, 7, true, true, true, new GregorianCalendar(2021, 03, 12, 8, 56, 2).getTime()));
            entryDao.insert(new Entry(Mood.Happy, 7, true, true, true, new GregorianCalendar(2021, 04, 13, 14, 17, 36).getTime()));
            return null;
        }
    }
}
