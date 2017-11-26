package com.example.lam.coachnutrition;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;

/**
 * Created by LAM on 26/11/2017.
 */
public class FakeAdapterFactory {


    public static CursorAdapter ingredientAdapter(Context context) {
        return new SimpleCursorAdapter(context,
                android.R.layout.simple_list_item_2,
                getIngredients(),
                new String[]{"item", "description"},
                new int[]{android.R.id.text1, android.R.id.text2},
                0);
    }

    private static Cursor getIngredients(){
        String[] columns = new String[] { "_id", "item", "description" };
        MatrixCursor cursor = new MatrixCursor(columns);
        String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
                "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
                "Android", "iPhone", "WindowsMobile" };
        for (int i = 0; i < values.length; ++i) {
            cursor.addRow(new Object[]{i, values[i], "description..."});
        }
        return cursor;
    }

}
