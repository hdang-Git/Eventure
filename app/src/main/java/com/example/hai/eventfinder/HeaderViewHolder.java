package com.example.hai.eventfinder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Hai on 3/22/2017.
 */

class HeaderViewHolder extends RecyclerView.ViewHolder {

    public final TextView tvTitle;

    public HeaderViewHolder(View view) {
        super(view);

        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
    }
}