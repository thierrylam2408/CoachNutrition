package com.example.coachnutrition;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void goToIngredient(View v) {
        Intent intent = new Intent(this, FoodActivity.class);
        startActivity(intent);
    }

    public void goToMeal(View v) {
        Intent intent = new Intent(this, ListMealActivity.class);
        startActivity(intent);
    }

    public void goToGraph(View v) {
        Intent intent = new Intent(this, GraphActivity.class);
        startActivity(intent);
    }
}
