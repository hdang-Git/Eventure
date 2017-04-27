
package com.example.hai.eventfinder.RetroFitAPI.Models.Yelp;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Region {

    @SerializedName("center")
    private Center mCenter;

    public Center getCenter() {
        return mCenter;
    }

    public static class Builder {

        private Center mCenter;

        public Region.Builder withCenter(Center center) {
            mCenter = center;
            return this;
        }

        public Region build() {
            Region Region = new Region();
            Region.mCenter = mCenter;
            return Region;
        }

    }

}
