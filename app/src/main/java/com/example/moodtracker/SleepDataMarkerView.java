package com.example.moodtracker;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SleepDataMarkerView extends MarkerView {

    private MPPointF mOffset;
    private TextView textViewSleepDuration;
    private TextView textViewDate;

    public SleepDataMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);

        textViewSleepDuration = findViewById(R.id.mv_tv_sd);
        textViewDate = findViewById(R.id.mv_tv_date);
    }

    @Override
    public void refreshContent(com.github.mikephil.charting.data.Entry e, Highlight highlight) {
        textViewSleepDuration.setText(String.format("Sleep duration: %s hours", e.getY()));

        String date = new SimpleDateFormat("dd MMMM YYYY")
                .format(new Date(new Float(e.getX()).longValue()));
        textViewDate.setText(String.format("Date: %s", date));

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
