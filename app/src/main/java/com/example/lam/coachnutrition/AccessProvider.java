package com.example.lam.coachnutrition;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

import java.util.ArrayList;

public class AccessProvider {

    private ContentResolver contentResolver;
    private Context context;

    public AccessProvider(Context context) {
        this.context = context;
        this.contentResolver = this.context.getContentResolver();
    }

    public void insertFood(Food _food) {
        this.contentResolver.insert(BaseInformation.CONTENT_URI_FOOD, _food.getValues());
    }

    public void insertFoodCategory(FoodCategory _foodCategory) {
        ArrayList<ContentValues> values = _foodCategory.getAllValues();

        for (int i = 0; i < values.size(); i++) {
            this.contentResolver.insert(BaseInformation.CONTENT_URI_FOOD_CATEGORY, values.get(i));
        }
    }

    public void insertMeal(Meal _meal) {
        ArrayList<ContentValues> values = _meal.getAllValues();

        for (int i = 0; i < values.size(); i++) {
            this.contentResolver.insert(BaseInformation.CONTENT_URI_MEAL, values.get(i));
        }
    }

    public Cursor query(String[] select, Uri uri) {
        return query(select, null, null, null, uri);
    }

    public Cursor query(String[] select, String where, Uri uri) {
        return query(select, where, null, null, uri);
    }

    public Cursor query(String[] select, String elementOrder, String croissant, Uri uri) {
        return query(select, null, elementOrder, croissant, uri);
    }

    public Cursor query(String[] select, String where, String elementOrder, String croissant, Uri uri) {
        if (elementOrder != null && croissant != null)
            return this.contentResolver.query(uri, select, where, null,
                    elementOrder + " " + croissant);
        else return this.contentResolver.query(uri, select, where, null, null);
    }

    public void deleteFood(String name){
        String[] args = {name};
        this.contentResolver.delete(BaseInformation.CONTENT_URI_FOOD,
                BaseInformation.FoodEntry.COLUMN_NAME + "  = ? ", args);
    }

    public int getCodeTypeFood(String typeFood){
        String[] column = {BaseInformation.FoodCategoryEntry.COLUMN_CODE};
        Cursor cursor = this.query(column,
        BaseInformation.FoodCategoryEntry.COLUMN_NAME + " = " +typeFood,
        BaseInformation.CONTENT_URI_FOOD_CATEGORY);
        return cursor.getInt(0);
    }

    private static Cursor getFakeCursor() {
        String[] columns = new String[]{"_id", "item", "description"};
        MatrixCursor cursor = new MatrixCursor(columns);
        String[] values = new String[]{"Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
                "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
                "Android", "iPhone", "WindowsMobile"};
        for (int i = 0; i < values.length; ++i) {
            cursor.addRow(new Object[]{i, values[i], "description..."});
        }
        return cursor;
    }
}