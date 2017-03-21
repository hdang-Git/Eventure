package com.example.hai.eventfinder;

import android.content.Intent;
import android.os.Message;
import android.os.Handler;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

/**
 * Created by chrx on 3/9/17.
 */

public class Event implements EventBriteRequest{

    public String eventName;
    public String eventDate;
    public String eventTime;
    public String eventLatitude;
    public String eventLongitude;
    public String eventLocation;
    public String eventDescription;
    public String eventImageURL;

    public Event(){

    }

    public Event(EventBuilder builder){
        this.eventName = builder.eventName;
        this.eventDate = builder.eventDate;
        this.eventTime = builder.eventTime;
        this.eventLocation = builder.eventLocation;
        this.eventLongitude = builder.eventLongitude;
        this.eventLatitude = builder.eventLatitude;
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

    public String getEventImageURL() {
        return eventImageURL;
    }

    //This creates the Async task and then uses it to fill out the attributes of this Event
    @Override
    public void requestEvent(int eventNum) {

        EventRequestAsyncTask BriteRequest= new EventRequestAsyncTask();
        ArrayList<String> ResultsArray= new ArrayList<String>();


        //This launches the ASYNC task that calls the API
        try {
            ResultsArray= BriteRequest.execute().get();
            Log.d("hmm" , ResultsArray.get(0));
        }
        catch(InterruptedException ie){
            Log.d("Race" , "Condition");
        }
        catch (ExecutionException ee){
            Log.d("Condition" , "Race");
        }
        this.setEventValues(ResultsArray);
    }

    public void setEventValues(ArrayList<String> infoArray){
        this.eventName = infoArray.get(0).toString();
        this.eventDescription= infoArray.get(1).toString();
        this.eventImageURL = infoArray.get(2).toString();
        this.eventLatitude =infoArray.get(3).toString();
        this.eventLongitude = infoArray.get(4).toString();
    }

    public static class EventBuilder{
        private String eventName;
        private String eventDate;
        private String eventTime;
        private String eventLatitude;
        private String eventLongitude;
        private String eventLocation;
        private String eventDescription;
        private String eventImageURL;

        public EventBuilder(String eventName){
            this.eventName = eventName;
        }

        public EventBuilder date(String eventDate){
            this.eventDate = eventDate;
            return this;
        }

        public EventBuilder time(String eventTime){
            this.eventTime = eventTime;
            return this;
        }

        public EventBuilder eventCoordinates(String eventLatitude, String eventLongitude){
            this.eventLatitude = eventLatitude;
            this.eventLongitude = eventLongitude;
            return this;
        }

        public EventBuilder eventDescription(String eventDescription){
            this.eventDescription = eventDescription;
            return this;
        }

        public EventBuilder imageUrl(String eventImageURL){
            this.eventImageURL = eventImageURL;
            return this;
        }
    }
}


