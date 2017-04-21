package com.example.hai.eventfinder.RetroFitAPI;

import android.content.Context;
import android.util.Log;

import com.example.hai.eventfinder.R;
import com.example.hai.eventfinder.RetroFitAPI.Models.Yelp.YelpReturn;

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

    String url = "https://api.yelp.com/v3/";
    String headerAuth = "";

    YelpReturn businesses;

    public YelpReturn makeCall(Context context){

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create());//GSON turns JSON to POJOs using Models

        final Retrofit retrofit = builder.build();

        //Creates instance of the client we will be using
        RetroInterfaceYelp client = retrofit.create(RetroInterfaceYelp.class);


        Call<YelpReturn> call = client.requestYelp("Bearer " + context.getResources().getText(R.string.yelp_key));

        call.enqueue(new Callback<YelpReturn>() {
            @Override
            public void onResponse(Call<YelpReturn> call, Response<YelpReturn> response) {

                businesses = response.body();

                Log.d("Retro Success" , "" + response);

            }

            @Override
            public void onFailure(Call<YelpReturn> call, Throwable t) {
                Log.d("Retro Failed : " , "" + t ) ;
            }
        });

        return businesses;
    }


}
