package com.example.hai.eventfinder;

import android.content.Intent;
import android.os.Message;
import android.os.Handler;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import static com.example.hai.eventfinder.ViewHolder.context;

/**
 * Created by chrx on 3/9/17.
 */

public class Event {

    public String eventName;
    public String eventDate;
    public String eventTime;
    public String eventLatitude;
    public String eventLongitude;
    public String eventLocation;
    public String eventDescription;
    public int eventPrice;
    public String eventPriceString;
    public String eventImageURL;

    public Event(){
        this.eventImageURL = "http://i.imgur.com/DvpvklR.png";
    }

    public Event(Builder builder){
        this.eventName = builder.eventName;
        this.eventDate = builder.eventDate;
        this.eventTime = builder.eventTime;
        this.eventPrice = builder.eventPrice;
        this.eventPriceString = builder.eventPriceString;
        this.eventLocation = builder.eventLocation;
        this.eventLongitude = builder.eventLongitude;
        this.eventLatitude = builder.eventLatitude;
        this.eventImageURL = builder.eventImageURL;
        this.eventDescription = builder.eventDescription;

    }

    public String getEventName() {
        return eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getEventTime() {
        return eventTime;
    }

    public String getEventLatitude() {
        return eventLatitude;
    }

    public String getEventLongitude() {
        return eventLongitude;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public int getEventPrice(){
        return eventPrice;
    }

    public String getEventPriceString(){
        return eventPriceString;
    }

    public String getEventImageURL() {
        return eventImageURL;
    }



    @Override
    public String toString() {
        return eventName + " " + eventDate + " " + eventTime + " "
                + eventLocation + " " + eventLatitude + " " + eventLongitude + " "
                + eventDescription + " " + eventPrice + " " + eventImageURL;
    }



    public void setEventValues(ArrayList<String> infoArray){

        this.eventName = infoArray.get(0).toString();
        this.eventDescription= infoArray.get(1).toString();
        this.eventImageURL = infoArray.get(2).toString();
        this.eventLatitude =infoArray.get(3).toString();
        this.eventLongitude = infoArray.get(4).toString();
    }



    public static class Builder{
        private String eventName;
        private String eventDate;
        private String eventTime;
        private String eventLatitude;
        private String eventLongitude;
        private int eventPrice;
        private String eventPriceString;
        private String eventLocation;
        private String eventDescription;
        private String eventImageURL;

        public Builder(String eventName){
            this.eventName = eventName;
        }

        public Builder setDate(String eventDate){
            this.eventDate = eventDate;
            return this;
        }

        public Builder setTime(String eventTime){
            this.eventTime = eventTime;
            return this;
        }

        public Builder setEventLocation(String eventLocation){
            this.eventLocation = eventLocation;
            return this;
        }

        public Builder setEventCoordinates(String eventLatitude, String eventLongitude){
            this.eventLatitude = eventLatitude;
            this.eventLongitude = eventLongitude;
            return this;
        }

        public Builder setEventDescription(String eventDescription){
            this.eventDescription = eventDescription;
            return this;
        }

        public Builder setImageUrl(String eventImageURL){
            this.eventImageURL = eventImageURL;
            return this;
        }

        public Builder setEventPrice(int eventPrice){
            this.eventPrice = eventPrice;
            return this;
        }

        public Builder setEventPriceString(String eventPrice){
            this.eventPriceString = eventPriceString;
            return this;
        }

        public Event build(){
            return new Event(this);
        }
    }
}


