package com.example.hai.eventfinder;


import java.util.ArrayList;

/**
 * Created by chrx on 3/9/17.
 */

public class Event {

    public String eventName;
    public String eventDate;
    public String eventStartTime;
    public String eventEndTime;
    public String eventLatitude;
    public String eventLongitude;
    public String eventLocation;
    public String eventDescription;
    public int eventPrice;
    public String eventPriceString;
    public String eventImageURL;
    public String eventAddressShort;
    public String eventAddressLong;

    public Event(){
        this.eventImageURL = "http://i.imgur.com/DvpvklR.png";
    }

    public Event(Builder builder){
        this.eventName = builder.eventName;
        this.eventDate = builder.eventDate;
        this.eventStartTime = builder.eventStartTime;
        this.eventEndTime = builder.eventEndTime;
        this.eventPrice = builder.eventPrice;
        this.eventPriceString = builder.eventPriceString;
        this.eventLocation = builder.eventLocation;
        this.eventLongitude = builder.eventLongitude;
        this.eventLatitude = builder.eventLatitude;
        this.eventImageURL = builder.eventImageURL;
        this.eventDescription = builder.eventDescription;
        this.eventAddressShort= builder.eventAddressShort;
        this.eventAddressLong= builder.eventAddressLong;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getEventStartTime() {
        return eventStartTime;
    }

    public String getEventEndTime() {
        return eventEndTime;
    }

    public String getEventLatitude() {
        return eventLatitude;
    }

    public String getEventLongitude() {
        return eventLongitude;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public int getEventPrice(){
        return eventPrice;
    }

    public String getEventPriceString(){
        return eventPriceString;
    }

    public String getEventImageURL() {
        return eventImageURL;
    }

    public String getEventAddressShort() {
        return eventAddressShort;
    }

    public String getEventAddressLong() {
        return eventAddressLong;
    }



    @Override
    public String toString() {
        return eventName + " " + eventDate + " "
                + eventStartTime + " " + eventEndTime + " "
                + eventLocation + " " + eventLatitude + " " + eventLongitude + " "
                + eventDescription + " " + eventPrice + " " + eventImageURL;
    }


    public static class Builder{
        private String eventName;
        private String eventDate;
        private String eventStartTime;
        private String eventEndTime;
        private String eventLatitude;
        private String eventLongitude;
        private int eventPrice;
        private String eventPriceString;
        private String eventLocation;
        private String eventDescription;
        private String eventImageURL;
        private String eventAddressShort;
        private String eventAddressLong;

        public Builder(String eventName){
            this.eventName = eventName;
        }

        public Builder setDate(String eventDate){
            this.eventDate = eventDate;
            return this;
        }

        public Builder setEventStartTime(String eventStartTime){
            this.eventStartTime = eventStartTime;
            return this;
        }

        public Builder setEventEndTime(String eventEndTime){
            this.eventEndTime = eventEndTime;
            return this;
        }

        public Builder setEventLocation(String eventLocation){
            this.eventLocation = eventLocation;
            return this;
        }

        public Builder setEventCoordinates(String eventLatitude, String eventLongitude){
            this.eventLatitude = eventLatitude;
            this.eventLongitude = eventLongitude;
            return this;
        }

        public Builder setEventDescription(String eventDescription){
            this.eventDescription = eventDescription;
            return this;
        }

        public Builder setImageUrl(String eventImageURL){
            this.eventImageURL = eventImageURL;
            return this;
        }

        public Builder setEventPrice(int eventPrice){
            this.eventPrice = eventPrice;
            return this;
        }

        public Builder setEventPriceString(String eventPriceString){
            this.eventPriceString = eventPriceString;
            return this;
        }

        public Builder setEventAddressShort(String eventAddressShort){
            this.eventAddressShort = eventAddressShort;
            return this;
        }

        public Builder setEventAddressLong(String eventAddressLong){
            this.eventAddressLong= eventAddressLong;
            return this;
        }

        public Event build(){
            return new Event(this);
        }
    }
}


