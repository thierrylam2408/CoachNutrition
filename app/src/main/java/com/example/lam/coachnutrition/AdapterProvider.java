package com.example.lam.coachnutrition;

import android.content.Context;
import android.database.Cursor;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;

public class AdapterProvider {

    public static SimpleCursorAdapter getTwoItemAdapterFood(Context context, Cursor query) {
        return new SimpleCursorAdapter(context,
                android.R.layout.simple_list_item_2,
                query,
                new String[]{BaseInformation.FoodEntry.COLUMN_NAME,
                        BaseInformation.FoodEntry.COLUMN_COLORIES},
                new int[]{android.R.id.text1, android.R.id.text2},
                0);
    }

    public static SimpleCursorAdapter getOneItemAdapterFood(Context context, Cursor query) {
        return new SimpleCursorAdapter(context,
                android.R.layout.simple_list_item_1,
                query,
                new String[]{BaseInformation.FoodEntry.COLUMN_NAME},
                new int[]{android.R.id.text1},
                0);
    }

    public static SimpleCursorAdapter getTwoItemAdapterListMeal(Context context, Cursor query) {
        return new SimpleCursorAdapter(context,
                android.R.layout.simple_list_item_2,
                query,
                new String[]{BaseInformation.MealEntry.COLUMN_NAME,
                        BaseInformation.MealEntry.COLUMN_TIMESTAMP},
                new int[]{android.R.id.text1, android.R.id.text2},
                0);
    }

    public static SimpleCursorAdapter getTwoItemAdapterMeal(Context context, Cursor query) {
        return new SimpleCursorAdapter(context,
                android.R.layout.simple_list_item_2,
                query,
                new String[]{BaseInformation.MealEntry.COLUMN_FOOD,
                        BaseInformation.MealEntry.COLUMN_WEIGHT},
                new int[]{android.R.id.text1, android.R.id.text2},
                0);
    }

}

