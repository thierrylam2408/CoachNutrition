package com.example.lam.coachnutrition;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

import java.text.SimpleDateFormat;
import java.util.Calendar;


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
        Cursor cur = this.contentResolver.query(BaseInformation.CONTENT_URI_FOOD, BaseInformation.FoodEntry.COLUMNS,
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
        String[] args = {BaseInformation.MealEntry._ID, BaseInformation.MealEntry.COLUMN_CODE,
                BaseInformation.MealEntry.COLUMN_FOOD, BaseInformation.MealEntry.COLUMN_WEIGHT};
        String where = BaseInformation.MealEntry.COLUMN_CODE + " = " + _meal.getCode() + " and " +
                BaseInformation.MealEntry.COLUMN_FOOD + " = '" + _meal.getFood() + "'";
        Cursor cursor = this.query(args, where, BaseInformation.CONTENT_URI_MEAL);
        if (cursor.moveToFirst()) {
            ContentValues values = new ContentValues();
            values.put(BaseInformation.MealEntry.COLUMN_WEIGHT,
                    _meal.getWeigth() + cursor.getFloat(cursor.getColumnIndex(BaseInformation.MealEntry.COLUMN_WEIGHT)));
            where = BaseInformation.MealEntry.COLUMN_CODE + " = ? and " +
                    BaseInformation.MealEntry.COLUMN_FOOD + " = ?";
            String[] selectionArgs = {String.valueOf(_meal.getCode()), _meal.getFood()};
            this.contentResolver.update(BaseInformation.CONTENT_URI_MEAL, values, where, selectionArgs);
        } else
            this.contentResolver.insert(BaseInformation.CONTENT_URI_MEAL, _meal.getValues());
    }

    public void deleteFood(String name) {
        String[] args = {name};
        this.contentResolver.delete(BaseInformation.CONTENT_URI_FOOD,
                BaseInformation.FoodEntry.COLUMN_NAME + "  = ? ", args);
        this.contentResolver.delete(BaseInformation.CONTENT_URI_MEAL,
                BaseInformation.MealEntry.COLUMN_FOOD + "  = ? ", args);
    }

    public void deleteFood(String name, int codeMeal) {
        String[] args = {name, String.valueOf(codeMeal)};
        this.contentResolver.delete(BaseInformation.CONTENT_URI_MEAL,
                BaseInformation.MealEntry.COLUMN_FOOD + "  = ? and " +
                        BaseInformation.MealEntry.COLUMN_CODE + " = ? ", args);
    }

    public void deleteMeal(int codeMeal) {
        String[] args = {String.valueOf(codeMeal)};
        this.contentResolver.delete(BaseInformation.CONTENT_URI_MEAL,
                BaseInformation.MealEntry.COLUMN_CODE + " = ? ", args);
    }

    public Cursor getMealByDay(Calendar cal) {
        String[] columns = {BaseInformation.MealEntry._ID,
                BaseInformation.MealEntry.COLUMN_CODE,
                BaseInformation.MealEntry.COLUMN_NAME,
                BaseInformation.MealEntry.COLUMN_TIMESTAMP};
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String currentDay = format.format(cal.getTime());
        cal.add(Calendar.DAY_OF_YEAR, 1);
        String nextDay = format.format(cal.getTime());
        cal.add(Calendar.DAY_OF_YEAR, -1);
        return query(
                columns,
                BaseInformation.MealEntry.COLUMN_NAME + " != 'None' and " +
                        BaseInformation.MealEntry.COLUMN_TIMESTAMP + " BETWEEN '" +
                        currentDay + "' AND '" + nextDay + "'",
                BaseInformation.MealEntry.COLUMN_TIMESTAMP,
                "ASC",
                BaseInformation.CONTENT_URI_MEAL);
    }

    public Float countCaloriesByMeal(int codeMeal) {
        Float sum = 0F;
        String[] selection = {
                BaseInformation.MealEntry.COLUMN_CODE,
                BaseInformation.MealEntry.COLUMN_FOOD,
                BaseInformation.MealEntry.COLUMN_WEIGHT};
        Cursor c = query(selection,
                BaseInformation.MealEntry.COLUMN_CODE + " = " + codeMeal,
                BaseInformation.CONTENT_URI_MEAL);
        while (c.moveToNext()) {
            sum += calorieByFood(c.getString(c.getColumnIndex(BaseInformation.MealEntry.COLUMN_FOOD))) *
                    c.getFloat(c.getColumnIndex(BaseInformation.MealEntry.COLUMN_WEIGHT));
        }
        return sum;
    }

    private int calorieByFood(String food) {
        String[] selection = {
                BaseInformation.FoodEntry.COLUMN_NAME,
                BaseInformation.FoodEntry.COLUMN_COLORIES
        };
        Cursor c = query(selection,
                BaseInformation.FoodEntry.COLUMN_NAME + " = '" + food + "'",
                BaseInformation.CONTENT_URI_FOOD);
        if (!c.moveToFirst())
            return 0;
        return c.getInt(c.getColumnIndex(BaseInformation.FoodEntry.COLUMN_COLORIES));
    }

    public Float countCalories(Calendar day) {
        Float sum = 0F;
        Cursor c = getMealByDay(day);
        while (c.moveToNext()) {
            sum += countCaloriesByMeal(c.getInt(c.getColumnIndex(BaseInformation.MealEntry.COLUMN_CODE)));
        }
        return sum;
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