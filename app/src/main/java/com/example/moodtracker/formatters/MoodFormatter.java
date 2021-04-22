package com.example.moodtracker.formatters;

import com.example.moodtracker.data.Mood;
import com.github.mikephil.charting.formatter.ValueFormatter;

public class MoodFormatter extends ValueFormatter {

    @Override
    public String getFormattedValue(float value) {
        int integerValue = Float.valueOf(value).intValue();
        return (Mood.values()[integerValue]).toString();
    };
}
