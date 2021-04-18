package com.example.moodtracker;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import java.util.Calendar;
import java.util.Date;

@Entity(tableName = "entry")
public class Entry {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private Mood mood;

    private float sleepDuration;

    private boolean hadBreakfast;
    private boolean hadLunch;
    private boolean hadDinner;

    private Date date;

    public Entry() {}

    @Ignore()
    public Entry(Mood mood, float sleepDuration, boolean hadBreakfast, boolean hadLunch, boolean hadDinner, Date date) {
        this.mood = mood;
        this.sleepDuration = sleepDuration;
        this.hadBreakfast = hadBreakfast;
        this.hadLunch = hadLunch;
        this.hadDinner = hadDinner;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public Mood getMood() {
        return mood;
    }

    public float getSleepDuration() {
        return sleepDuration;
    }

    public boolean isHadBreakfast() {
        return hadBreakfast;
    }

    public boolean isHadLunch() {
        return hadLunch;
    }

    public boolean isHadDinner() {
        return hadDinner;
    }

    public Date getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMood(Mood mood) {
        this.mood = mood;
    }

    public void setSleepDuration(float sleepDuration) {
        this.sleepDuration = sleepDuration;
    }

    public void setHadBreakfast(boolean hadBreakfast) {
        this.hadBreakfast = hadBreakfast;
    }

    public void setHadLunch(boolean hadLunch) {
        this.hadLunch = hadLunch;
    }

    public void setHadDinner(boolean hadDinner) {
        this.hadDinner = hadDinner;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
