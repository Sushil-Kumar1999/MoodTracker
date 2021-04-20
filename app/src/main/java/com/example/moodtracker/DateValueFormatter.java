package com.example.moodtracker;

import com.github.mikephil.charting.formatter.ValueFormatter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateValueFormatter extends ValueFormatter {

    @Override
    public String getFormattedValue(float value) {

        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM");
        Date date = new Date(new Float(value).longValue());

        return formatter.format(date);
    };

}
