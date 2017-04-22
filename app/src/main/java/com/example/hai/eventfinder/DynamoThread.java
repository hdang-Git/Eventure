package com.example.hai.eventfinder;

import android.content.Context;
import android.util.Log;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedScanList;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

import java.util.ArrayList;


/**
 * This is A class that will run the thread when user creates an Event
 */

public class DynamoThread extends MainActivity{

    //final public Context c;
    final private static String IDENTITY_POOL_ID = "us-east-1:88da1b70-0758-448c-b3e6-196a3a688e19";
    ArrayList<String> eventDetails;
    Context context;

    //pass context and ArrayList
    public DynamoThread(ArrayList<String> eventDetails, Context context)
    {
        this.eventDetails = eventDetails;
        this.context = context;
    }

    public void runDynamo()
    {

        Runnable runnable = new Runnable() {

            public void run() {
                //DynamoDB calls go here

                // Initialize the Amazon Cognito credentials provider
                // this is for a sample database of type Books
                CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                        context,
                        IDENTITY_POOL_ID, // Identity Pool ID
                        Regions.US_EAST_1 // Region
                );

                //Allows us to map a class to actual database
                //initializing the actual client
                AmazonDynamoDBClient ddbClient = new AmazonDynamoDBClient(credentialsProvider);

                //This is for the mapping of the db and json files
                DynamoDBMapper mapper = new DynamoDBMapper(ddbClient);

                //This is adding a new book to the database
                RowDataEvents event = new RowDataEvents();

                event.setName(eventDetails.get(0));
                event.setDescription(eventDetails.get(1));
                event.setLatitude("dummy");
                event.setLongitude("dummy");
                event.setURL(eventDetails.get(3));
                event.setTime(eventDetails.get(4));
                event.setDate(eventDetails.get(5));


                //This saves the actual object we created to database
                mapper.save(event);

                DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
                PaginatedScanList<RowDataEvents> result = mapper.scan(RowDataEvents.class, scanExpression);

                Log.d("result", result.get(0).toString());
                Log.d("result", result.get(1).toString());

                Log.d("result 2", result.get(2).getName().toString());

            }
        };

        //Thread to handle synchronous database
        Thread mythread = new Thread(runnable);
        mythread.start();
    }

}
