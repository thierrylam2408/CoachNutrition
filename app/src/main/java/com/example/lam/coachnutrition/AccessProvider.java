package com.example.lam.coachnutrition;


import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;


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

    public void displaySelectFood(String _name) {
        String selection = BaseInformation.FoodEntry.COLUMN_NAME + " = ?";
        String selectionArgs[] = {_name};
        displayFood(selection, selectionArgs);
    }

    public void displayAllFood() {
        displayFood(null, null);
    }

    public void displayFood(String selection, String selectionArgs[]) {
        Cursor cur = this.contentResolver.query(BaseInformation.CONTENT_URI_FOOD, BaseInformation.FoodEntry.columns,
                selection, selectionArgs, null);

        if (cur.moveToFirst()) {

            do {
                int id = cur.getInt(cur.getColumnIndex(BaseInformation.FoodEntry._ID));

                Food food = new Food(
                        cur.getString(cur.getColumnIndex(BaseInformation.FoodEntry.COLUMN_NAME)),
                        cur.getString(cur.getColumnIndex(BaseInformation.FoodEntry.COLUMN_CATEGORY)),
                        cur.getFloat(cur.getColumnIndex(BaseInformation.FoodEntry.COLUMN_COLORIES)),
                        cur.getFloat(cur.getColumnIndex(BaseInformation.FoodEntry.COLUMN_LIPIDES)),
                        cur.getFloat(cur.getColumnIndex(BaseInformation.FoodEntry.COLUMN_GLUCIDES)),
                        cur.getFloat(cur.getColumnIndex(BaseInformation.FoodEntry.COLUMN_PROTEINES))
                );

                food.setId(id);
            } while (cur.moveToNext());
        }
    }

    public void insertFoodCategory(FoodCategory _foodCategory) {
        this.contentResolver.insert(BaseInformation.CONTENT_URI_FOOD_CATEGORY, _foodCategory.getValues());
    }

    public void insertMeal(Meal _meal) {
        this.contentResolver.insert(BaseInformation.CONTENT_URI_MEAL, _meal.getValues());
    }

    public void deleteFood(String name){
        String[] args = {name};
        this.contentResolver.delete(BaseInformation.CONTENT_URI_FOOD,
                BaseInformation.FoodEntry.COLUMN_NAME + "  = ? ", args);
        this.contentResolver.delete(BaseInformation.CONTENT_URI_MEAL,
                BaseInformation.MealEntry.COLUMN_FOOD + "  = ? ", args);
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