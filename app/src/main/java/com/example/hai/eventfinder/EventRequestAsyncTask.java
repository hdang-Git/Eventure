package com.example.hai.eventfinder;

import android.os.AsyncTask;
import android.os.Message;
import android.util.Log;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
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
public class EventRequestAsyncTask extends AsyncTask<ASYNCparams, Integer , ArrayList<Event>> {

    ASYNCparams p;

    String returnStringName;
    String returnStringDate;
    String returnStringTime;
    String returnStringLocation;
    String returnStringLatitude;
    String returnStringLongitude;
    String returnStringDescription;
    String returnStringImageURL;

    ArrayList<Event> returnEventArray = new ArrayList<Event>();



    //This is what happens in the thread
    @Override
    protected ArrayList doInBackground (ASYNCparams... params) {

        p = params[0];

        final String urlString = "https://www.eventbriteapi.com/v3/events/search/?token=" + p.context.getResources().getText(R.string.event_brite_key) + "&location.latitude=" + p.latitude  + "&location.longitude=" + p.longitude + "&location.within=1mi&expand=organizer,venue";

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

                //Get the number of events specified by eventbrite
                JSONObject pagination =  blockObject.getJSONObject("pagination");
                int count = pagination.getInt("object_count");
                int page_count = pagination.getInt("page_count");
                int page_size= pagination.getInt("page_size");
                Log.d("Counter", "Count: " + count);
                Log.d("Counter", "Page Count: " + page_count);

                for (int i=0; i<(page_size); i++) {


                    JSONArray eventsArray = blockObject.getJSONArray("events");
                    JSONObject event = eventsArray.getJSONObject(i);

                    JSONObject eventNameInfo = event.getJSONObject("name");
                    returnStringName = eventNameInfo.getString("text");

                    JSONObject eventDescriptionInfo = event.getJSONObject("description");
                    returnStringDescription = eventDescriptionInfo.getString("text");

                    //Check to see if a logo is even there
                    String logo = event.getString("logo");
                    if(!logo.startsWith("null")){
                        JSONObject eventImageInfo = event.getJSONObject("logo");
                        returnStringImageURL = eventImageInfo.getString("url");
                    }
                    else{
                        returnStringImageURL = "https://e2a10ce0-a-62cb3a1a-s-sites.googlegroups.com/site/shahrammohrehkesh/home/Shahram-ODU.jpg?attachauth=ANoY7crqr3OItmFh2DZDTBcd6uQLLBqUcOQaLKLuVb7vDnb6HbxBGZa91A8eA2mDAkpA-sS46up__Uhf102aCXwUW2bfax_adibGduFyOKNguPxXEVIhtFfWCj0FVkGnZME9uDKCJYTg8VzrYeO5kC60H7D9fg5eclci3_u3_aTiogy-aANF4IzRZnDAsbIb2-Tsd0pk9s8YfofVY6seseBc6GanBh3AsV1oReF7bjrQl-fqF_btWF8%3D&attredirects=1";
                    }

                    String free = event.getString("is_free");
                    if(free.startsWith("false")){
                       JSONObject eventCost = event.getJSONObject("cost");
                        int eventPrice = event.getInt("value");
                        String eventPriceString = event.getString("display");
                        }
                    else{
                        int eventPrice = 0;
                        String eventPriceString = "$0";
                    }

//                    if(event.has("logo")) {
//                        JSONObject eventImageInfo = event.getJSONObject("logo");
//                        returnStringImageURL = eventImageInfo.getString("url");
//                    }


//                    if(eventImageInfo.toString() != "null") {
//                    }
//                    else{
//                        returnStringImageURL ="http://i.imgur.com/DvpvklR.png";
//                    }

                    JSONObject venueInfo = event.getJSONObject("venue");

                    JSONObject addressInfo = venueInfo.getJSONObject("address");
                    returnStringLatitude = addressInfo.getString("latitude");
                    returnStringLongitude = addressInfo.getString("longitude");


                /*
                returnStringArray.add(returnStringName);
                returnStringArray.add(returnStringDescription);
                returnStringArray.add(returnStringImageURL);
                returnStringArray.add(returnStringLatitude);
                returnStringArray.add(returnStringLongitude);
                */

                Event eventBuilder = new Event.Builder(returnStringName)
                        .setEventDescription(returnStringDescription)
                        .setImageUrl(returnStringImageURL)
                        .setEventCoordinates(returnStringLatitude, returnStringLongitude)
                        .build();

                returnEventArray.add(eventBuilder);

                p.events.add(new Event());

                }
            }
            catch(JSONException e){
                //Log.d("Failed JSON" , "Failed JSON Pull doInBackground() for EventBrite");
                Log.d("Failed JSON" , "" + e);
            }
        }
        catch (Exception e){
            Log.d("Failed JSON" , "doInBackground() failed with Exception e");
            e.printStackTrace();
        }
        return returnEventArray;
    }

    //Just logging to check that the thread behaved correctly
    @Override
    protected void onPostExecute(ArrayList<Event> result){

        Log.d("size me up " , " " + result.size());

        for(int i=0; i<result.size(); i++ ) {
            p.events.get(i).eventName = result.get(i).getEventName();
            p.events.get(i).eventDescription = result.get(i).getEventDescription();
            p.events.get(i).eventImageURL= result.get(i).getEventImageURL();
//            try {
//                ViewHolder.setMapLocation(p.viewHolder.map, result.get(i));
//            } catch (GooglePlayServicesNotAvailableException e) {
//                Log.d("fail", e.getMessage());
//            }
        }
        Log.d("event result" , p.events.get(0).eventName);
        p.adapter.notifyDataSetChanged();


//        for(int i=0; i<result.size(); i++ ) {
//            p.viewHolder.eventName.setText(result.get(i).getEventName());
//            p.viewHolder.eventDescription.setText(result.get(i).getEventDescription());
//            Picasso.with(p.context).load(result.get(i).getEventImageURL()).into(p.viewHolder.eventImage);
//            //This sets the Event object values
//            //p.event.setEventValues(result);
//            try {
//                ViewHolder.setMapLocation(p.viewHolder.map, result.get(i));
//            } catch (GooglePlayServicesNotAvailableException e) {
//                Log.d("fail", e.getMessage());
//            }
//
//        }//End of for()

        //p.viewHolder.setMapLocation(p.viewHolder.map , p.event);

        //Log.d("Event check" , p.event.eventName );
    }
}
