package com.example.coachnutrition;

import android.content.ContentValues;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Meal implements ModelValues {

    private int code;
    private String name;
    private String food;
    private float weigth;
    private Timestamp time;

    public Meal(int code, String name, Timestamp time) {
        this.code = code;
        this.name = name;
        food = "None";
        weigth = 0;
        this.time = time;
    }

    public Meal(int code, String food, float weigth) {
        this.code = code;
        this.name = "None";
        this.food = food;
        this.weigth = weigth;
        time = new Timestamp(System.currentTimeMillis());
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getFood() {
        return food;
    }

    public float getWeigth() {
        return weigth;
    }

    public Timestamp getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "code=" + code +
                ", name='" + name + '\'' +
                ", food=" + food +
                ", weigth=" + weigth +
                ", time=" + time +
                '}';
    }

    @Override
    public ContentValues getValues() {
        ContentValues values = new ContentValues();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = format.format(time.getTime());
        values.put(BaseInformation.MealEntry.COLUMN_CODE, code);
        values.put(BaseInformation.MealEntry.COLUMN_NAME, name);
        values.put(BaseInformation.MealEntry.COLUMN_FOOD, food);
        values.put(BaseInformation.MealEntry.COLUMN_WEIGHT, weigth);
        values.put(BaseInformation.MealEntry.COLUMN_TIMESTAMP, date);
        return values;
    }
}
