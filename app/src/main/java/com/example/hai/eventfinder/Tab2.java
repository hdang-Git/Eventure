package com.example.hai.eventfinder;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.logging.Logger;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab2 extends Fragment {
    Logger log = Logger.getAnonymousLogger();

    public Tab2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab2, container, false);
    }

    public static Tab2 newInstance(){
        Tab2 tab2 = new Tab2();
        return tab2;
    }

    //test stub
    public void setNumber(int num){
        log.info("Number is: " + num);
    }


}
