package com.example.hai.eventfinder;

        import android.app.ProgressDialog;
        import android.content.Context;
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
public class UserEventAsyncTask extends AsyncTask<ASYNCparams, Integer , ArrayList<Event>> {

    ASYNCparams p;

    Context myContext;

    EventRequestAsyncTask myEventRequestAsyncTask;

    String returnStringName;
    String returnStringDate;
    String returnStringStartTime;
    String returnStringEndTime;
    String returnStringLocation;
    String returnStringLatitude;
    String returnStringLongitude;
    String returnStringDescription;
    String returnStringImageURL;
    int returnEventPrice;
    String returnEventPriceString;

    ProgressDialog mProgressBar;

    ArrayList<Event> returnEventArray = new ArrayList<Event>();


    public UserEventAsyncTask(Context c , EventRequestAsyncTask async){
        myContext = c;
        myEventRequestAsyncTask = async;
    }


    //This is what happens in the thread
    @Override
    protected ArrayList doInBackground (ASYNCparams... params) {

        p = params[0];

        final String urlString = "https://ftrgldsibk.execute-api.us-east-1.amazonaws.com/sith/events/asf";

        try{
            Log.d("Final URL:" , urlString);
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

                //TODO: change max number to count
                for (int i = 0; i<2; i++) {


                    JSONArray eventsArray = blockObject.getJSONArray("Events");
                    JSONObject event = eventsArray.getJSONObject(i);

                    JSONObject eventNameInfo = event.getJSONObject("name");
                    returnStringName = eventNameInfo.getString("text");

                    JSONObject eventDescriptionInfo = event.getJSONObject("description");
                    returnStringDescription = eventDescriptionInfo.getString("text");

                    //Check to see if a logo is even there
                    String logo = event.getString("logo");
                    if (!logo.startsWith("null")) {
                        JSONObject eventImageInfo = event.getJSONObject("logo");
                        returnStringImageURL = eventImageInfo.getString("url");
                    } else {
                        returnStringImageURL = "https://e2a10ce0-a-62cb3a1a-s-sites.googlegroups.com/site/shahrammohrehkesh/home/Shahram-ODU.jpg?attachauth=ANoY7crqr3OItmFh2DZDTBcd6uQLLBqUcOQaLKLuVb7vDnb6HbxBGZa91A8eA2mDAkpA-sS46up__Uhf102aCXwUW2bfax_adibGduFyOKNguPxXEVIhtFfWCj0FVkGnZME9uDKCJYTg8VzrYeO5kC60H7D9fg5eclci3_u3_aTiogy-aANF4IzRZnDAsbIb2-Tsd0pk9s8YfofVY6seseBc6GanBh3AsV1oReF7bjrQl-fqF_btWF8%3D&attredirects=1";
                    }

                    /*
                    Boolean free = event.getBoolean("is_free");
                    if (free == false) {
                        JSONArray eventTicketsArray = event.getJSONArray("ticket_classes");
                        int drill = 0;
                        while (!eventTicketsArray.getJSONObject(drill).has("cost") && (drill < eventTicketsArray.length())) {
                            drill++;
                        }
                        JSONObject eventTicket = eventTicketsArray.getJSONObject(drill);//TODO take into account the other types of tickets(don't just grab cheapest price
                        JSONObject eventCost = eventTicket.getJSONObject("cost");
                        returnEventPrice = eventCost.getInt("value");
                        returnEventPriceString = eventCost.getString("display");
                    } else {
                        returnEventPrice = 0;
                        returnEventPriceString = "$0";
                    }
                    */
                    returnEventPrice = 0;
                    returnEventPriceString = "$0";


                    returnStringDate = FormatUtils.retrieveEventBriteDate("2014-09-24T22:00:00");
                    returnStringStartTime = FormatUtils.retrieveEventBriteTime("2014-09-24T22:00:00");
                    returnStringEndTime = FormatUtils.retrieveEventBriteTime("2014-09-24T22:00:00");

                    JSONObject venueInfo = event.getJSONObject("venue");

                    JSONObject addressInfo = venueInfo.getJSONObject("address");
                    returnStringLatitude = addressInfo.getString("latitude");
                    returnStringLongitude = addressInfo.getString("longitude");

                    Event eventBuilder = new Event.Builder(returnStringName)
                            .setEventDescription(returnStringDescription)
                            .setImageUrl(returnStringImageURL)
                            .setDate(returnStringDate)
                            .setEventStartTime(returnStringStartTime)
                            .setEventEndTime(returnStringEndTime)
                            .setEventPrice(returnEventPrice)
                            .setEventPriceString(returnEventPriceString)
                            .setEventCoordinates(returnStringLatitude, returnStringLongitude)
                            .build();

                    //For default text (if no JSON to replace default stuff)
                    p.events.add(new Event());

                    returnEventArray.add(eventBuilder);
                }
            } catch(JSONException e){
                Log.d("Failed JSON" , "Failed JSON Pull doInBackground() for UserRequest: " + e.getMessage());
                return returnEventArray;
            }
        } catch (Exception e){
            Log.d("Failed JSON" , "doInBackground() failed with Exception e(UserRequest): " + e.getMessage());
            e.printStackTrace();
            return returnEventArray;
        }
        return returnEventArray;
    }

    //Just logging to check that the thread behaved correctly
    @Override
    protected void onPostExecute(ArrayList<Event> result){

        /*
        for(int i = 0; i < result.size(); i++) {
            Log.d("size of result" , String.valueOf(result.size()));
            myEventRequestAsyncTask.returnEventArray.add(result.get(i));
        }
        */
//        EventRequestAsyncTask BriteRequest = new EventRequestAsyncTask(p.context);
//        myEventRequestAsyncTask.returnEventArray = result;
//        myEventRequestAsyncTask.execute();
        EventRequestAsyncTask BriteRequest = new EventRequestAsyncTask(p.context);
        BriteRequest.returnEventArray = result;
        BriteRequest.execute(p);


        /*
        if (mProgressBar.isShowing()) {
            mProgressBar.dismiss();
        }

        Log.d("size me up " , " " + result.size());

        for(int i=0; i<result.size(); i++ ) {
            p.events.get(i).eventName = result.get(i).getEventName();
            p.events.get(i).eventDescription = result.get(i).getEventDescription();
            p.events.get(i).eventImageURL = result.get(i).getEventImageURL();
            p.events.get(i).eventPrice= result.get(i).getEventPrice();
            p.events.get(i).eventPriceString= result.get(i).getEventPriceString();
            p.events.get(i).eventDate = result.get(i).getEventDate();
            p.events.get(i).eventStartTime = result.get(i).getEventStartTime();
            p.events.get(i).eventEndTime = result.get(i).getEventEndTime();
            p.events.get(i).eventLatitude = result.get(i).getEventLatitude();
            p.events.get(i).eventLongitude=  result.get(i).getEventLongitude();
//            try {
//                ViewHolder.setMapLocation(p.viewHolder.map, result.get(i));
//            } catch (GooglePlayServicesNotAvailableException e) {
//                Log.d("fail", e.getMessage());
//            }
        }
        //Log.d("event result" , p.events.get(0).eventName);
        p.adapter.notifyDataSetChanged();
        */


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
