package com.example.moodtracker.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;

import com.example.moodtracker.R;
import com.example.moodtracker.SleepDataMarkerView;
import com.example.moodtracker.data.Entry;
import com.example.moodtracker.data.Mood;
import com.example.moodtracker.formatters.DateValueFormatter;
import com.example.moodtracker.formatters.MoodFormatter;
import com.example.moodtracker.utils.Utilities;
import com.example.moodtracker.viewmodels.EntryViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

public class SleepDurationAllMoodsActivity extends AppCompatActivity {

    private LineChart lineChart;
    private EntryViewModel entryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_duration_all_moods);
        setTitle("Sleep duration for all moods");

        lineChart = findViewById(R.id.chart_sleep_duration_all_moods);
        entryViewModel = new ViewModelProvider(this).get(EntryViewModel.class);

        setChartData();
    }

    private void setChartData() {
        List<Entry> cheerfulEntryList = entryViewModel.findEntriesByMood(Mood.Cheerful);
        List<Entry> happyEntryList = entryViewModel.findEntriesByMood(Mood.Happy);
        List<Entry> mehEntryList = entryViewModel.findEntriesByMood(Mood.Meh);
        List<Entry> sadEntryList = entryViewModel.findEntriesByMood(Mood.Sad);
        List<Entry> angryEntryList = entryViewModel.findEntriesByMood(Mood.Angry);

        LineDataSet cheerfulDataSet = new LineDataSet(getChartEntries(cheerfulEntryList), "");
        cheerfulDataSet.setLabel("Cheerful");
        cheerfulDataSet.setColor(Utilities.getColor(Mood.Cheerful));
        cheerfulDataSet.setDrawValues(false);

        LineDataSet happyDataSet = new LineDataSet(getChartEntries(happyEntryList), "");
        happyDataSet.setLabel("Happy");
        happyDataSet.setColor(Utilities.getColor(Mood.Happy));
        happyDataSet.setDrawValues(false);

        LineDataSet mehDataSet = new LineDataSet(getChartEntries(mehEntryList), "");
        mehDataSet.setLabel("Meh");
        mehDataSet.setColor(Utilities.getColor(Mood.Meh));
        mehDataSet.setDrawValues(false);

        LineDataSet sadDataSet = new LineDataSet(getChartEntries(sadEntryList), "");
        sadDataSet.setLabel("Sad");
        sadDataSet.setColor(Utilities.getColor(Mood.Sad));
        sadDataSet.setDrawValues(false);

        LineDataSet angryDataSet = new LineDataSet(getChartEntries(angryEntryList), "");
        angryDataSet.setLabel("Angry");
        angryDataSet.setColor(Utilities.getColor(Mood.Angry));
        angryDataSet.setDrawValues(false);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(cheerfulDataSet);
        dataSets.add(happyDataSet);
        dataSets.add(mehDataSet);
        dataSets.add(sadDataSet);
        dataSets.add(angryDataSet);

        LineData lineData = new LineData(dataSets);
        lineChart.setData(lineData);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setSpaceMin(10 * Utilities.ONE_DAY_IN_MILLISECONDS);
        xAxis.setSpaceMax(10 * Utilities.ONE_DAY_IN_MILLISECONDS);
        xAxis.setValueFormatter(new DateValueFormatter());
        xAxis.setGranularityEnabled(true);
        xAxis.setGranularity(Utilities.ONE_DAY_IN_MILLISECONDS);

        lineChart.getDescription().setEnabled(false);
        lineChart.animateXY(1000, 1000);

        SleepDataMarkerView markerView = new SleepDataMarkerView(this, R.layout.sleep_marker_view, entryViewModel);
        lineChart.setMarker(markerView);

        lineChart.invalidate();
    }

    private ArrayList<com.github.mikephil.charting.data.Entry> getChartEntries(List<Entry> dbEntryList) {
        ArrayList<com.github.mikephil.charting.data.Entry> chartDataEntries = new ArrayList<>();

        for (Entry dbEntry : dbEntryList) {
            com.github.mikephil.charting.data.Entry chartDataEntry =
                    new com.github.mikephil.charting.data.Entry(
                            Long.valueOf(dbEntry.getDate().getTime()).floatValue(),
                            dbEntry.getSleepDuration());
            chartDataEntry.setData(dbEntry); // Entry object needed later for marker view
            chartDataEntries.add(chartDataEntry);
        }

        return chartDataEntries;
    }
}