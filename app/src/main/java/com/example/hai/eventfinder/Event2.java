package com.example.hai.eventfinder;

import android.os.AsyncTask;
import android.os.Message;
import android.util.Log;

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
public class Event2 extends AsyncTask<Void , Integer , ArrayList<String>> {


    public String eventName;
    public String date;
    public String time;
    public String location;

    String response;
    String returnString;

    ArrayList<String> returnStringArray = new ArrayList<String>();

    final String urlString = "https://www.eventbriteapi.com/v3/events/search/?token=AHPLYGZSAR7PQKHZPQI4&location.latitude=39.9502352&location.longitude=-75.17327569999998&location.within=1mi";

    //new Event2().execute(urlString);

    //This is what happens in the thread
    @Override
    protected ArrayList doInBackground (Void... params) {

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
                JSONObject event = eventsArray.getJSONObject(0);
                JSONObject eventNameInfo = event.getJSONObject("name");

                returnString = eventNameInfo.getString("text");

                returnStringArray.add(returnString);

                Log.d("json test", eventNameInfo.getString("text"));

                this.eventName = eventNameInfo.getString("text");
            }
            catch(JSONException e){
                Log.d("Failed JSON" , "went to catch");
            }
        }
        catch (Exception e){
            Log.d("Failed" , "This thing failed");
            e.printStackTrace();
        }

        return returnStringArray;
    }

    //The thread then gives stuff to this method
    @Override
    protected void onPostExecute(ArrayList result){

        String crayon = result.get(0).toString();

        Log.d("Hello world" , this.eventName) ;
        Log.d("Hello world again" , crayon) ;


    }
}
