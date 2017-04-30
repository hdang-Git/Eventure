package com.example.hai.eventfinder;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.EventLog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
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
    List<Event>  originalEventData;

    //Event myEvent = new Event();
    Context context;
    LayoutInflater inflater;
    Activity activity;
    EventFilter eventFilter = new EventFilter();

    @Override
    public int getCount() {
        int count;
        if(eventsArray == null){
            count = 0;
        } else {
            count = eventsArray.size();
        }
        return count;
    }

    private final HashSet<MapView> mMaps = new HashSet<MapView>();

    public myFoldingCellListAdapter(Context context, int resource) {
        super(context, resource);
        //this.constructEventArray(this);
    }


    public myFoldingCellListAdapter(Context context, int resource, List<Event> objects) {
        super(context, resource, objects);
        eventsArray = objects;
    }

    public myFoldingCellListAdapter(Context context, int resource, List<Event> objects , Activity act) {
        super(context, resource, objects);
        eventsArray = objects;
        originalEventData = objects;
        this.activity = act;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //View view =  super.getView(position, convertView, parent);
        FoldingCell v = (FoldingCell) convertView;
        ViewHolder viewHolder;

        Event eachEvent = getItem(position);
        if(inflater == null){
            context = parent.getContext();
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if(v == null) {
            log.info("cell isn't null");
            viewHolder = new ViewHolder(context.getApplicationContext() , activity);
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = (FoldingCell) inflater.inflate(R.layout.cell, parent, false);

            //Title card (outer stuff / closed card)
            viewHolder.priceClosed = (TextView) v.findViewById(R.id.title_price);
            viewHolder.dateClosed = (TextView) v.findViewById(R.id.title_date);
            viewHolder.startTimeClosed = (TextView) v.findViewById(R.id.title_time);
            viewHolder.eventNameClosed = (TextView) v.findViewById(R.id.title_name);
            viewHolder.addressClosed = (TextView) v.findViewById(R.id.title_address);
            viewHolder.timeClosed = (TextView) v.findViewById(R.id.title_time2);
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

        Event eventItem = new Event();
        viewHolder.mapView.setTag(eventItem);
        viewHolder.mapView.setTag(eventsArray.get(position));



        //Todo: fix from crash
        if(viewHolder.map != null){
            try {
                //viewHolder.setMapLocation(viewHolder.map, eventItem);
                //Log.d("before mappass" , eventsArray.get(position).toString());
                ViewHolder.setMapLocation(viewHolder.map, eventsArray.get(position));
            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }
        }
        else{
            Log.d("Testing map" , "else reached");
        }

        //Event Time
        String eventTime = eventsArray.get(position).getEventStartTime() + "-" + eventsArray.get(position).getEventEndTime();

        //open / content card
        viewHolder.eventNameOpen.setText(eventsArray.get(position).eventName);
        viewHolder.eventDescription.setText(eventsArray.get(position).getEventDescription());
        Picasso.with(context).load(eventsArray.get(position).getEventImageURL()).into(viewHolder.eventImage);
        viewHolder.eventDateOpen.setText(eventsArray.get(position).getEventDate());
        viewHolder.eventPlaceOpen.setText(eventsArray.get(position).getEventAddressLong());
        //TODO: reattach the map
        //viewHolder.mapView = ;
        //closed

        //closed / title card
        viewHolder.eventNameClosed.setText(eventsArray.get(position).eventName);
        viewHolder.priceClosed.setText(eventsArray.get(position).eventPriceString);
        viewHolder.dateClosed.setText(eventsArray.get(position).getEventDate());
        viewHolder.timeClosed.setText(eventTime);
        viewHolder.startTimeClosed.setText(eventsArray.get(position).getEventStartTime());
        viewHolder.addressClosed.setText(eventsArray.get(position).getEventAddressShort());
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



    public void filter(String query) {
        if (TextUtils.isEmpty(query)) {
            eventsArray = new ArrayList<>(eventsArray);
        } else {
            eventsArray.clear();
            for (Event value : originalEventData) {
                if (value.toString().toLowerCase().contains(query.toLowerCase())) {
                    eventsArray.add(value);
                }
            }
        }
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return eventFilter;
    }

    class EventFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String query = charSequence.toString().toLowerCase();
            FilterResults results = new FilterResults();
            if(TextUtils.isEmpty(query)){
                eventsArray = new ArrayList<>();
            } else {
                eventsArray.clear();
                for(Event event: originalEventData){
                    if(event.toString().toLowerCase().contains(query)){
                        eventsArray.add(event);
                    }
                }
            }
            results.values = eventsArray;
            results.count = eventsArray.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            eventsArray = (ArrayList<Event>) filterResults.values;
            notifyDataSetChanged();
        }
    }

}
