package com.example.lam.coachnutrition;

/**
 * Created by LAM on 11/12/2017.
 */

import android.content.ContentValues;

import java.util.ArrayList;

public interface ModelValues {

    ContentValues getValues();

    ArrayList<ContentValues> getAllValues();
}