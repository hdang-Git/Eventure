
package com.example.hai.eventfinder.RetroFitAPI.Models.Yelp;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Center {

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

        public Center.Builder withLatitude(Double latitude) {
            mLatitude = latitude;
            return this;
        }

        public Center.Builder withLongitude(Double longitude) {
            mLongitude = longitude;
            return this;
        }

        public Center build() {
            Center Center = new Center();
            Center.mLatitude = mLatitude;
            Center.mLongitude = mLongitude;
            return Center;
        }

    }

}
