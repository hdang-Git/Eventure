package com.example.hai.eventfinder.RetroFitAPI;

import com.example.hai.eventfinder.RetroFitAPI.Models.Yelp.YelpReturn;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Van on 4/19/2017.
 */

public interface RetroInterfaceYelp {

    //String base_url = "https://api.yelp.com/v3/businesses/search?categories=coffee&latitude=39.9502352&longitude=-75.17327569999998";

    //Supply Header auth when making the RetrofitCall
    //@GET("search?categories=coffee&latitude=39.9502352&longitude=-75.17327569999998")
    @GET("search?categories=coffee")
    //Call<YelpReturn> requestYelp(@Header("Authorization")String Bearer);
    Call<YelpReturn> requestYelp(@Header("Authorization") String Bearer , @Query("latitude") String lat , @Query("longitude") String lon);


}
