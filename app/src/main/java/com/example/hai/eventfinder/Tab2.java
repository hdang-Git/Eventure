package com.example.hai.eventfinder;


import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.logging.Logger;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab2 extends Fragment {
    Logger log = Logger.getAnonymousLogger();

    Button timeButton;
    Button dateButton;

    //This is references itself so that we do not have to make
    //the methods inside the mainActivity
    Tab2 myself = this;

    public Tab2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab2, container, false);

        return view;
    }

    //this method is because of the life cycle,
    //to help avoid button referencing a null object
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        timeButton = (Button) getView().findViewById(R.id.pickTime);
        dateButton = (Button) getView().findViewById(R.id.pickDate);

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myself.showDatePickerDialog(view);
            }
        });

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myself.showTimePickerDialog(view);
            }
        });
    }

    public static Tab2 newInstance(){
        Tab2 tab2 = new Tab2();
        return tab2;
    }

    //test stub
    public void setNumber(int num){
        log.info("Number is: " + num);
    }


    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getActivity().getFragmentManager() , "timePicker");

    }


    public void showDatePickerDialog(View v){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getActivity().getFragmentManager(), "datePicker");
    }


}
