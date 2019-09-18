package com.example.tutor_inteligente;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.util.Timer;
import java.util.TimerTask;

public class TutorFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView description_exercise;
    private TextView number_exercise;
    private MaterialButton txt_time;
    private MaterialButton txt_errors;
    private MaterialButton txt_helps;
    private TextView msg_errors;
    private OnFragmentInteractionListener mListener;
    public Boolean status = false;

    public TutorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tutor, container, false);
        description_exercise = view.findViewById(R.id.description_exercise);
        number_exercise = view.findViewById(R.id.number_exercise);
        txt_time = view.findViewById(R.id.txt_time);
        txt_errors = view.findViewById(R.id.txt_errors);
        msg_errors = view.findViewById(R.id.msg_errors);
        txt_helps = view.findViewById(R.id.txt_help);
        status = true;
        // Inflate the layout for this fragment
        return view;
    }

    public void setDescription_exercise(String exercise) {
        description_exercise.setText(exercise);
    }

    public void setNumber_exercise(Integer number) {
        number_exercise.setText("Ejercicio " + number + ":");
    }

    public void setTextError(Integer number) {
        txt_errors.setText("ERRORES: " + number);
    }

    private int tiempo;

    public void setTextTime() {
        tiempo = 0;
        Timer contador = new Timer("contadorTiempo");
        contador.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                tiempo++;
                txt_time.setText("TIEMPO: " + tiempo);
            }
        }, 1000, 1000);
    }

    public int getTextTime(){
        return tiempo;
    }


    public void setTextHelps(Integer number) {
        txt_helps.setText("AYUDAS: " + number);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
