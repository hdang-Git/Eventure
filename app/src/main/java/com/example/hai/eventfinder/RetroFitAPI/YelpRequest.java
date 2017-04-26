package com.example.hai.eventfinder.RetroFitAPI;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hai.eventfinder.InfoWindowViewHolder;
import com.example.hai.eventfinder.R;
import com.example.hai.eventfinder.RetroFitAPI.Models.Yelp.YelpReturn;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.util.Map;

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

    int i = 0;

    String url = "https://api.yelp.com/v3/businesses/";

    YelpReturn businesses;

    LatLng coffeeCoords;


    public YelpReturn makeCall(final Context context , String Latitude , String Longitude , GoogleMap map , InfoWindowViewHolder viewHolder){

        final GoogleMap gmap = map;

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create());//GSON turns JSON to POJOs using Models

        final Retrofit retrofit = builder.build();

        final LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        //Creates instance of the client we will be using
        RetroInterfaceYelp client = retrofit.create(RetroInterfaceYelp.class);


        Call<YelpReturn> call = client.requestYelp("Bearer " + context.getResources().getText(R.string.yelp_key) , Latitude , Longitude);

        call.enqueue(new Callback<YelpReturn>() {
            @Override
            public void onResponse(Call<YelpReturn> call, Response<YelpReturn> response) {

                businesses = response.body();

                for(i=0; i < businesses.getBusinesses().size() && i<10; i++) {


                    Log.d("Retro Success", "" + businesses.getBusinesses().get(i).getName());
                    coffeeCoords = new LatLng(
                            businesses.getBusinesses().get(i).getCoordinates().getLatitude(),
                            businesses.getBusinesses().get(i).getCoordinates().getLongitude()
                    );

                    gmap.addMarker(new MarkerOptions()
                            .position(coffeeCoords)
                            .title(businesses.getBusinesses().get(i).getName())
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.kitchen_coffee_cup)));


//                    gmap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
//                        @Override
//                        public View getInfoWindow(Marker marker) {
//                            return null;
//                        }
//
//                        @Override
//                        public View getInfoContents(Marker marker) {
//
//
//
//                            View v = inflater.inflate(R.layout.activity_yelp_info_window , null);
//
//
//                            TextView shopName = (TextView) v.findViewById(R.id.info_window_shop_name);
//                            Button yelpButton = (Button) v.findViewById(R.id.visit_yelp_button);
//                            Button createEventure = (Button) v.findViewById(R.id.create_eventure_button);
//                            ImageView shopImage= (ImageView) v.findViewById(R.id.info_window_picture);
//
//                            shopName.setText(businesses.getBusinesses().get(i).getName());
//
//                            Picasso.with(context).load(businesses.getBusinesses().get(i).getImageUrl()).into(shopImage);
//
//                            return v;
//
//                        }
//                    });



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
