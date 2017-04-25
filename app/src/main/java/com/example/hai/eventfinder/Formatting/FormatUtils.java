package com.example.hai.eventfinder.Formatting;

//This class is used to format passed input

import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Hai on 4/20/2017.
 */

public class FormatUtils {

    /**
     * This function retrieves the date from EventBrite API only.
     * @param datetime a datetime from EventBrite API i.e. 2017-04-29T22:00:00
     * @return date String of form MMM dd i.e. Apr 20
     */
    public static String retrieveEventBriteDate(String datetime){
        //
        if(datetime == null){
            return "";
        }
        //For eventbrite, string contains T to denote time, so get the date before that.
        int index = datetime.indexOf("T");
        String dateString = datetime.substring(0, index);
        System.out.println(dateString);
        String pattern = "yyyy-MM-dd";  //eventbrite's api date format string 2017-04-29
        //Get newly formatted date as string
        String dateFormatted;
        try{
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            Date date = df.parse(dateString);
            dateFormatted = new SimpleDateFormat("MMM dd").format(date);
        }catch(Exception e){
            return "Bad date format in retrieveEventBriteDate(): " + e.getMessage();
        }
        return dateFormatted;
    }

    /**
     * This function retrieves the time from EventBrite API only.
     *  @param datetime a datetime from EventBrite API i.e. 2017-04-29T22:00:00
     *  @return time String of form HH:mm AM/PM  i.e. 10:00 AM
     */
    public static String retrieveEventBriteTime(String datetime){
        //
        if(datetime == null){
            return "";
        }
        //For eventbrite, string contains T to denote time, so get the time after that.
        int index = datetime.indexOf("T");
        String timeString = datetime.substring(index + 1, datetime.length());
        System.out.println(timeString);
        String pattern = "hh:mm:ss";  //eventbrite's api time format string 22:00:00  (00:00:00 - 23:59:59) may include 24
        String timeReformatted;
        //Get newly formatted time as string
        try{
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            Date date = df.parse(timeString);
            timeReformatted = new SimpleDateFormat("hh:mm a").format(date);
        }catch(Exception e){
            return "Bad time format in retrieveEventBriteTime(): " + e.getMessage();
        }
        return timeReformatted;
    }
}
