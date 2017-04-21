package com.example.hai.eventfinder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.EventLog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ramotion.foldingcell.FoldingCell;
import com.squareup.picasso.Picasso;


import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;


public class myFoldingCellListAdapter extends ArrayAdapter<Event> {
    public HashSet<Integer> unfoldedIndexes = new HashSet<>();

    Logger log = Logger.getAnonymousLogger();

    public List<Event> eventsArray;

    //Event myEvent = new Event();
    Context context;
    LayoutInflater inflater;

    private final HashSet<MapView> mMaps = new HashSet<MapView>();

    public myFoldingCellListAdapter(Context context, int resource) {
        super(context, resource);
        //this.constructEventArray(this);
    }


    public myFoldingCellListAdapter(Context context, int resource, List<Event> objects) {
        super(context, resource, objects);
        eventsArray = objects;
    }

//    public void constructEventArray() {
//        //TODO the int is not needed anymore
//        ASYNCparams eventArgs = new ASYNCparams(0, viewHolder, this.getContext(), eventsArray.get(position));
//        EventRequestAsyncTask BriteRequest = new EventRequestAsyncTask();
//        BriteRequest.execute(eventArgs);
//    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //View v =  super.getView(position, convertView, parent);
        FoldingCell v = (FoldingCell) convertView;
        ViewHolder viewHolder;

        Event eachEvent = getItem(position);
        if(inflater == null){
            context = parent.getContext();
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if(v == null) {
            log.info("cell isn't null");
            viewHolder = new ViewHolder(context.getApplicationContext());
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = (FoldingCell) inflater.inflate(R.layout.cell, parent, false);

            //Title card (outer stuff / closed card)
            viewHolder.priceClosed = (TextView) v.findViewById(R.id.title_price);
            viewHolder.dateClosed = (TextView) v.findViewById(R.id.title_date);
            viewHolder.timeClosed = (TextView) v.findViewById(R.id.title_time);
            viewHolder.eventNameClosed = (TextView) v.findViewById(R.id.title_name);//G
            viewHolder.addressClosed = (TextView) v.findViewById(R.id.title_address);
            viewHolder.ratingLabel = (TextView) v.findViewById(R.id.title_ratinglabel);
            viewHolder.ratingBar = (RatingBar) v.findViewById(R.id.ratingBar);
            viewHolder.timeLabel = (TextView) v.findViewById(R.id.title_timeLabel2);
            viewHolder.eventTypeLabel = (TextView) v.findViewById(R.id.eventTypeLabel);
            viewHolder.eventTypeClosed = (TextView) v.findViewById(R.id.eventType);

            //Content card (inner stuff / open card)
            viewHolder.eventNameOpen = (TextView) v.findViewById(R.id.content_title);
            viewHolder.eventDescription = (TextView) v.findViewById(R.id.content_description);
            viewHolder.eventImage = (ImageView) v.findViewById(R.id.imageHeaderBackground);
            viewHolder.eventDateOpen = (TextView) v.findViewById(R.id.content_date);
            viewHolder.eventTimeOpen = (TextView) v.findViewById(R.id.content_time);
            viewHolder.eventPlaceOpen = (TextView) v.findViewById(R.id.content_location);
            viewHolder.mapView = (MapView) v.findViewById(R.id.lite_map);


            v.setTag(viewHolder);
            viewHolder.initializeMapView();
           mMaps.add(viewHolder.mapView);



        } else {
            // for existing cell set valid valid state(without animation)
            if (unfoldedIndexes.contains(position)) {
                Log.d("unfold" , "unfolded");
                v.unfold(true);
            } else {
                Log.d("fold" , "folded");
                v.fold(true);
            }
            log.info("cell is null");
            viewHolder = (ViewHolder) v.getTag();
        }

//        viewHolder.eventNameClosed.setText(eventsArray.get(position).eventName);
//        Picasso.with(context).load(eventsArray.get(position).eventImageURL).into(viewHolder.eventImage);


//        ASYNCparams eventArgs = new ASYNCparams(position, viewHolder, this.getContext(), eventsArray.get(position));
//        EventRequestAsyncTask BriteRequest = new EventRequestAsyncTask();
//        BriteRequest.execute(eventArgs);


        Event eventItem = new Event();
        viewHolder.mapView.setTag(eventItem);

        //Todo: fix from crash
        if(viewHolder.map != null){
            try {
                viewHolder.setMapLocation(viewHolder.map, eventItem);
            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }
        }


        //open
        viewHolder.eventNameOpen.setText(eventsArray.get(position).eventName);
        viewHolder.eventDescription.setText(eventsArray.get(position).getEventDescription());
        Picasso.with(context).load(eventsArray.get(position).getEventImageURL()).into(viewHolder.eventImage);
        viewHolder.eventDateOpen.setText(eventsArray.get(position).getEventDate());
        viewHolder.eventTimeOpen.setText(eventsArray.get(position).getEventTime());
        viewHolder.eventPlaceOpen.setText(eventsArray.get(position).getEventLocation());
        //TODO: reattach the map
        //viewHolder.mapView = ;
        //closed

        viewHolder.eventNameClosed.setText(eventsArray.get(position).eventName);
        viewHolder.priceClosed.setText(eventsArray.get(position).eventPriceString);
        viewHolder.dateClosed.setText(eventsArray.get(position).getEventDate());
        viewHolder.timeClosed.setText(eventsArray.get(position).getEventTime());
        viewHolder.addressClosed.setText(eventsArray.get(position).getEventLocation());
        viewHolder.ratingBar.setNumStars(3);
        //viewHolder.eventTypeClosed;

        Log.d("George" , "We're about to return v");
        Log.d("Hai", "No we're not! jk");
        return v;
    }

    // simple methods for register cell state changes
    public void registerToggle(int position) {

        Log.d("toggle position " , Integer.toString(position));
        //Logs everything in the hashset
        for(int i : unfoldedIndexes){
            Iterator<Integer> itr = unfoldedIndexes.iterator();
            while(itr.hasNext()) {
                Log.d("hash check", Integer.toString(itr.next()));
            }
        }
        if (unfoldedIndexes.contains(position)) {
            Log.d("close", "closed");
            registerFold(position);
        }
        else {
            Log.d("open", "opened");
            registerUnfold(position);
        }
    }

    public void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

    public void registerUnfold(int position) {
        Log.d("open position" , Integer.toString(position));
        unfoldedIndexes.add(position);

    }

    public HashSet<MapView> getMaps() {
        return mMaps;
    }



}
