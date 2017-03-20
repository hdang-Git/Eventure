package com.example.hai.eventfinder;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.logging.Logger;

/**
 * Created by Van on 3/19/2017.
 */

//Copy Pasted from Adapter, put it here for passing in ASYNC
public class ViewHolder implements OnMapReadyCallback {

        Context context;

        public ViewHolder(Context c){
                this.context = c;
        }

        Logger log = Logger.getAnonymousLogger();

        TextView price;
        TextView date;
        TextView time;
        TextView eventNameClosed;//G
        TextView address;
        TextView ratingLabel;
        RatingBar ratingBar;
        TextView timeLabel;
        TextView eventTypeLabel;
        TextView eventType;

        //Below this is George's stuff
        TextView eventName;
        TextView eventDescription;
        ImageView eventImage;

        MapView mapView;
        GoogleMap map;

        @Override
        public void onMapReady(GoogleMap googleMap) {
                log.info("onMapReady() is called");
                MapsInitializer.initialize(context.getApplicationContext());
                map = googleMap;
                Event data = (Event) mapView.getTag();
                if (data != null) {
                        setMapLocation(map, data);
                }
        }

/**
 * Initialises the MapView by calling its lifecycle methods.
 */

        public void initializeMapView() {
            if (mapView != null) {
                // Initialise the MapView
                mapView.onCreate(null);
                // Set the map ready callback to receive the GoogleMap object
                mapView.getMapAsync(this);
            }
        }


        public static void setMapLocation(GoogleMap map, Event data) {
                double latitude = 39.9502352;
                double longitude = -75.17327569999998;
                LatLng location = new LatLng(latitude, longitude);
                // Add a marker for this item and set the camera
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 13f));
                map.addMarker(new MarkerOptions().position(location));

                // Set the map type back to normal.
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
    }

