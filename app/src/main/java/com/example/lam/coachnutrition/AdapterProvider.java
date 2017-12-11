package com.example.lam.coachnutrition;

import android.content.Context;
import android.database.Cursor;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;

public class AdapterProvider {

    public static SimpleCursorAdapter getTwoItemAdapter(Context context, Cursor query) {
        return new SimpleCursorAdapter(context,
                android.R.layout.simple_list_item_2,
                query,
                new String[]{BaseInformation.FoodEntry.COLUMN_NAME,
                        BaseInformation.FoodEntry.COLUMN_COLORIE},
                new int[]{android.R.id.text1, android.R.id.text2},
                0);
    }

    public static SimpleCursorAdapter getOneItemAdapter(Context context, Cursor query) {
        return new SimpleCursorAdapter(context,
                android.R.layout.simple_list_item_1,
                query,
                new String[]{BaseInformation.FoodEntry.COLUMN_NAME},
                new int[]{android.R.id.text1},
                0);
    }

}

