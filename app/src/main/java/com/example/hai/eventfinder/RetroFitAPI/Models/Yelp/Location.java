
package com.example.hai.eventfinder.RetroFitAPI.Models.Yelp;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Location {

    @SerializedName("address1")
    private String mAddress1;
    @SerializedName("address2")
    private String mAddress2;
    @SerializedName("address3")
    private String mAddress3;
    @SerializedName("city")
    private String mCity;
    @SerializedName("country")
    private String mCountry;
    @SerializedName("display_address")
    private List<String> mDisplayAddress;
    @SerializedName("state")
    private String mState;
    @SerializedName("zip_code")
    private String mZipCode;

    public String getAddress1() {
        return mAddress1;
    }

    public String getAddress2() {
        return mAddress2;
    }

    public String getAddress3() {
        return mAddress3;
    }

    public String getCity() {
        return mCity;
    }

    public String getCountry() {
        return mCountry;
    }

    public List<String> getDisplayAddress() {
        return mDisplayAddress;
    }

    public String getState() {
        return mState;
    }

    public String getZipCode() {
        return mZipCode;
    }

    public static class Builder {

        private String mAddress1;
        private String mAddress2;
        private String mAddress3;
        private String mCity;
        private String mCountry;
        private List<String> mDisplayAddress;
        private String mState;
        private String mZipCode;

        public Location.Builder withAddress1(String address1) {
            mAddress1 = address1;
            return this;
        }

        public Location.Builder withAddress2(String address2) {
            mAddress2 = address2;
            return this;
        }

        public Location.Builder withAddress3(String address3) {
            mAddress3 = address3;
            return this;
        }

        public Location.Builder withCity(String city) {
            mCity = city;
            return this;
        }

        public Location.Builder withCountry(String country) {
            mCountry = country;
            return this;
        }

        public Location.Builder withDisplayAddress(List<String> displayAddress) {
            mDisplayAddress = displayAddress;
            return this;
        }

        public Location.Builder withState(String state) {
            mState = state;
            return this;
        }

        public Location.Builder withZipCode(String zipCode) {
            mZipCode = zipCode;
            return this;
        }

        public Location build() {
            Location Location = new Location();
            Location.mAddress1 = mAddress1;
            Location.mAddress2 = mAddress2;
            Location.mAddress3 = mAddress3;
            Location.mCity = mCity;
            Location.mCountry = mCountry;
            Location.mDisplayAddress = mDisplayAddress;
            Location.mState = mState;
            Location.mZipCode = mZipCode;
            return Location;
        }

    }

}
