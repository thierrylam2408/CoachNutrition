package com.example.lam.coachnutrition;

/**
 * Created by LAM on 11/12/2017.
 */
import android.content.ContentValues;

import java.util.ArrayList;

public class FoodCategory implements ModelValues {

    private static int counter_id = 0;
    private static int counter_values = 0;

    private int id;
    private int code;
    private ArrayList<Integer> code_food;
    private String name;

    public FoodCategory(int _code_food, String _name) {
        id = ++counter_id;
        code = id;
        (code_food = new ArrayList<>()).add(_code_food);
        name = _name;
    }

    public FoodCategory(int _code, int _code_food, String _name) {
        id = _code;
        code = _code;
        (code_food = new ArrayList<>()).add(_code_food);
        name = _name;
    }

    public FoodCategory(int _code, ArrayList<Integer> _code_food, String _name) {
        id = _code;
        code = _code;
        code_food = _code_food;
        name = _name;
    }

    public int getId() {
        return id;
    }

    public int getCode() {
        return code;
    }

    public ArrayList<Integer> getCode_food() {
        return code_food;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "FoodCategory{" +
                "id=" + id +
                ", code=" + code +
                ", code_food=" + code_food +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public ContentValues getValues() {
        ContentValues values = new ContentValues();

        values.put(BaseInformation.FoodCategoryEntry.COLUMN_CODE, code);
        values.put(BaseInformation.FoodCategoryEntry.COLUMN_CODE_FOOD, code_food.get(++counter_values));
        values.put(BaseInformation.FoodCategoryEntry.COLUMN_NAME, name);

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
