package com.example.moodtracker.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;

import com.example.moodtracker.formatters.DateValueFormatter;
import com.example.moodtracker.data.Entry;
import com.example.moodtracker.viewmodels.EntryViewModel;
import com.example.moodtracker.formatters.MoodFormatter;
import com.example.moodtracker.R;
import com.example.moodtracker.EntryDataMarkerView;
import com.example.moodtracker.utils.Utilities;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

public class MoodVariationSingleMealActivity extends AppCompatActivity {

    private LineChart lineChart;
    private EntryViewModel entryViewModel;
    private String selectedMeal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_variation_single_meal);

        selectedMeal =  getIntent().getStringExtra("Meal");
        setTitle(String.format("Mood variation for %s", selectedMeal));

        lineChart = findViewById(R.id.chart_mood_variation_single_meal);

        entryViewModel = new ViewModelProvider(this).get(EntryViewModel.class);

        setChartData();
    }

    private List<Entry> getMealEatenEntryList(String selectedMeal) {
        List<Entry> result;
        switch (selectedMeal) {
            case "Breakfast" : 
                result = entryViewModel.findEntriesByBreakfast(true);
                break;
            case "Lunch" :
                result = entryViewModel.findEntriesByLunch(true);
                break;
            case "Dinner" :
                result = entryViewModel.findEntriesByDinner(true);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + selectedMeal);
        }

        return result;
    }

    private List<Entry> getMealNotEatenEntryList(String selectedMeal) {
        List<Entry> result;
        switch (selectedMeal) {
            case "Breakfast" :
                result = entryViewModel.findEntriesByBreakfast(false);
                break;
            case "Lunch" :
                result = entryViewModel.findEntriesByLunch(false);
                break;
            case "Dinner" :
                result = entryViewModel.findEntriesByDinner(false);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + selectedMeal);
        }

        return result;
    }

    private void setChartData() {
        List<Entry> mealEatenEntryList = getMealEatenEntryList(selectedMeal);
        List<Entry> mealNotEatenEntryList = getMealNotEatenEntryList(selectedMeal);

        LineDataSet mealEatenDataSet = new LineDataSet(getChartEntries(mealEatenEntryList), "");
        mealEatenDataSet.setLabel(selectedMeal + " eaten");
        mealEatenDataSet.setColor(Color.GREEN);
        mealEatenDataSet.setDrawValues(false);

        LineDataSet mealNotEatenDataSet = new LineDataSet(getChartEntries(mealNotEatenEntryList), "");
        mealNotEatenDataSet.setLabel(selectedMeal + " not eaten");
        mealNotEatenDataSet.setColor(Color.RED);
        mealNotEatenDataSet.setDrawValues(false);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(mealEatenDataSet);
        dataSets.add(mealNotEatenDataSet);

        LineData lineData = new LineData(dataSets);
        lineChart.setData(lineData);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setSpaceMin(5 * Utilities.ONE_DAY_IN_MILLISECONDS);
        xAxis.setSpaceMax(5 * Utilities.ONE_DAY_IN_MILLISECONDS);
        xAxis.setValueFormatter(new DateValueFormatter());
        xAxis.setGranularityEnabled(true);
        xAxis.setGranularity(Utilities.ONE_DAY_IN_MILLISECONDS);

        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setValueFormatter(new MoodFormatter());
        yAxis.setAxisMinimum(0);
        yAxis.setAxisMaximum(4);
        yAxis.setSpaceMin(1);
        yAxis.setSpaceMax(1);
        yAxis.setGranularityEnabled(true);
        yAxis.setGranularity(1);

        lineChart.getAxisRight().setEnabled(false);

        lineChart.getDescription().setEnabled(false);
        lineChart.animateXY(1000, 1000);

        EntryDataMarkerView markerView = new EntryDataMarkerView(this, R.layout.marker_view_entry_data);
        lineChart.setMarker(markerView);

        lineChart.invalidate();
    }

    private ArrayList<com.github.mikephil.charting.data.Entry> getChartEntries(List<Entry> dbEntryList) {
        ArrayList<com.github.mikephil.charting.data.Entry> chartDataEntries = new ArrayList<>();

        for (Entry dbEntry : dbEntryList) {
            com.github.mikephil.charting.data.Entry chartDataEntry =
                    new com.github.mikephil.charting.data.Entry(
                            Long.valueOf(dbEntry.getDate().getTime()).floatValue(),
                            dbEntry.getMood().ordinal());
            chartDataEntry.setData(dbEntry); // Entry object needed later for marker view
            chartDataEntries.add(chartDataEntry);
        }

        return chartDataEntries;
    }
}