package com.example.coachnutrition;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdaptaterFood extends ArrayAdapter<Food> {

    private final ArrayList<Food> objects;

    public CustomAdaptaterFood(@NonNull Context context, int resource, @NonNull ArrayList<Food> objects) {
        super(context, resource, objects);
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.food_rows, null);
        }
        Food i = objects.get(position);

        if (i != null) {
            TextView t1 = (TextView) v.findViewById(R.id.textView1);
            TextView t2 = (TextView) v.findViewById(R.id.textView2);

            if (t1 != null) {
                t1.setText("" + i.getCalories());
            }

            if (t2 != null) {
                t2.setText(BaseInformation.FoodEntry.COLUMN_COLORIES);
            }

        }
        return v;
    }
}
