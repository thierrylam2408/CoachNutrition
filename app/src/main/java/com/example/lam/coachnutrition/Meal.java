package com.example.lam.coachnutrition;

/**
 * Created by LAM on 11/12/2017.
 */
import android.content.ContentValues;

import java.util.ArrayList;
import java.util.Date;

public class Meal implements ModelValues {

    private static int counter_id = 0;
    private static int counter_values = 0;

    private int id;
    private int code;
    private ArrayList<Integer> code_food;
    private String name;
    private float weigth;
    private Date date;

    public Meal(int _code_food, String _name, float _weigth, Date _date) {
        id = ++counter_id;
        code = id;
        (code_food = new ArrayList<>()).add(_code_food);
        name = _name;
        weigth = _weigth;
        date = _date;
    }

    public Meal(int _code, int _code_food, String _name, float _weigth, Date _date) {
        id = _code;
        code = _code;
        (code_food = new ArrayList<>()).add(_code_food);
        name = _name;
        weigth = _weigth;
        date = _date;
    }

    public Meal(int _code, ArrayList<Integer> _code_food, String _name, float _weigth, Date _date) {
        id = _code;
        code = _code;
        code_food = _code_food;
        name = _name;
        weigth = _weigth;
        date = _date;
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

    public ArrayList<Integer> getCode_food() {
        return code_food;
    }

    public float getWeigth() {
        return weigth;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", code=" + code +
                ", code_food=" + code_food +
                ", name='" + name + '\'' +
                ", weigth=" + weigth +
                ", date=" + date +
                '}';
    }

    @Override
    public ContentValues getValues() {
        ContentValues values = new ContentValues();

        values.put(BaseInformation.MealEntry.COLUMN_CODE, code);
        values.put(BaseInformation.MealEntry.COLUMN_FOOD, code_food.get(++counter_values));
        values.put(BaseInformation.MealEntry.COLUMN_NAME, name);
        values.put(BaseInformation.MealEntry.COLUMN_WEIGHT, weigth);

        return values;
    }

    @Override
    public ArrayList<ContentValues> getAllValues() {
        ArrayList<ContentValues> list = new ArrayList<>();

        for (int i = 0; i < code_food.size(); i++) {
            list.add(getValues());
        }
        counter_values = 0;

        return list;
    }
}
