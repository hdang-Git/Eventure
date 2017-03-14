package com.example.hai.eventfinder;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class CellFragment extends Fragment {


    public CellFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("test1" , "we are here");
        View v = inflater.inflate(R.layout.cell , container , false);

        /*
        Event testEvent = new Event();

        testEvent.requestEvent();

        TextView title = (TextView) v.findViewById(R.id.testing);

        title.setText(testEvent.eventName);

        Log.d("test2" , testEvent.eventName);
        */

        //Toast.makeText(getActivity().getApplicationContext() , testEvent.eventName , Toast.LENGTH_SHORT).show();
        Toast.makeText(getActivity().getApplicationContext() , "hai", Toast.LENGTH_SHORT).show();


        //test = (Button) v.findViewById(R.id.)

        return v;
    }

}
