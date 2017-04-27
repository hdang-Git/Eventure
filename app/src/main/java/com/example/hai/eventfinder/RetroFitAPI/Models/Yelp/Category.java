
package com.example.hai.eventfinder.RetroFitAPI.Models.Yelp;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Category {

    @SerializedName("alias")
    private String mAlias;
    @SerializedName("title")
    private String mTitle;

    public String getAlias() {
        return mAlias;
    }

    public String getTitle() {
        return mTitle;
    }

    public static class Builder {

        private String mAlias;
        private String mTitle;

        public Category.Builder withAlias(String alias) {
            mAlias = alias;
            return this;
        }

        public Category.Builder withTitle(String title) {
            mTitle = title;
            return this;
        }

        public Category build() {
            Category Category = new Category();
            Category.mAlias = mAlias;
            Category.mTitle = mTitle;
            return Category;
        }

    }

}
