package com.example.lam.coachnutrition;

import android.content.Context;
import android.database.Cursor;
import android.widget.CursorAdapter;

/**
 * Created by LAM on 29/11/2017.
 */
public class ModeAffichageIngredient {
    private boolean detail;
    private boolean name;
    private boolean calorie;
    private boolean croissant;
    private Context context;


    public ModeAffichageIngredient(Context context){
        this.context = context;
        initBooleanAffichage();
    }

    public ModeAffichageIngredient(Context context, boolean detail, boolean name, boolean calorie, boolean croissant){
        this.context = context;
        this.detail = detail;
        this.name = name;
        this.calorie = calorie;
        this.croissant = croissant;
    }

    public void initBooleanAffichage(){
        detail = false;
        name = true;
        calorie = false;
        croissant = true;
    }

    public CursorAdapter getAdapter(Cursor query){
        if(detail)
            return AdapterProvider.getTwoItemAdapter(context, query);
        else return AdapterProvider.getOneItemAdapter(context, query);
    }

    public String[] getColumnsCursor(){
        String[] notDetailedColumns = {AccessProvider.columnId, AccessProvider.columnName};
        String[] detailedColumns = {AccessProvider.columnId, AccessProvider.columnName, AccessProvider.columnCalorie};
        if(!detail)
            return notDetailedColumns;
        else return detailedColumns;
    }

    public String getOrderOrientation(){
        if(croissant)
            return "ASC";
        else return "DESC";
    }

    public String getOrderElement(){
        if(name)
            return AccessProvider.columnName;
        else return AccessProvider.columnCalorie;
    }

    public void changeDetail(){
        detail = !detail;
    }

    public void setName(boolean n){
        name = n;
    }

    public void setCalorie(boolean c){
        calorie = c;
    }

    public void setCroissant(boolean c){
        croissant = c;
    }

    public boolean getDetail(){
        return detail;
    }

    public boolean getName(){
        return name;
    }

    public boolean getCalorie(){
        return calorie;
    }

    public boolean getCroissant(){
        return croissant;
    }

}
