package com.example.hai.eventfinder;

import android.os.AsyncTask;
import android.os.Message;
import android.util.Log;

import com.example.hai.eventfinder.Formatting.FormatUtils;
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
    String returnStringStartTime;
    String returnStringEndTime;
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
                Log.d("downloaded data", response);
                //Get the number of events specified by eventbrite
                JSONObject pagination =  blockObject.getJSONObject("pagination");
                int count = pagination.getInt("object_count");
                int page_count = pagination.getInt("page_count");
                int page_size = pagination.getInt("page_size");
                Log.d("Counter", "Count: " + count);
                Log.d("Counter", "Page Count: " + page_count);

                //TODO: change max number to count
                for (int i = 0; i< page_size; i++) {

                    p.events.add(new Event());

                    JSONArray eventsArray = blockObject.getJSONArray("events");
                    JSONObject event = eventsArray.getJSONObject(i);

                    JSONObject eventNameInfo = event.getJSONObject("name");
                    returnStringName = eventNameInfo.getString("text");

                    JSONObject eventDescriptionInfo = event.getJSONObject("description");
                    returnStringDescription = eventDescriptionInfo.getString("text");

                    //Null check to prevent error of retrieving a null object
                    String logo = event.getString("logo");
                    if(!logo.startsWith("null")){
                        JSONObject eventImageInfo = event.getJSONObject("logo");
                        returnStringImageURL = eventImageInfo.getString("url");
                    } else {
                        returnStringImageURL = "https://e2a10ce0-a-62cb3a1a-s-sites.googlegroups.com/site/shahrammohrehkesh/home/Shahram-ODU.jpg?attachauth=ANoY7co6IBUSOZxM83j_f0yNEl7G4bhCDaS3Ci-apKgHc5_TRHQ8E9SL7BAsc6dFw6nzwkDzi9rqMnIHuxBMB2sIagkS7tzQdyXi7K_r8NKMCmPFB6U9nWMAKTKRTdQXDK-lYXvQDxw7coKsaI2VbXhnEi4J7BfP2TUKGVjQNIlQJGQ9L2Px3HXD9OpqrUc0H-1gvItnSgNaYbe-y8v03briC7bRtMgIsKw1ab-bcC4X3BsL9Q0yp9s%3D&attredirects=0";
                    }

                    //Get Event Date
                    JSONObject eventDate = event.getJSONObject("start");
                    returnStringDate = FormatUtils.retrieveEventBriteDate(eventDate.getString("local"));
                    //Get Event Start Time
                    JSONObject eventStartTime = event.getJSONObject("start");
                    returnStringStartTime = FormatUtils.retrieveEventBriteTime(eventStartTime.getString("local"));
                    //Get Event End Time
                    JSONObject endDateTime = event.getJSONObject("end");
                    returnStringEndTime = FormatUtils.retrieveEventBriteTime(endDateTime.getString("local"));


                    JSONObject venueInfo = event.getJSONObject("venue");
/*
                    JSONObject addressInfo = venueInfo.getJSONObject("address");
                    returnStringLatitude = addressInfo.getString("latitude");
                    returnStringLongitude = addressInfo.getString("longitude");
*/
                    Event eventBuilder = new Event.Builder(returnStringName)
                            .setEventDescription(returnStringDescription)
                            .setImageUrl(returnStringImageURL)
                            .setDate(returnStringDate)
                            .setEventStartTime(returnStringStartTime)
                            .setEventEndTime(returnStringEndTime)
                            //.setEventCoordinates(returnStringLatitude, returnStringLongitude)
                            .build();

                    returnEventArray.add(eventBuilder);
                }
            } catch(JSONException e){
                Log.d("Failed JSON" , "Failed JSON Pull doInBackground() for EventBrite: " + e.getMessage());
            }
        } catch (Exception e){
            Log.d("Failed JSON" , "doInBackground() failed with Exception e: " + e.getMessage());
            e.printStackTrace();
        }
        return returnEventArray;
    }

    //Just logging to check that the thread behaved correctly
    @Override
    protected void onPostExecute(ArrayList<Event> result){

        Log.d("size me up " , " " + result.size());
        //Update data in passed array from EventFragment with json data
        for(int i=0; i<result.size(); i++ ) {
            p.events.get(i).eventName = result.get(i).getEventName();
            p.events.get(i).eventDescription = result.get(i).getEventDescription();
            p.events.get(i).eventImageURL = result.get(i).getEventImageURL();
            p.events.get(i).eventDate = result.get(i).getEventDate();
            p.events.get(i).eventStartTime = result.get(i).getEventStartTime();
            p.events.get(i).eventEndTime = result.get(i).getEventEndTime();

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
