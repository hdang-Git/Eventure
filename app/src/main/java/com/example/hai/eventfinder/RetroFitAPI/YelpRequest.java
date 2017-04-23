package com.example.hai.eventfinder.RetroFitAPI;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.hai.eventfinder.R;
import com.example.hai.eventfinder.RetroFitAPI.Models.Yelp.YelpReturn;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Header;

/**
 * Created by Van on 4/20/2017.
 */

public class YelpRequest {

    String url = "https://api.yelp.com/v3/businesses/";

    YelpReturn businesses;

    LatLng coffeeCoords;


    public YelpReturn makeCall(final Context context , String Latitude , String Longitude , GoogleMap map){

        final GoogleMap gmap = map;

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create());//GSON turns JSON to POJOs using Models

        final Retrofit retrofit = builder.build();

        //Creates instance of the client we will be using
        RetroInterfaceYelp client = retrofit.create(RetroInterfaceYelp.class);


        Call<YelpReturn> call = client.requestYelp("Bearer " + context.getResources().getText(R.string.yelp_key) , Latitude , Longitude);

        call.enqueue(new Callback<YelpReturn>() {
            @Override
            public void onResponse(Call<YelpReturn> call, Response<YelpReturn> response) {

                businesses = response.body();

                for(int i=0; i < businesses.getBusinesses().size() && i<10; i++) {


                    Log.d("Retro Success", "" + businesses.getBusinesses().get(i).getName());
                    coffeeCoords = new LatLng(
                            businesses.getBusinesses().get(i).getCoordinates().getLatitude(),
                            businesses.getBusinesses().get(i).getCoordinates().getLongitude()
                    );

                    gmap.addMarker(new MarkerOptions()
                            .position(coffeeCoords)
                            .title(businesses.getBusinesses().get(i).getName())
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.kitchen_coffee_cup)));

                }//end of for

                //Toast.makeText(context , businesses.getBusinesses().get(0).getName()  , Toast.LENGTH_LONG);

            }

            @Override
            public void onFailure(Call<YelpReturn> call, Throwable t) {
                Log.d("Retro Failed : " , "" + t ) ;
            }
        });

        return businesses;
    }


}
