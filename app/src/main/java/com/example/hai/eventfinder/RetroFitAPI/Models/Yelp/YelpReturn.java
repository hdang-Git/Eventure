
package com.example.hai.eventfinder.RetroFitAPI.Models.Yelp;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class YelpReturn {

    @SerializedName("businesses")
    private List<Business> mBusinesses;
    @SerializedName("region")
    private Region mRegion;
    @SerializedName("total")
    private Long mTotal;

    public List<Business> getBusinesses() {
        return mBusinesses;
    }

    public Region getRegion() {
        return mRegion;
    }

    public Long getTotal() {
        return mTotal;
    }

    public static class Builder {

        private List<Business> mBusinesses;
        private Region mRegion;
        private Long mTotal;

        public YelpReturn.Builder withBusinesses(List<Business> businesses) {
            mBusinesses = businesses;
            return this;
        }

        public YelpReturn.Builder withRegion(Region region) {
            mRegion = region;
            return this;
        }

        public YelpReturn.Builder withTotal(Long total) {
            mTotal = total;
            return this;
        }

        public YelpReturn build() {
            YelpReturn YelpReturn = new YelpReturn();
            YelpReturn.mBusinesses = mBusinesses;
            YelpReturn.mRegion = mRegion;
            YelpReturn.mTotal = mTotal;
            return YelpReturn;
        }

    }

}
