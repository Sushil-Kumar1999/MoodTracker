package com.example.moodtracker;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moodtracker.data.Entry;
import com.example.moodtracker.utils.Utilities;
import com.example.moodtracker.viewmodels.EntryViewModel;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EntryDataMarkerView extends MarkerView {

    private MPPointF mOffset;
    private TextView textViewSleepDuration;
    private TextView textViewDate;
    private TextView textViewMood;
    private ImageView imageViewMood;
    private TextView textViewBreakfast;
    private TextView textViewLunch;
    private TextView textViewDinner;

    public EntryDataMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);

        textViewSleepDuration = findViewById(R.id.mv_tv_sd);
        textViewDate = findViewById(R.id.mv_tv_date);
        textViewMood = findViewById(R.id.mv_tv_mood);
        imageViewMood = findViewById(R.id.mv_iv_mood);
        textViewBreakfast = findViewById(R.id.mv_tv_breakfast);
        textViewLunch = findViewById(R.id.mv_tv_lunch);
        textViewDinner = findViewById(R.id.mv_tv_dinner);
    }

    @Override
    public void refreshContent(com.github.mikephil.charting.data.Entry e, Highlight highlight) {
        Entry entryData = (Entry) e.getData();

        String formattedDate = new SimpleDateFormat("dd MMMM YYYY").format(entryData.getDate());
        textViewDate.setText(String.format("Date: %s", formattedDate));

        textViewSleepDuration.setText(String.format("Sleep duration: %s hours", entryData.getSleepDuration()));
        textViewMood.setText(String.format("Mood: %s", entryData.getMood().toString()));
        imageViewMood.setImageResource(Utilities.getEmojiResourceId(entryData.getMood()));
        textViewBreakfast.setText(String.format("Had Breakfast: %s", getAnswer(entryData.isHadBreakfast())));
        textViewLunch.setText(String.format("Had Lunch: %s", getAnswer(entryData.isHadLunch())));
        textViewDinner.setText(String.format("Had Dinner: %s", getAnswer(entryData.isHadDinner())));

        super.refreshContent(e, highlight);
    }


    @Override
    public MPPointF getOffset() {
        if(mOffset == null) {
            // center the marker horizontally and vertically
            mOffset = new MPPointF(-(getWidth() / 2), -getHeight());
        }
        return mOffset;
    }

    private String getAnswer(boolean value) {
        return value ? "Yes" : "No";
    }
}
