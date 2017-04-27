
package com.example.hai.eventfinder.RetroFitAPI.Models.Yelp;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Coordinates {

    @SerializedName("latitude")
    private Double mLatitude;
    @SerializedName("longitude")
    private Double mLongitude;

    public Double getLatitude() {
        return mLatitude;
    }

    public Double getLongitude() {
        return mLongitude;
    }

    public static class Builder {

        private Double mLatitude;
        private Double mLongitude;

        public Coordinates.Builder withLatitude(Double latitude) {
            mLatitude = latitude;
            return this;
        }

        public Coordinates.Builder withLongitude(Double longitude) {
            mLongitude = longitude;
            return this;
        }

        public Coordinates build() {
            Coordinates Coordinates = new Coordinates();
            Coordinates.mLatitude = mLatitude;
            Coordinates.mLongitude = mLongitude;
            return Coordinates;
        }

    }

}
