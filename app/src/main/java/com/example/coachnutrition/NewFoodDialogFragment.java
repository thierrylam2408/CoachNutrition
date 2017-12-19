package com.example.coachnutrition;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class NewFoodDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.new_food_dialog, null);
        final AccessProvider accessProvider = new AccessProvider(NewFoodDialogFragment.this.getActivity());
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.confirmer, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText nameText = (EditText) view.findViewById(R.id.name);
                        EditText caloriesText = (EditText) view.findViewById(R.id.calories);
                        EditText lipidesText = (EditText) view.findViewById(R.id.lipides);
                        EditText glucidesText = (EditText) view.findViewById(R.id.glucides);
                        EditText proteinesText = (EditText) view.findViewById(R.id.proteines);

                        Float calories, lipides = null, glucides = null, proteines = null;

                        if (nameText.getText().toString().trim().equals("") || caloriesText.getText().toString().equals("")) {
                            Toast.makeText(getActivity(), "Les champs ne sont pas remplis", Toast.LENGTH_LONG).show();
                        } else {
                            Spinner foodCategorySpinner = (Spinner) view.findViewById(R.id.category);
                            String name = nameText.getText().toString();
                            calories = Float.parseFloat(caloriesText.getText().toString());

                            if(!lipidesText.getText().toString().equals("")) {
                                lipides = Float.parseFloat(lipidesText.getText().toString());
                            }

                            if(!glucidesText.getText().toString().equals("")) {
                                glucides = Float.parseFloat(glucidesText.getText().toString());
                            }

                            if(!proteinesText.getText().toString().equals("")) {
                                proteines = Float.parseFloat(proteinesText.getText().toString());
                            }

                            String category = foodCategorySpinner.getSelectedItem().toString();

                            Food food = new Food(name, category, calories, lipides, glucides, proteines);
                            accessProvider.insertFood(food);
                            ((FoodActivity) NewFoodDialogFragment.this.getActivity()).onDialogDismiss();
                        }
                    }
                })
                .setNegativeButton(R.string.annuler, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        NewFoodDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
