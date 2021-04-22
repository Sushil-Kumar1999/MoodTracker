package com.example.moodtracker.utils;

import androidx.room.TypeConverter;

import com.example.moodtracker.data.Mood;

import java.util.Date;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static Mood fromIntToMood(int value) {
        return (Mood.values()[value]);
    }

    @TypeConverter
    public static int fromMoodToInt(Mood mood) {
        return mood.ordinal();
    }
}
