package com.example.hai.eventfinder;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.hai.eventfinder.RetroFitAPI.YelpRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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

        mMap.addMarker(new MarkerOptions().position(coords).title("Your Event"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(coords));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15));

        //These methods make the call for Yelp using retrofit
        YelpRequest testyelp = new YelpRequest();
        testyelp.makeCall(getApplicationContext() ,eventLat , eventLon , mMap);
    }
}
