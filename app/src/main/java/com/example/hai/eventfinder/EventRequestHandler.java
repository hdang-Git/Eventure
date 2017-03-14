package com.example.hai.eventfinder;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Logger;

/**
 * Created by Van on 3/13/2017.
 */

public class EventRequestHandler implements EventBriteRequest {

        Logger log = Logger.getAnonymousLogger();

        //Takes the event and sets all the attributes according to API
        public void requestEvent(final Event myEvent){    //Creating handler
            myEvent.eventName = "hello";
            final Handler responseHandler = new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    try {

                        JSONObject blockObject = new JSONObject((String) msg.obj);

                        Log.d("SUCCESS" , "SUCCESS");

                        JSONArray eventsArray = blockObject.getJSONArray("events");
                        JSONObject event = eventsArray.getJSONObject(0);
                        JSONObject eventNameInfo = event.getJSONObject("name");

                        Log.d("json test" , eventNameInfo.getString("text"));

                        myEvent.setEventName(eventNameInfo.getString("text"));
                        //myEvent.eventName = eventNameInfo.getString("text");

                        Log.d("json derulo" , myEvent.eventName);



                        //Event myTemp = (Event) msg.obj;

                        //myTemp.eventName = eventNameInfo.getString("text");
                        //eventName.append(eventNameInfo.getString("text"));


                    } catch (JSONException e) {
                        Log.d("FAILED" , "FAILED");
                        e.printStackTrace();
                    }
                    Log.d("json derulo2" , myEvent.eventName);
                    return true;
                }

            });


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
                        responseHandler.sendMessage(msg);
                        Log.d("json test2" , myEvent.eventName);

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
            Log.d("json test4" , myEvent.eventName);
    }


}

