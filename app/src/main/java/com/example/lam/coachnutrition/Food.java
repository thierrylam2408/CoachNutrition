package com.example.lam.coachnutrition;

/**
 * Created by LAM on 11/12/2017.
 */

import android.content.ContentValues;

public class Food implements ModelValues {

    private static int counter_id = 0;

    private int id;
    private String name;
    private String category;
    private Float calories, lipides, glucides, proteines;


    public Food(String _name, String _category, Float _calories) {
        id = ++counter_id;
        name = _name;
        category = _category;
        calories = _calories;
        lipides = null;
        glucides = null;
        proteines = null;
    }

    public Food(String _name, String _category, Float _calorie, Float _lipides) {
        id = ++counter_id;
        name = _name;
        category = _category;
        calories = _calorie;
        lipides = _lipides;
        glucides = null;
        proteines = null;
    }

    public Food(String _name, String _category, Float _calorie, Float _lipides, Float _glucides) {
        id = ++counter_id;
        name = _name;
        category = _category;
        calories = _calorie;
        lipides = _lipides;
        glucides = _glucides;
        proteines = null;
    }

    public Food(String _name, String _category, Float _calorie,
                Float _lipides, Float _glucides, Float _protéines) {
        id = ++counter_id;
        name = _name;
        category = _category;
        calories = _calorie;
        lipides = _lipides;
        glucides = _glucides;
        proteines = _protéines;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public Float getCalories() {
        return calories;
    }

    public Float getLipides() {
        return lipides;
    }

    public Float getGlucides() {
        return glucides;
    }

    public Float getProteines() {
        return proteines;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCalories(Float calories) {
        this.calories = calories;
    }

    public void setLipides(Float lipides) {
        this.lipides = lipides;
    }

    public void setGlucides(Float glucides) {
        this.glucides = glucides;
    }

    public void setProteines(Float proteines) {
        this.proteines = proteines;
    }

    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", calories=" + calories +
                ", lipides=" + lipides +
                ", glucides=" + glucides +
                ", proteines=" + proteines +
                '}';
    }

    @Override
    public ContentValues getValues() {
        ContentValues values = new ContentValues();

        values.put(BaseInformation.FoodEntry.COLUMN_NAME, name);
        values.put(BaseInformation.FoodEntry.COLUMN_CATEGORY, category);
        values.put(BaseInformation.FoodEntry.COLUMN_COLORIES, calories);

        if(lipides != null) {
            values.put(BaseInformation.FoodEntry.COLUMN_LIPIDES, lipides);
        }

        if(glucides != null) {
            values.put(BaseInformation.FoodEntry.COLUMN_GLUCIDES, glucides);
        }

        if(proteines != null) {
            values.put(BaseInformation.FoodEntry.COLUMN_PROTEINES, calories);
        }

        return values;
    }

}