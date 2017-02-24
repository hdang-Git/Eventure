package com.example.hai.eventfinder;

import java.util.ArrayList;

/**
 * Created by Hai on 1/11/2017.
 */

public class EventItem {
    private double price;
    private String time;
    private String date;
    private String description;
//This is gay 
    public EventItem(double price, String time, String date, String description){

    }
    public static ArrayList<EventItem> getTest() {
        ArrayList<EventItem> arr = new ArrayList<>();
        arr.add(new EventItem(3.00, "7:00PM", "1/17/17", "blah blah blah"));
        arr.add(new EventItem(4.21, "8:00PM", "1/17/17", "boop bee do"));
        arr.add(new EventItem(5.12, "9:00PM", "1/17/17", "foobar"));
        arr.add(new EventItem(6.32, "10:00PM", "1/17/17", "ha ha ha ha ha ha ha"));
        return arr;
    }
}