package com.example.hai.eventfinder;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.hai.eventfinder.RetroFitAPI.Models.Yelp.Business;
import com.example.hai.eventfinder.RetroFitAPI.Models.Yelp.YelpReturn;
import com.example.hai.eventfinder.RetroFitAPI.YelpRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.HashMap;

import static com.example.hai.eventfinder.ViewHolder.context;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    LatLng coords;

    String eventLat;
    String eventLon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Bundle bundle = getIntent().getParcelableExtra("mapCoords");
        coords = bundle.getParcelable("coords");

        eventLat = String.valueOf(coords.latitude);
        eventLon = String.valueOf(coords.longitude);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        Log.d("Event coords atm", coords.toString());

        LatLng eventCoords = new LatLng(coords.latitude, coords.longitude);

        mMap.addMarker(new MarkerOptions().position(coords).title("Your Event"));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(coords));
//        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(eventCoords , 16);
//        mMap.animateCamera(cameraUpdate);


        //This is for the InfoWindow
        InfoWindowViewHolder viewHolder = new InfoWindowViewHolder();
        viewHolder.yelpButton = (Button) findViewById(R.id.visit_yelp_button);
        viewHolder.createEventure = (Button) findViewById(R.id.create_eventure_button);
        viewHolder.eventName = (TextView) findViewById(R.id.info_window_shop_name);


        //this is to store marker data in memory when we make the yelp request
        //see Models.Yelp for what a 'Business' object is
        final HashMap<Marker, Business> markerData = new HashMap<Marker, Business>();

        //These methods make the call for Yelp using retrofit
        YelpRequest testyelp = new YelpRequest();
        testyelp.makeCall(getApplicationContext(), eventLat, eventLon, mMap, viewHolder, markerData);

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                final Marker markerReference = marker;//need a reference of this later once glide loads the resources
                boolean resourceLoaded =false;

                //Check if you clicked on a yelp marker(by checking hash that was made on request)
                //If it is null we don't have to do anything since it's the event marker
                if (markerData.get(marker) != null) {

                    mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                        //cache a reference to yourself for glide
                        GoogleMap.InfoWindowAdapter myself = this;

                        @Override
                        public View getInfoWindow(Marker marker) {
                            return null;
                        }

                        @Override
                        public View getInfoContents(final Marker marker) {


                            View v = getLayoutInflater().inflate(R.layout.activity_yelp_info_window, null);


                            TextView shopName = (TextView) v.findViewById(R.id.info_window_shop_name);
                            Button yelpButton = (Button) v.findViewById(R.id.visit_yelp_button);
                            Button createEventure = (Button) v.findViewById(R.id.create_eventure_button);
                            final ImageView shopImage= (ImageView) v.findViewById(R.id.info_window_picture);

                            shopName.setText(markerData.get(marker).getName());

                            Log.d("marker's url" , markerData.get(marker).getImageUrl());


                            Glide.with(context).load(markerData.get(marker).getImageUrl()).placeholder(R.mipmap.kitchen_coffee_cup)
                                    .into(new SimpleTarget<GlideDrawable>(150,150) {
                                        @Override
                                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                                                shopImage.setImageDrawable(resource);
                                        }
                                    });
                            //Picasso.with(context).load(markerData.get(marker).getImageUrl()).into(shopImage);
                            /*
                            Glide.with(context).load(markerData.get(marker).getImageUrl()).placeholder(R.mipmap.kitchen_coffee_cup).asBitmap()
                                    .into(new SimpleTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                            // Setting new icon for Marker
                                            shopImage.setIcon(BitmapDescriptorFactory.fromBitmap(resource));
                                        }
                                    });
                                    */

                            return v;

                        }
                    });
                }

                return false;
            }
        });


    }

}

