package com.example.coachnutrition;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class AlterFoodDialogFragment extends DialogFragment {

    private EditText nameText, caloriesText, lipidesText, glucidesText, proteinesText;
    private Spinner spinner;
    private Cursor cursor;
    private Food food;

    public static AlterFoodDialogFragment AlterFoodDialogFragmentWithCursor(Cursor _cursor) {
        AlterFoodDialogFragment fragment = new AlterFoodDialogFragment();
        fragment.setCursor(_cursor);
        fragment.buildFood();
        return fragment;
    }


    protected Cursor getCursor() {
        return this.cursor;
    }

    private void setCursor(Cursor _cursor) {
        this.cursor = _cursor;
    }

    private void buildFood() {
        food = new Food(
                this.cursor.getInt(this.cursor
                        .getColumnIndex(BaseInformation.FoodEntry._ID)),
                this.cursor.getString(this.cursor
                        .getColumnIndex(BaseInformation.FoodEntry.COLUMN_NAME)),
                this.cursor.getString(this.cursor
                        .getColumnIndex(BaseInformation.FoodEntry.COLUMN_CATEGORY)),
                this.cursor.getFloat(this.cursor
                        .getColumnIndex(BaseInformation.FoodEntry.COLUMN_COLORIES)),
                this.cursor.getFloat(this.cursor
                        .getColumnIndex(BaseInformation.FoodEntry.COLUMN_LIPIDES)),
                this.cursor.getFloat(this.cursor
                        .getColumnIndex(BaseInformation.FoodEntry.COLUMN_GLUCIDES)),
                this.cursor.getFloat(this.cursor
                        .getColumnIndex(BaseInformation.FoodEntry.COLUMN_PROTEINES))
        );
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final FoodActivity foodActivity = (FoodActivity) getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(foodActivity);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_alter_food_dialog, null);
        final AccessProvider accessProvider =
                new AccessProvider(AlterFoodDialogFragment.this.getActivity());

        nameText = (EditText) view.findViewById(R.id.name);
        caloriesText = (EditText) view.findViewById(R.id.calories);
        lipidesText = (EditText) view.findViewById(R.id.lipides);
        glucidesText = (EditText) view.findViewById(R.id.glucides);
        proteinesText = (EditText) view.findViewById(R.id.proteines);
        spinner = (Spinner) view.findViewById(R.id.spinner);


        nameText.setText(food.getName());
        caloriesText.setText("" + food.getCalories());
        lipidesText.setText("" + food.getLipides());
        glucidesText.setText("" + food.getGlucides());
        proteinesText.setText("" + food.getProteines());


        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.confirmer, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        Float calories = null, lipides = null, glucides = null, proteines = null;

                        if (nameText.getText().toString().trim().equals("") || caloriesText.getText().toString().equals("")) {
                            Toast.makeText(getActivity(), "Les champs ne sont pas remplis", Toast.LENGTH_LONG).show();
                        } else {
                            Spinner foodCategorySpinner = (Spinner) view.findViewById(R.id.category);
                            String name = nameText.getText().toString();
                            calories = Float.parseFloat(caloriesText.getText().toString());

                            if (!lipidesText.getText().toString().equals("")) {
                                lipides = Float.parseFloat(lipidesText.getText().toString());
                            }

                            if (!glucidesText.getText().toString().equals("")) {
                                glucides = Float.parseFloat(glucidesText.getText().toString());
                            }

                            if (!proteinesText.getText().toString().equals("")) {
                                proteines = Float.parseFloat(proteinesText.getText().toString());
                            }

                            String type = foodCategorySpinner.getSelectedItem().toString();
                            Food food = new Food(name, type, calories, lipides, glucides, proteines);
                            accessProvider.alterFood(food);
                            ((FoodActivity) AlterFoodDialogFragment.this.getActivity()).onDialogDismiss();
                        }
                    }
                })
                .setNegativeButton(R.string.annuler, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AlterFoodDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
