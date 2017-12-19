package com.example.coachnutrition;

import android.content.ContentValues;

public class FoodCategory implements ModelValues {

    private static int counter_id = 0;

    private int id;
    private String name;

    public FoodCategory(String _name) {
        id = ++counter_id;
        name = _name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "FoodCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public ContentValues getValues() {
        ContentValues values = new ContentValues();

        values.put(BaseInformation.FoodCategoryEntry.COLUMN_NAME, name);

        return values;
    }

}
