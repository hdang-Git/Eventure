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
    public String date;
    public String time;
    public String location;

    public Event(){

    }

    public Event(int eventNum){
       this.requestEvent(eventNum);
        //return this;
    }

    //This creates the Async task and then uses it to fill out the attributes of this Event
    @Override
    public void requestEvent(int eventNum) {

        EventRequestAsyncTask BriteRequest= new EventRequestAsyncTask();
        ArrayList ResultsArray= new ArrayList<String>();

        //Hello world will show up in eventName if the API call fails
        String testEventName = "Hello World";

        //This launches the ASYNC task that calls the API
        try {
            ResultsArray= BriteRequest.execute(eventNum).get();
            testEventName = ResultsArray.get(0).toString();
            Log.d("Done" , testEventName);
        }
        catch(InterruptedException ie){
            Log.d("Race" , "Condition");
        }
        catch (ExecutionException ee){
            Log.d("Condition" , "Race");
        }
        this.eventName = testEventName;
    }

    public String getEventName() {
        return eventName;
    }
}


