package com.example.moodtracker.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;

import com.example.moodtracker.EntryViewModel;
import com.example.moodtracker.IntegerFormatter;
import com.example.moodtracker.Mood;
import com.example.moodtracker.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Collections;

public class MoodCountActivity extends AppCompatActivity {

    private PieChart pieChart;
    private EntryViewModel entryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_count);
        setTitle("Mood count");

        entryViewModel = new ViewModelProvider(this).get(EntryViewModel.class);
        pieChart = findViewById(R.id.mood_count_chart);
        setupChart();
        setChartData();
    }

    private void setupChart() {
        pieChart.setDrawHoleEnabled(true);
        //pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(16f);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("Mood count");
        pieChart.setCenterTextSize(24f);
        pieChart.getDescription().setEnabled(false);

        Legend legend = pieChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setDrawInside(false);
    }

    private void setChartData() {
        ArrayList<PieEntry> pieEntryList = new ArrayList<>();

        int cheerfulMoodCount = entryViewModel.findEntriesByMood(Mood.Cheerful).size();
        int happyMoodSize = entryViewModel.findEntriesByMood(Mood.Happy).size();
        int mehMoodCount = entryViewModel.findEntriesByMood(Mood.Meh).size();
        int sadMoodCount = entryViewModel.findEntriesByMood(Mood.Sad).size();
        int angryMoodCount = entryViewModel.findEntriesByMood(Mood.Angry).size();

        pieEntryList.add(new PieEntry(cheerfulMoodCount, "Cheerful"));
        pieEntryList.add(new PieEntry(happyMoodSize, "Happy"));
        pieEntryList.add(new PieEntry(mehMoodCount, "Meh"));
        pieEntryList.add(new PieEntry(sadMoodCount, "Sad"));
        pieEntryList.add(new PieEntry(angryMoodCount, "Angry"));

        ArrayList<Integer> colors = new ArrayList<>();
        for (int color : ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }
        for (int color : ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }

        PieDataSet dataSet = new PieDataSet(pieEntryList, "Mood frequency");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new IntegerFormatter());
        data.setValueTextSize(16f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        pieChart.invalidate();

        pieChart.animateXY(1400, 1400, Easing.EaseInOutQuad);
    }
}