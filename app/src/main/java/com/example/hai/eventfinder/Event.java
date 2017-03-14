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

    /*
    final public String eventName = "N/A";
    final public String date= "N/A";
    final public String time= "N/A";
    final public String location= "N/A";
    */
    public String eventName;
    public String date;
    public String time;
    public String location;


    final Logger log = Logger.getAnonymousLogger();


   public void Event(){
       /*
       this.eventName = "N/A";
       this.date= "N/A";
       this.time= "N/A";
       this.location = "N/A";
       */
   }


   public void setEventName(String myEventName) {

       this.eventName = myEventName;

   }


    //Going to make the API call here
    public void requestEvent(Event myEvent) {
        Log.d("hi" , "hi");
        Event tempEvent = retrieveData();
        myEvent.eventName = tempEvent.eventName;
        //this.eventName = "chicken nuggets";
        //Log.d("json test3" , "hi");
    }


    public Event retrieveData() {

        //final Logger log = Logger.getAnonymousLogger();
        final String urlString = "https://www.eventbriteapi.com/v3/events/search/?token=AHPLYGZSAR7PQKHZPQI4&location.latitude=39.9502352&location.longitude=-75.17327569999998&location.within=1mi";
        final Event tempEvent = new Event();

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
                    tempEvent.eventName = JSONParsing(msg, tempEvent);
                    //responseHandler.sendMessage(msg);
                    Log.d("json test2" , eventName);
                    Log.d("ugh test" , tempEvent.eventName);

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
        return tempEvent;
    }

    public String JSONParsing(Message msg, Event myEvent){
        try {

                JSONObject blockObject = new JSONObject((String) msg.obj);
                Log.d("SUCCESS" , "SUCCESS");

                JSONArray eventsArray = blockObject.getJSONArray("events");
                JSONObject event = eventsArray.getJSONObject(0);
                JSONObject eventNameInfo = event.getJSONObject("name");

                Log.d("json test" , eventNameInfo.getString("text"));

                myEvent.eventName = eventNameInfo.getString("text");
                Log.d("jjjson test" , myEvent.eventName);


                Log.d("gggson test" , myEvent.eventName);
                return myEvent.eventName;

                //Event myTemp = (Event) msg.obj;

                //myTemp.eventName = eventNameInfo.getString("text");
                //eventName.append(eventNameInfo.getString("text"));


            } catch (JSONException e) {
                Log.d("FAILED" , "FAILED");
                e.printStackTrace();
            }
            Log.d("gggson test" , myEvent.eventName);
            return myEvent.eventName;
    }

    //Creating handler
    Handler responseHandler = new Handler(new Handler.Callback() {


        @Override
        public boolean handleMessage(Message msg) {
            try {

                JSONObject blockObject = new JSONObject((String) msg.obj);
                Log.d("SUCCESS" , "SUCCESS");

                JSONArray eventsArray = blockObject.getJSONArray("events");
                JSONObject event = eventsArray.getJSONObject(0);
                JSONObject eventNameInfo = event.getJSONObject("name");

                Log.d("json test" , eventNameInfo.getString("text"));



                //Event myTemp = (Event) msg.obj;

                //myTemp.eventName = eventNameInfo.getString("text");
                //eventName.append(eventNameInfo.getString("text"));


            } catch (JSONException e) {
                Log.d("FAILED" , "FAILED");
                e.printStackTrace();
            }
            return true;
        }
    });


}


