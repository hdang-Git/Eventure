
package com.example.hai.eventfinder.RetroFitAPI.Models.Yelp;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Business {

    @SerializedName("categories")
    private List<Category> mCategories;
    @SerializedName("coordinates")
    private Coordinates mCoordinates;
    @SerializedName("display_phone")
    private String mDisplayPhone;
    @SerializedName("distance")
    private Double mDistance;
    @SerializedName("id")
    private String mId;
    @SerializedName("image_url")
    private String mImageUrl;
    @SerializedName("is_closed")
    private Boolean mIsClosed;
    @SerializedName("location")
    private Location mLocation;
    @SerializedName("name")
    private String mName;
    @SerializedName("phone")
    private String mPhone;
    @SerializedName("price")
    private String mPrice;
    @SerializedName("rating")
    private Double mRating;
    @SerializedName("review_count")
    private Long mReviewCount;
    @SerializedName("transactions")
    private List<Object> mTransactions;
    @SerializedName("url")
    private String mUrl;

    public List<Category> getCategories() {
        return mCategories;
    }

    public Coordinates getCoordinates() {
        return mCoordinates;
    }

    public String getDisplayPhone() {
        return mDisplayPhone;
    }

    public Double getDistance() {
        return mDistance;
    }

    public String getId() {
        return mId;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public Boolean getIsClosed() {
        return mIsClosed;
    }

    public Location getLocation() {
        return mLocation;
    }

    public String getName() {
        return mName;
    }

    public String getPhone() {
        return mPhone;
    }

    public String getPrice() {
        return mPrice;
    }

    public Double getRating() {
        return mRating;
    }

    public Long getReviewCount() {
        return mReviewCount;
    }

    public List<Object> getTransactions() {
        return mTransactions;
    }

    public String getUrl() {
        return mUrl;
    }

    public static class Builder {

        private List<Category> mCategories;
        private Coordinates mCoordinates;
        private String mDisplayPhone;
        private Double mDistance;
        private String mId;
        private String mImageUrl;
        private Boolean mIsClosed;
        private Location mLocation;
        private String mName;
        private String mPhone;
        private String mPrice;
        private Double mRating;
        private Long mReviewCount;
        private List<Object> mTransactions;
        private String mUrl;

        public Business.Builder withCategories(List<Category> categories) {
            mCategories = categories;
            return this;
        }

        public Business.Builder withCoordinates(Coordinates coordinates) {
            mCoordinates = coordinates;
            return this;
        }

        public Business.Builder withDisplayPhone(String displayPhone) {
            mDisplayPhone = displayPhone;
            return this;
        }

        public Business.Builder withDistance(Double distance) {
            mDistance = distance;
            return this;
        }

        public Business.Builder withId(String id) {
            mId = id;
            return this;
        }

        public Business.Builder withImageUrl(String imageUrl) {
            mImageUrl = imageUrl;
            return this;
        }

        public Business.Builder withIsClosed(Boolean isClosed) {
            mIsClosed = isClosed;
            return this;
        }

        public Business.Builder withLocation(Location location) {
            mLocation = location;
            return this;
        }

        public Business.Builder withName(String name) {
            mName = name;
            return this;
        }

        public Business.Builder withPhone(String phone) {
            mPhone = phone;
            return this;
        }

        public Business.Builder withPrice(String price) {
            mPrice = price;
            return this;
        }

        public Business.Builder withRating(Double rating) {
            mRating = rating;
            return this;
        }

        public Business.Builder withReviewCount(Long reviewCount) {
            mReviewCount = reviewCount;
            return this;
        }

        public Business.Builder withTransactions(List<Object> transactions) {
            mTransactions = transactions;
            return this;
        }

        public Business.Builder withUrl(String url) {
            mUrl = url;
            return this;
        }

        public Business build() {
            Business Business = new Business();
            Business.mCategories = mCategories;
            Business.mCoordinates = mCoordinates;
            Business.mDisplayPhone = mDisplayPhone;
            Business.mDistance = mDistance;
            Business.mId = mId;
            Business.mImageUrl = mImageUrl;
            Business.mIsClosed = mIsClosed;
            Business.mLocation = mLocation;
            Business.mName = mName;
            Business.mPhone = mPhone;
            Business.mPrice = mPrice;
            Business.mRating = mRating;
            Business.mReviewCount = mReviewCount;
            Business.mTransactions = mTransactions;
            Business.mUrl = mUrl;
            return Business;
        }

    }

}
