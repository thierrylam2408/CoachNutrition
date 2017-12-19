package com.example.lam.coachnutrition;

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
                        EditText calorieText = (EditText) view.findViewById(R.id.calories);
                        if (nameText.getText().toString().trim().equals("") || calorieText.getText().toString().equals("")) {
                            Toast.makeText(getActivity(), "Les champs ne sont pas remplis", Toast.LENGTH_LONG).show();
                        } else {
                            Spinner foodTypeSpinner = (Spinner) view.findViewById(R.id.type);
                            String name = nameText.getText().toString();
                            Float calorie = Float.parseFloat(calorieText.getText().toString());
                            String type = foodTypeSpinner.getSelectedItem().toString();
                            Food food = new Food(name, type, calorie);
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
