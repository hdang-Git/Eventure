package com.example.hai.eventfinder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ramotion.foldingcell.FoldingCell;

import org.w3c.dom.Text;

import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Hai on 1/11/2017.
 */

public class myFoldingCellListAdapter extends ArrayAdapter<EventItem> {
    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    Logger log = Logger.getAnonymousLogger();
    public myFoldingCellListAdapter(Context context, int resource) {
        super(context, resource);
    }

    public myFoldingCellListAdapter(Context context, int resource, List<EventItem> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //View v =  super.getView(position, convertView, parent);
        FoldingCell v = (FoldingCell) convertView;
        ViewHolder viewHolder;
        if(v == null) {
            log.info("cell isn't null");
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = (FoldingCell) inflater.inflate(R.layout.cell, parent, false);
            viewHolder.price = (TextView) v.findViewById(R.id.title_price);
            viewHolder.date = (TextView) v.findViewById(R.id.title_date);
            viewHolder.time = (TextView) v.findViewById(R.id.title_time);
            viewHolder.name = (TextView) v.findViewById(R.id.title_name);
            viewHolder.address = (TextView) v.findViewById(R.id.title_address);
            viewHolder.ratingLabel = (TextView) v.findViewById(R.id.title_ratinglabel);
            viewHolder.ratingBar = (RatingBar) v.findViewById(R.id.ratingBar);
            viewHolder.timeLabel = (TextView) v.findViewById(R.id.title_timeLabel2);
            viewHolder.eventTypeLabel = (TextView) v.findViewById(R.id.eventTypeLabel);
            viewHolder.eventType = (TextView) v.findViewById(R.id.eventType);
        } else {
            log.info("cell is null");
        }
        return v;
    }

    // simple methods for register cell state changes
    public void registerToggle(int position) {
        if (unfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }

    public void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

    public void registerUnfold(int position) {
        unfoldedIndexes.add(position);
    }

    private static class ViewHolder{
        TextView price;
        TextView date;
        TextView time;
        TextView name;
        TextView address;
        TextView ratingLabel;
        RatingBar ratingBar;
        TextView timeLabel;
        TextView eventTypeLabel;
        TextView eventType;


    }
}
