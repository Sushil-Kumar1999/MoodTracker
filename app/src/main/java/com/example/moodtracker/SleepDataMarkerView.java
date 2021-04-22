package com.example.moodtracker;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moodtracker.data.Entry;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SleepDataMarkerView extends MarkerView {

    private MPPointF mOffset;
    private TextView textViewSleepDuration;
    private TextView textViewDate;
    private TextView textViewMoodValue;
    private ImageView imageViewMood;

    public SleepDataMarkerView(Context context, int layoutResource, EntryViewModel eViewModel) {
        super(context, layoutResource);

        textViewSleepDuration = findViewById(R.id.mv_tv_sd);
        textViewDate = findViewById(R.id.mv_tv_date);
        textViewMoodValue = findViewById(R.id.mv_tv_mood_value);
        imageViewMood = findViewById(R.id.mv_iv_mood);
    }

    @Override
    public void refreshContent(com.github.mikephil.charting.data.Entry e, Highlight highlight) {
        textViewSleepDuration.setText(String.format("Sleep duration: %s hours", e.getY()));

        Date date = new Date(Float.valueOf(e.getX()).longValue());
        String formattedDate = new SimpleDateFormat("dd MMMM YYYY").format(date);
        textViewDate.setText(String.format("Date: %s", formattedDate));

        Entry entryData = (Entry) e.getData();
        textViewMoodValue.setText(entryData.getMood().toString());
        imageViewMood.setImageResource(Utilities.getEmojiResourceId(entryData.getMood()));

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
}
