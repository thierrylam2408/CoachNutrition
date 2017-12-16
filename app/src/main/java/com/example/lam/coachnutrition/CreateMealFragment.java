package com.example.lam.coachnutrition;

import android.app.DialogFragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;




/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateMealFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateMealFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateMealFragment extends DialogFragment {

    private OnFragmentInteractionListener mListener;

    public CreateMealFragment() {

    }


    // TODO: Rename and change types and number of parameters
    public static CreateMealFragment newInstance() {
        CreateMealFragment fragment = new CreateMealFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_create_meal, container, false);
        final Button annulerB = (Button) v.findViewById(R.id.annuler);
        Button confirmerB = (Button) v.findViewById(R.id.confirmer);

        final TimePicker timePicker = (TimePicker) v.findViewById(R.id.tp);
        timePicker.setIs24HourView(true);
        final EditText editText = (EditText) v.findViewById(R.id.name);

        annulerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        confirmerB.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String nom = editText.getText().toString();
                int hours = timePicker.getHour();
                int mins = timePicker.getMinute();
                if(nom.trim().equals("")){
                    editText.setError("Le nom est necessaire!");
                }else{
                    onButtonPressed(nom, hours, mins);
                    dismiss();
                }
            }
        });
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String nom, int hours, int mins) {
        if (mListener != null) {
            mListener.onFragmentInteraction(nom, hours, mins);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String nom, int hours, int mins);
    }
}
