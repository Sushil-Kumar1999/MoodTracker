package com.example.moodtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class SleepDurationSingleMoodActivity extends AppCompatActivity {

    private LineChart chart;
    private EntryViewModel entryViewModel;
    private List<Entry> entryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_duration_single_mood);

        String selectedMood =  getIntent().getStringExtra("Mood");
        setTitle(String.format("Sleep duration for %s mood", selectedMood));

        chart = findViewById(R.id.chart_sleep_duration_single_mood);
        entryViewModel = new ViewModelProvider(this).get(EntryViewModel.class);

        entryList = entryViewModel.findEntriesByMood(Mood.valueOf(selectedMood));
        setChartData();
    }

    private void setChartData() {
        ArrayList<com.github.mikephil.charting.data.Entry> chartDataArray = new ArrayList<>();

        for (Entry entry : entryList) {
            com.github.mikephil.charting.data.Entry chartDataEntry =
                    new com.github.mikephil.charting.data.Entry(
                        Long.valueOf(entry.getDate().getTime()).floatValue(),
                        entry.getSleepDuration());
            chartDataEntry.setData(entry); // Entry object needed later for marker view
            chartDataArray.add(chartDataEntry);
        }

        LineDataSet dataSet = new LineDataSet(chartDataArray, "Sleep durations per mood");

        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(16f);

        LineData lineData = new LineData();
        lineData.addDataSet(dataSet);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setSpaceMin(10 * Utilities.ONE_DAY_IN_MILLISECONDS);
        xAxis.setSpaceMax(10 * Utilities.ONE_DAY_IN_MILLISECONDS);
        xAxis.setValueFormatter(new DateValueFormatter());
        xAxis.setGranularityEnabled(true);
        xAxis.setGranularity(Utilities.ONE_DAY_IN_MILLISECONDS);

        chart.getLegend().setEnabled(false);
        chart.setData(lineData);
        chart.getDescription().setEnabled(false);
        chart.animateXY(1000, 1000);

        SleepDataMarkerView markerView = new SleepDataMarkerView(this, R.layout.sleep_marker_view, entryViewModel);
        chart.setMarker(markerView);

        chart.invalidate();
    }
}