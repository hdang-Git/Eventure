package com.example.hai.eventfinder;

import android.content.Context;
import android.util.Log;

//import com.amazonaws.auth.CognitoCachingCredentialsProvider;
//import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
//import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
//import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedScanList;
//import com.amazonaws.regions.Regions;
//import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

/**
 * Created by Lenny Ramos on 3/17/2017.
 */

public class DynamoThread extends MainActivity{

    final public Context c;

    public DynamoThread(Context myContext){
        this.c = myContext;
    }

    /*
    public void runDynamo()
    {

        Runnable runnable = new Runnable() {

            public void run() {
                //DynamoDB calls go here

                // Initialize the Amazon Cognito credentials provider
                // this is for a sample database of type Books
                CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                        c,
                        "us-east-1:88da1b70-0758-448c-b3e6-196a3a688e19", // Identity Pool ID
                        Regions.US_EAST_1 // Region
                );
                //Allows us to map a class to actual database
                //initializing the actual client
                AmazonDynamoDBClient ddbClient = new AmazonDynamoDBClient(credentialsProvider);

                //This is for the mapping of the db and json files
                DynamoDBMapper mapper = new DynamoDBMapper(ddbClient);

                //This is adding a new book to the database
                RowDataBooks book = new RowDataBooks();
                book.setTitle("Great Expectations");
                book.setAuthor("Charles Dickens");
                book.setPrice(1299);
                book.setIsbn("1234567890");
                book.setHardCover(false);

                //This is adding a new book to the database
                RowDataBooks book2 = new RowDataBooks();
                book.setTitle("George Adventure");
                book.setAuthor("George Keenan");
                book.setPrice(300);
                book.setIsbn("0987654321");
                book.setHardCover(true);

                //This is adding a new book to the database
                RowDataBooks book3 = new RowDataBooks();
                book.setTitle("George Adventure");
                book.setAuthor("Lenny");
                book.setPrice(300);
                book.setIsbn("098721123");
                book.setHardCover(true);

                //This saves the actual object we created to database
                mapper.save(book);

                DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
                PaginatedScanList<RowDataBooks> result = mapper.scan(RowDataBooks.class, scanExpression);

                Log.d("result", result.get(0).toString());
                Log.d("result", result.get(1).toString());

                Log.d("result 2", result.get(2).getAuthor().toString());

            }
        };

        //Thread to handle synchronous database
        Thread mythread = new Thread(runnable);
        mythread.start();
    }
    */
}
