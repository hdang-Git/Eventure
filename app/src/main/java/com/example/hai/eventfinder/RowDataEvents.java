package com.example.hai.eventfinder;


import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBDocument;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

@DynamoDBTable(tableName = "Events")
public class RowDataEvents {

    private String name;
    private String description;
    private String latitude;
    private String longitude;
    private String url;
    private String date;
    private String time;
    private String free;

    @DynamoDBHashKey(attributeName = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {this.name = name; }

    @DynamoDBIndexRangeKey(attributeName = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @DynamoDBAttribute(attributeName = "latitude")
    public String getLatitude() {return latitude;}

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @DynamoDBAttribute(attributeName = "longitude")
    public String getLongitude() { return longitude;}

    public void setLongitude(String longitude) {this.longitude = longitude;}

    @DynamoDBAttribute(attributeName = "url")
    public String getURL() {
        return url;
    }

    public void setURL(String url) {
        this.url = url;
    }

    @DynamoDBAttribute(attributeName = "date")
    public String getDate(){ return date;}

    public void setDate(String date){ this.date = date;}

    @DynamoDBAttribute(attributeName = "time")
    public String getTime(){ return time;}

    public void setTime(String time){ this.time = time;}

    @DynamoDBAttribute(attributeName = "is_free")
    public String getFree(){ return free;}

    public void setFree(String free){ this.free= free;}
}



