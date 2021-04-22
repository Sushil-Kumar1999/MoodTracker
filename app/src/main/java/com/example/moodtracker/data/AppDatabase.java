package com.example.moodtracker.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.moodtracker.utils.Converters;

import java.util.GregorianCalendar;

@Database(entities = {Entry.class}, version = 2)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    private static Migration migration_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE entry ADD COLUMN note TEXT DEFAULT ''");
        }
    };

    public abstract EntryDao entryDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "app_database")
                    .addMigrations(migration_1_2)
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

            entryDao.insert(new Entry(Mood.Happy, 7, true, true, true,
                    new GregorianCalendar(2021, 02, 11, 10, 23, 34).getTime(),
                    "I am happy"));
            entryDao.insert(new Entry(Mood.Happy, 7, true, true, true,
                    new GregorianCalendar(2021, 03, 12, 8, 56, 2).getTime(),
                    "I am happy"));
            entryDao.insert(new Entry(Mood.Happy, 7, true, true, true,
                    new GregorianCalendar(2021, 04, 13, 14, 17, 36).getTime(),
                    "I am happy"));
            return null;
        }
    }
}
