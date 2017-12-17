package com.example.lam.coachnutrition;

import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GraphActivity extends AppCompatActivity {

    private AccessProvider accessProvider;
    private final String[] select = {
            BaseInformation.MealEntry._ID,
            BaseInformation.MealEntry.COLUMN_CODE,
            BaseInformation.MealEntry.COLUMN_NAME,
            BaseInformation.MealEntry.COLUMN_TIMESTAMP,
            BaseInformation.MealEntry.COLUMN_FOOD,
            BaseInformation.MealEntry.COLUMN_WEIGHT};
    private Calendar cal;
    private GraphView graph;
    private float minCal=350F;
    private float maxCal=820F;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        accessProvider = new AccessProvider(this);
        graph = (GraphView) findViewById(R.id.graph);
        cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);

        barCalories();
        drawLimit(minCal);
        drawLimit(maxCal);

        graph.setTitle("Objectif hebdomadaire");
        graph.getGridLabelRenderer().setLabelFormatter(
                new DateAsXAxisLabelFormatter(getApplication(), new SimpleDateFormat("dd/MM/yy")));
        graph.getGridLabelRenderer().setNumHorizontalLabels(3);
        graph.getViewport().setMinX(cal.getTimeInMillis());
        cal.add(Calendar.DAY_OF_YEAR, 6);
        graph.getViewport().setMaxX(cal.getTimeInMillis());
        cal.add(Calendar.DAY_OF_YEAR, -6);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getGridLabelRenderer().setHumanRounding(false);
    }

    private void barCalories(){
        DataPoint[] datas = new DataPoint[7];
        for (int i=0; i<7; i++){
            Date date = new Date();
            date.setTime(cal.getTimeInMillis());
            Float calories = accessProvider.countCalories(cal);
            datas[i] = new DataPoint(date, calories);
            cal.add(Calendar.DAY_OF_YEAR, 1);
        }
        cal.add(Calendar.DAY_OF_YEAR, -7);
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(datas);
        series.setSpacing(5);
        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                if(data.getY()> maxCal || data.getY()< minCal)
                    return Color.rgb(255, 44, 46);
                else return Color.rgb(86, 201, 50);
            }
        });
        graph.addSeries(series);
    }

    private void drawLimit(float limit){
        DataPoint[] tab = new DataPoint[7];
        for (int i=0; i<7; i++){
            Date date = new Date();
            date.setTime(cal.getTimeInMillis());
            tab[i] = new DataPoint(date,limit);
            cal.add(Calendar.DAY_OF_YEAR, 1);
        }
        cal.add(Calendar.DAY_OF_YEAR, -7);
        LineGraphSeries<DataPoint> line = new LineGraphSeries<>(tab);
        line.setThickness(2);
        line.setColor(Color.rgb(46, 93, 248));
        graph.addSeries(line);
    }
}
