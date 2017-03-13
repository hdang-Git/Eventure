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
import java.util.logging.Logger;

/**
 * Created by chrx on 3/9/17.
 */

public class Event implements EventBriteRequest{

    public String eventName;
    public String date;
    public String time;
    public String location;

    final Logger log = Logger.getAnonymousLogger();


   public void Event(){
       this.eventName = "N/A";
       this.date= "N/A";
       this.time= "N/A";
       this.location = "N/A";
   }


    //Going to make the API call here
    public void requestEvent() {
        Log.d("hi" , "hi");
        retrieveData();
    }


    public void retrieveData() {

        //final Logger log = Logger.getAnonymousLogger();
        final String urlString = "https://www.eventbriteapi.com/v3/events/search/?token=AHPLYGZSAR7PQKHZPQI4&location.latitude=39.9502352&location.longitude=-75.17327569999998&location.within=1mi";

        Thread t = new Thread() {
            public void run() {
                //result = (TextView) findViewById(R.id.resultView);
        //        log.info("run() is called");
                try
                {
                    URL url = new URL(urlString);
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(
                                    url.openStream()));
                    String tmpString = "";
                    String response = "";
                    while (tmpString != null) {
                        response.concat(tmpString);
                        response = response + tmpString;
                        tmpString = reader.readLine();
                    }
                    Message msg = Message.obtain();
                    msg.obj = response;

                    Log.d("downloaded data", response);
                    responseHandler.sendMessage(msg);

                } catch (
                        Exception e
                        )

                {
                    Log.d("downloaded data", "hi");
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }

    //Creating handler
    Handler responseHandler = new Handler(new Handler.Callback() {

        //Event tempEvent = new Event();

        @Override
        public boolean handleMessage(Message msg) {
            try {
                JSONObject blockObject = new JSONObject((String) msg.obj);
                Log.d("SUCCESS" , "SUCCESS");

                JSONArray eventsArray = blockObject.getJSONArray("events");
                JSONObject event = eventsArray.getJSONObject(0);
                JSONObject eventNameInfo = event.getJSONObject("name");

                Log.d("json test" , eventNameInfo.getString("text"));

                eventName = eventNameInfo.getString("text");

            } catch (JSONException e) {
                Log.d("FAILED" , "FAILED");
                e.printStackTrace();
            }
            return true;
        }
    });


}


