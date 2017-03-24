package com.example.hai.eventfinder;

import android.os.AsyncTask;
import android.os.Message;
import android.util.Log;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.jar.JarException;

/**
 * Created by Van on 3/13/2017.
 */

//AsyncTask<What you give it , progress , What you want the result of the thread execution to be>
public class EventRequestAsyncTask extends AsyncTask<ASYNCparams, Integer , ArrayList<String>> {

    ASYNCparams p;

    String returnStringName;
    String returnStringDate;
    String returnStringTime;
    String returnStringLocation;
    String returnStringLatitude;
    String returnStringLongitude;
    String returnStringDescription;
    String returnStringImageURL;

    ArrayList<String> returnStringArray = new ArrayList<String>();

    Event eventBuilder = new Event();

    //new Event2().execute(urlString);

    //This is what happens in the thread
    @Override
    protected ArrayList doInBackground (ASYNCparams... params) {

        p = params[0];

        final String urlString = "https://www.eventbriteapi.com/v3/events/search/?token=" + p.context.getResources().getText(R.string.event_brite_key) + "&location.latitude=39.9502352&location.longitude=-75.17327569999998&location.within=1mi&expand=organizer,venue";

        Log.d("url check " , urlString);

        int JSONdrill = p.JSONdrill;

        try{
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
            try {
                JSONObject blockObject = new JSONObject(response);
                Log.d("SUCCESS", "SUCCESS");

                JSONArray eventsArray = blockObject.getJSONArray("events");
                JSONObject event = eventsArray.getJSONObject(JSONdrill);

                JSONObject eventNameInfo = event.getJSONObject("name");
                returnStringName = eventNameInfo.getString("text");

                JSONObject eventDescriptionInfo= event.getJSONObject("description");
                returnStringDescription= eventDescriptionInfo.getString("text");

                JSONObject eventImageInfo = event.getJSONObject("logo");
                returnStringImageURL= eventImageInfo.getString("url");

                JSONObject venueInfo = event.getJSONObject("venue");

                JSONObject addressInfo = venueInfo.getJSONObject("address");
                returnStringLatitude = addressInfo.getString("latitude");
                returnStringLongitude= addressInfo.getString("longitude");

                /*
                returnStringArray.add(returnStringName);
                returnStringArray.add(returnStringDescription);
                returnStringArray.add(returnStringImageURL);
                returnStringArray.add(returnStringLatitude);
                returnStringArray.add(returnStringLongitude);
                */
                eventBuilder = new Event.Builder(returnStringName)
                        .setEventDescription(returnStringDescription)
                        .setImageUrl(returnStringImageURL)
                        .setEventCoordinates(returnStringLatitude, returnStringLongitude)
                        .build();

                Log.d("json test", eventNameInfo.getString("text"));

            }
            catch(JSONException e){
                Log.d("Failed JSON" , "Failed JSON Pull doInBackground() for EventBrite");
            }
        }
        catch (Exception e){
            Log.d("Failed JSON" , "doInBackground() failed with Exception e");
            e.printStackTrace();
        }

        return returnStringArray;
    }

    //Just logging to check that the thread behaved correctly
    @Override
    protected void onPostExecute(ArrayList result){

        Log.d("PostExecute" , eventBuilder.toString());

        p.viewHolder.eventName.setText(eventBuilder.getEventName());
        p.viewHolder.eventDescription.setText(eventBuilder.getEventDescription());
        Picasso.with(p.context).load(eventBuilder.getEventImageURL()).into(p.viewHolder.eventImage);

        //This sets the Event object values
        //p.event.setEventValues(result);

        ViewHolder.setMapLocation(p.viewHolder.map, eventBuilder);
        //p.viewHolder.setMapLocation(p.viewHolder.map , p.event);

        //Log.d("Event check" , p.event.eventName );
    }
}
