package com.example.lam.coachnutrition;

/**
 * Created by LAM on 11/12/2017.
 */
import android.content.ContentValues;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Meal implements ModelValues {

    private static int counter_id = 0;
    private static int counter_values = 0;

    private int id;
    private int code;
    private String name;
    private String food;
    private float weigth;
    private Timestamp time;

    public Meal(String name, Timestamp time){
        id = ++counter_id;
        code = id;
        this.name = name;
        food = "None";
        weigth = 0;
        this.time = time;
    }

    public Meal(int code, String food, float weigth){
        id = ++counter_id;
        this.code = code;
        this.name = "None";
        this.food = food;
        this.weigth = weigth;
        time = new Timestamp(System.currentTimeMillis());
    }

    public Meal(String _name, String _food, float _weigth) {
        id = ++counter_id;
        code = id;
        name = _name;
        food = _food;
        weigth = _weigth;
        time = new Timestamp(System.currentTimeMillis());
    }

    public Meal(int _code, String _name, String _food, float _weigth) {
        id = _code;
        code = _code;
        name = _name;
        food = _food;
        weigth = _weigth;
        time = new Timestamp(System.currentTimeMillis());
    }

    public int getId() {
        return id;
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
                "id=" + id +
                ", code=" + code +
                ", name='" + name + '\'' +
                ", food=" + food +
                ", weigth=" + weigth +
                ", time=" + time +
                '}';
    }

    @Override
    public ContentValues getValues() {
        ContentValues values = new ContentValues();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date = format.format(time.getTime());
        values.put(BaseInformation.MealEntry.COLUMN_CODE, code);
        values.put(BaseInformation.MealEntry.COLUMN_NAME, name);
        values.put(BaseInformation.MealEntry.COLUMN_FOOD, food);
        values.put(BaseInformation.MealEntry.COLUMN_WEIGHT, weigth);
        values.put(BaseInformation.MealEntry.COLUMN_TIMESTAMP, date);
        return values;
    }
}
