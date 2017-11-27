package com.example.lam.coachnutrition;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;

/**
 * Created by LAM on 26/11/2017.
 */
public class AccessProvider {

    public final static String authority = "com.projet.contentprovider";

    public final static String tableIngredient = "food";
    public final static String columnId = "_id";
    public final static String columnName = "name";
    public final static String columnCalorie = "calorie";
    public final static String columnWeight = "weight";

    private ContentResolver contentResolver;
    private Context context;

    public AccessProvider(Context context){
        this.context = context;
        this.contentResolver = this.context.getContentResolver();
    }

    public void insertIngredient(String name, float calories, float weight){
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("content").authority(authority).appendPath(tableIngredient);
        Uri uri = builder.build();
        ContentValues values = new ContentValues();
        values.put(columnName, name);
        values.put(columnCalorie, calories);
        values.put(columnWeight, weight);
        uri = this.contentResolver.insert(uri, values);
    }

    public Cursor query(String[] select, String where){
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("content").authority(authority).appendPath(tableIngredient);
        Uri uri = builder.build();
        return this.contentResolver.query(uri, select, where, null, null);
    }

    public Cursor query(String[] select){
        return query(select, null);
    }

    private static Cursor getFakeCursor(){
        String[] columns = new String[] { "_id", "item", "description" };
        MatrixCursor cursor = new MatrixCursor(columns);
        String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
                "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
                "Android", "iPhone", "WindowsMobile" };
        for (int i = 0; i < values.length; ++i) {
            cursor.addRow(new Object[]{i, values[i], "description..."});
        }
        return cursor;
    }

}
