package com.example.lam.coachnutrition;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Calendar;

public class GraphActivity extends AppCompatActivity {

    private AccessProvider accessProvider;
    private final String[] select = {
            BaseInformation.MealEntry._ID,
            BaseInformation.MealEntry.COLUMN_CODE,
            BaseInformation.MealEntry.COLUMN_NAME,
            BaseInformation.MealEntry.COLUMN_TIMESTAMP,
            BaseInformation.MealEntry.COLUMN_FOOD,
            BaseInformation.MealEntry.COLUMN_WEIGHT};
    private Cursor cursor;
    private Calendar cal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        accessProvider = new AccessProvider(this);
        GraphView graph = (GraphView) findViewById(R.id.graph);
        cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        DataPoint[] datas = new DataPoint[7];
        for (int i=0; i<7; i++){
            Float calories = accessProvider.countCalories(cal);
            datas[i] = new DataPoint(i,calories);
            cal.set(Calendar.DAY_OF_YEAR, 1);
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(datas);
        graph.addSeries(series);
    }
}
