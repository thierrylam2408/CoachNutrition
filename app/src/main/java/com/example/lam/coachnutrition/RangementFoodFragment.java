package com.example.lam.coachnutrition;

import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;


public class RangementFoodFragment extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private OnDialogInteraction mListener;

    public RangementFoodFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static RangementFoodFragment newInstance() {
        RangementFoodFragment fragment = new RangementFoodFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_rangement_ingredient, container, false);
        final Spinner spinner = (Spinner) v.findViewById(R.id.spinner);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.type_rangement_ingredient));
        spinner.setAdapter(spinnerAdapter);
        Button annulerB = (Button) v.findViewById(R.id.annuler);
        Button confirmerB = (Button) v.findViewById(R.id.confirmer);
        final RadioButton croissant = (RadioButton) v.findViewById(R.id.croissant);
        final RadioButton decroissant = (RadioButton) v.findViewById(R.id.decroissant);

        annulerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        confirmerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean alphabet;
                boolean calorie;
                if (spinner.getSelectedItemPosition() == 0) {
                    alphabet = true;
                    calorie = false;
                } else {
                    alphabet = false;
                    calorie = true;
                }
                boolean croissant = !decroissant.isChecked();
                onButtonPressed(alphabet, calorie, croissant);
                dismiss();
            }
        });
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(boolean alphabet, boolean calorie, boolean croissant) {
        if (mListener != null) {
            mListener.onDialogInteraction(alphabet, calorie, croissant);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDialogInteraction) {
            mListener = (OnDialogInteraction) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDialogInteraction");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnDialogInteraction {
        // TODO: Update argument type and name
        void onDialogInteraction(boolean alphabet, boolean calorie, boolean croissant);
    }
}
