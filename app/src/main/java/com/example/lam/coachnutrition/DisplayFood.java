package com.example.lam.coachnutrition;

import android.content.Context;
import android.database.Cursor;
import android.widget.CursorAdapter;

/**
 * Created by LAM on 11/12/2017.
 */
public class DisplayFood {

    public static final boolean DEFAULT_DETAIL = false;
    public static final boolean DEFAULT_NAME = true;
    public static final boolean DEFAULT_CALORIE = false;
    public static final boolean DEFAULT_CROISSANT = true;

    private Context context;
    private boolean detail;
    private boolean name;
    private boolean calorie;
    private boolean croissant;
    private String type;

    public DisplayFood(Context context) {
        this.context = context;
        initBooleanDisplay();
    }

    public DisplayFood(Context context, boolean detail, boolean name, boolean calorie, boolean croissant, String type) {
        this.context = context;
        this.detail = detail;
        this.name = name;
        this.calorie = calorie;
        this.croissant = croissant;
        this.type = type;
    }

    public void initBooleanDisplay() {
        detail = DEFAULT_DETAIL;
        name = DEFAULT_NAME;
        calorie = DEFAULT_CALORIE;
        croissant = DEFAULT_CROISSANT;
    }

    public CursorAdapter getAdapter(Cursor query) {
        if (detail)
            return AdapterProvider.getTwoItemAdapter(context, query);
        else return AdapterProvider.getOneItemAdapter(context, query);
    }

    public String[] getColumnsCursor() {
        String[] notDetailedColumns = {BaseInformation.FoodEntry._ID, BaseInformation.FoodEntry.COLUMN_NAME};
        String[] detailedColumns = {BaseInformation.FoodEntry._ID, BaseInformation.FoodEntry.COLUMN_NAME, BaseInformation.FoodEntry.COLUMN_COLORIES};
        if (!detail)
            return notDetailedColumns;
        else return detailedColumns;
    }

    public String getOrderOrientation() {
        if (croissant)
            return "ASC";
        else return "DESC";
    }

    public String getOrderElement() {
        if (name)
            return BaseInformation.FoodEntry.COLUMN_NAME;
        else return BaseInformation.FoodEntry.COLUMN_COLORIES;
    }

    public void changeDetail() {
        detail = !detail;
    }

    public void setName(boolean n) {
        name = n;
    }

    public void setCalorie(boolean c) {
        calorie = c;
    }

    public void setCroissant(boolean c) {
        croissant = c;
    }

    public boolean getDetail() {
        return detail;
    }

    public boolean getName() {
        return name;
    }

    public boolean getCalorie() { return calorie; }

    public boolean getCroissant() { return croissant; }

    public String getType() { return type; }

    public void setType(String type){ this.type = type; }
}