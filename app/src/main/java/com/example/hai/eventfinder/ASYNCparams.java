package com.example.hai.eventfinder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Van on 3/19/2017.
 */

//This class is here because I can only pass one paramter to the async task but I need multiple things
public class ASYNCparams {

   public ViewHolder viewHolder;

   public Context context; //needed for R class(API key)

   public myFoldingCellListAdapter adapter;


    public String longitude;
    public String latitude;

    public ArrayList<Event> events;

    public ASYNCparams(int i , ArrayList<Event> es, Context c , myFoldingCellListAdapter a){
        this.events = es;
        this.adapter = a;
        this.context = c;
        this.latitude = "40.689249";
        this.longitude= "-74.044500";
    }

    public ASYNCparams(int i , ArrayList<Event> es, Context c , myFoldingCellListAdapter a, String lat , String lon){
        this.events = es;
        this.adapter = a;
        this.context = c;
        this.latitude = lat;
        this.longitude= lon;
    }

}
