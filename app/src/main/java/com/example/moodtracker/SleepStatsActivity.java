package com.example.moodtracker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SleepStatsActivity extends AppCompatActivity {

    private LineChart chart;
    private EntryViewModel entryViewModel;
    private List<Entry> entryList;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_stats);
        setTitle("Sleep duration vs time");

        chart = findViewById(R.id.sleep_chart);
        entryViewModel = new ViewModelProvider(this).get(EntryViewModel.class);

        entryViewModel.getAllEntries().observe(this, entries -> {
            entryList = entries;
            // sort in ascending order of date
            entryList.sort(Comparator.comparing(e -> e.getDate().getTime()));
            setChartData();
        });
    }

    private void setChartData() {
        ArrayList<com.github.mikephil.charting.data.Entry> chartDataArray = new ArrayList<>();

        for (Entry entry : entryList) {
            chartDataArray.add(
                    new com.github.mikephil.charting.data.Entry(
                            Long.valueOf(entry.getDate().getTime()).floatValue(),
                            entry.getSleepDuration())
                    );
        }

        LineDataSet dataSet = new LineDataSet(chartDataArray, "Sleep durations");

        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(16f);

        LineData lineData = new LineData();
        lineData.addDataSet(dataSet);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new DateValueFormatter());
        xAxis.setGranularityEnabled(true);
        xAxis.setGranularity(86400000); // 24 hrs in milliseconds

        chart.getLegend().setEnabled(false);
        chart.setData(lineData);
        chart.getDescription().setEnabled(false);
        chart.animateXY(1000, 1000);
        chart.invalidate();
    }
}