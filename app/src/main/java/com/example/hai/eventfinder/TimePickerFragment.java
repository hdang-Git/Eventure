package com.example.hai.eventfinder;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.text.format.DateFormat;
import java.util.Calendar;//This library was imported instead of android because of API requirement
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 * Created by Lenny Ramos on 3/20/2017.
 */

public class TimePickerFragment extends DialogFragment
                            implements TimePickerDialog.OnTimeSetListener{

    public TimePickerFragment(){
        //empty constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        //using current time as default values
        //since event will occur after or on the day of creation

        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute){
         //Do something with the time chosen by user
         //convert to strings and store them in the database

        EditText timeText = (EditText) getActivity().findViewById(R.id.timeEdit);
        timeText.setText(view.getCurrentHour() + " : " + view.getCurrentMinute());
    }
}
