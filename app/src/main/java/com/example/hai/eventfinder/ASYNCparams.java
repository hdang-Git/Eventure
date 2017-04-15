package com.example.hai.eventfinder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Van on 3/19/2017.
 */

//This class is here because I can only pass one paramter to the async task but I need multiple things
public class ASYNCparams {
    public int JSONdrill;           //position of event in folding cell
    public ViewHolder viewHolder;   //viewholder that has reference to ui elements
    public Context context;         //context of current fragment
    public Event event;             //event data

    public ASYNCparams(int i , ViewHolder vh , Context c , Event e){
        this.JSONdrill = i;
        this.viewHolder= vh;
        this.context = c;
        this.event = e;
    }

}
