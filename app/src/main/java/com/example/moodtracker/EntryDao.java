package com.example.moodtracker;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.Date;
import java.util.List;

@Dao
public interface EntryDao {

    @Insert
    void insert(Entry entry);

    @Delete()
    void delete(Entry entry);

    @Query("select * from entry order by date desc")
    LiveData<List<Entry>> getAll();

    @Query("SELECT * FROM entry WHERE mood = :mood ORDER BY date ASC")
    List<Entry> findEntriesByMood(Mood mood);
}
