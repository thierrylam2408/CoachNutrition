package com.example.lam.coachnutrition;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by LAM on 25/11/2017.
 */
public class NewIngredientDialogFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Get the layout inflater
            LayoutInflater inflater = getActivity().getLayoutInflater();
            final View view = inflater.inflate(R.layout.new_ingredient_dialog, null);
            final AccessProvider accessProvider = new AccessProvider(NewIngredientDialogFragment.this.getActivity());
            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(view)
                    // Add action buttons
                    .setPositiveButton(R.string.confirmer, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            EditText name = (EditText)view.findViewById(R.id.name);
                            EditText calorie = (EditText)view.findViewById(R.id.calorie);
                            accessProvider.insertIngredient(name.getText().toString(), Float.parseFloat(calorie.getText().toString()), 0f);
                            ((IngredientActivity) NewIngredientDialogFragment.this.getActivity()).onDialogDismiss();

                        }
                    })
                    .setNegativeButton(R.string.annuler, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            NewIngredientDialogFragment.this.getDialog().cancel();
                        }
                    });
            return builder.create();
        }
    }
