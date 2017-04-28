package com.example.hai.eventfinder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.logging.Logger;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Van on 3/19/2017.
 */

//Copy Pasted from Adapter, put it here for passing in ASYNC
public class ViewHolder implements OnMapReadyCallback {


        static Context context;
        static Activity activity;

        public ViewHolder(Context c){
                this.context = c;
        }

        public ViewHolder(Context c , Activity a){
                this.context = c;
                this.activity = a;
        }

        Logger log = Logger.getAnonymousLogger();
        //Title card (outer stuff/closed card)
        TextView priceClosed;
        TextView dateClosed;
        TextView timeClosed;
        TextView eventNameClosed;
        TextView addressClosed;
        TextView startTimeClosed;
        TextView eventTypeLabel;
        TextView eventTypeClosed;
        ImageView eventImage;

        //Content Card (inner stuff/open card)
        TextView eventNameOpen;
        TextView eventDescription;
        TextView eventDateOpen;
        TextView eventTimeOpen;
        TextView eventPlaceOpen;


        MapView mapView;
        GoogleMap map;


        @Override
        public void onMapReady(GoogleMap googleMap) {
                log.info("onMapReady() is called");
                MapsInitializer.initialize(context.getApplicationContext());
                map = googleMap;
                Event data = (Event) mapView.getTag();


                if (data != null) {
                    try {
                            setMapLocation(map, data);
                    }
                    catch(GooglePlayServicesNotAvailableException e){
                                Log.d("uhh" , e.toString());
                        }
                }
        }

        /**
         * Initialises the MapView by calling its lifecycle methods.
         */

        public void initializeMapView() {
            if (mapView != null) {
                //TODO: FIX THIS
                // Initialise the MapView
                mapView.onCreate(null);
                // Set the map ready callback to receive the GoogleMap object
                mapView.getMapAsync(this);
                Log.d("George" , "we done");
            }
        }


        public static void setMapLocation(GoogleMap map, Event data) throws GooglePlayServicesNotAvailableException {

                Log.d("before mappass" , data.toString());

                double latitude;
                double longitude;

                if (map == null)
                        return; // Google Maps not available
                try {
                        MapsInitializer.initialize(context);
                }
                catch (Exception e) {
                        //catch (GooglePlayServicesNotAvailableException e) {
                        Log.d("Map" , "Loser");
                        return;
                }

                Log.d("About to set markers" , "turkey");
                if(data.eventLatitude == null) {
                        Log.d("About to set markers" , "if");
                        latitude = 39.9502352;
                        longitude = -75.17327569999998;
                }
                else {
                        Log.d("About to set markers" , "else");
                        latitude = Double.parseDouble(data.eventLatitude);
                        longitude = Double.parseDouble(data.eventLongitude);
                        map.clear();
                }

                final LatLng location = new LatLng(latitude, longitude);
                // Add a marker for this item and set the camera
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 13f));
                map.addMarker(new MarkerOptions().position(location));

                // Set the map type back to normal.
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                        @Override
                        public void onMapClick(LatLng latLng) {
                                Log.d("Map clicked" , "aye");

                                Intent intent = new Intent(context.getApplicationContext() , MapsActivity.class);

                                Bundle args = new Bundle();
                                args.putParcelable("coords", location);

                                intent.putExtra("mapCoords" , args);

                                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK) ;

                                activity.startActivity(intent);

                        }
                });
        }

        public static AbsListView.RecyclerListener mRecycleListener = new AbsListView.RecyclerListener() {

                @Override
                public void onMovedToScrapHeap(View view) {
                        ViewHolder holder = (ViewHolder) view.getTag();
                        if (holder != null && holder.map != null) {
                                // Clear the map and free up resources by changing the map type to none
                                holder.map.clear();
                                holder.map.setMapType(GoogleMap.MAP_TYPE_NONE);
                        }

                }
        };



}

