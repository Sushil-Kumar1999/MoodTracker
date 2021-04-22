package com.example.moodtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;
import java.util.List;

public class MoodVariationSingleMealActivity extends AppCompatActivity {

    private LineChart lineChart;
    private EntryViewModel entryViewModel;
    private List<Entry> entryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_variation_single_meal);

        String selectedMeal =  getIntent().getStringExtra("Meal");
        setTitle(String.format("Mood variation for %s", selectedMeal));

        lineChart = findViewById(R.id.chart_mood_variation_single_meal);

        entryViewModel = new ViewModelProvider(this).get(EntryViewModel.class);
        entryList = getEntryList(selectedMeal);
        setChartData();
    }

    private List<Entry> getEntryList(String selectedMeal) {
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

    private void setChartData() {
        ArrayList<com.github.mikephil.charting.data.Entry> chartDataArray = new ArrayList<>();

        for (Entry entry : entryList) {
            com.github.mikephil.charting.data.Entry chartDataEntry =
                    new com.github.mikephil.charting.data.Entry(
                            Long.valueOf(entry.getDate().getTime()).floatValue(),
                            entry.getMood().ordinal());
            chartDataEntry.setData(entry); // Entry object needed later for marker view
            chartDataArray.add(chartDataEntry);
        }

        LineDataSet dataSet = new LineDataSet(chartDataArray, "Mood variations for meal");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setDrawValues(false);

        LineData lineData = new LineData();
        lineData.addDataSet(dataSet);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setSpaceMin(10 * Utilities.ONE_DAY_IN_MILLISECONDS);
        xAxis.setSpaceMax(10 * Utilities.ONE_DAY_IN_MILLISECONDS);
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

        lineChart.getLegend().setEnabled(false);
        lineChart.setData(lineData);
        lineChart.getDescription().setEnabled(false);
        lineChart.animateXY(1000, 1000);

        SleepDataMarkerView markerView = new SleepDataMarkerView(this, R.layout.sleep_marker_view, entryViewModel);
        lineChart.setMarker(markerView);

        lineChart.invalidate();
    }
}