package com.example.lam.coachnutrition;

/**
 * Created by LAM on 11/12/2017.
 */
import android.content.ContentValues;

import java.util.ArrayList;

public class Food implements ModelValues {

    private static int counter = 0;

    private int id;
    private String name;
    private float calorie;
    public int code_category;

    public Food(String _name, float _calorie, int _code_category) {
        id = ++counter;
        name = _name;
        calorie = _calorie;
        code_category = _code_category;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getCalorie() {
        return calorie;
    }

    public int getCode_category() {
        return code_category;
    }

    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", calorie=" + calorie +
                ", code_category=" + code_category +
                '}';
    }

    @Override
    public ContentValues getValues() {
        ContentValues values = new ContentValues();

        values.put(BaseInformation.FoodEntry.COLUMN_NAME, name);
        values.put(BaseInformation.FoodEntry.COLUMN_COLORIE, calorie);
        values.put(BaseInformation.FoodEntry.COLUMN_CATEGORY, code_category);

        return values;
    }

    @Override
    public ArrayList<ContentValues> getAllValues() {
        return null;
    }
}