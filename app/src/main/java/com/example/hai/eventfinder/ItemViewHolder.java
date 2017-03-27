package com.example.hai.eventfinder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Hai on 3/22/2017.
 */

public class ItemViewHolder extends RecyclerView.ViewHolder{
    public final View rootView;
    public final ImageView imgItem;
    public final TextView eventItem;
    public final TextView eventPrice;
    public final TextView eventTime;
    public final TextView eventLocation;

    public ItemViewHolder(View view) {
        super(view);

        rootView = view;
        imgItem = (ImageView) view.findViewById(R.id.searchImage);
        eventItem = (TextView) view.findViewById(R.id.searchEvent);
        eventPrice = (TextView) view.findViewById(R.id.searchPrice);
        eventTime = (TextView) view.findViewById(R.id.searchTime);
        eventLocation = (TextView) view.findViewById(R.id.searchLocation);
    }
}
