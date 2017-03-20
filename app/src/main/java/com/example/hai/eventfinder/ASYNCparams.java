package com.example.hai.eventfinder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Van on 3/19/2017.
 */

//This class is here because I can only pass one paramter to the async task but I need two things: int + viewholder
public class ASYNCparams {
   public int JSONdrill;
   public ViewHolder viewHolder;

    public ASYNCparams(int i , ViewHolder vh){
        this.JSONdrill = i;
        this.viewHolder= vh;
    }

}
