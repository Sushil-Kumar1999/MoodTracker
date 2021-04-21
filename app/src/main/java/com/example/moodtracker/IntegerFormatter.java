package com.example.moodtracker;

import com.github.mikephil.charting.formatter.ValueFormatter;

public class IntegerFormatter extends ValueFormatter {

    @Override
    public String getFormattedValue(float value) {
        int integerValue = Float.valueOf(value).intValue();
        return String.valueOf(integerValue);
    };
}
