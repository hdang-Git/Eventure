package com.example.hai.eventfinder;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Lenny Ramos on 2/24/2017.
 */

public class SearchableActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        handleIntent(getIntent());
    }

    @Override
    public  void onNewIntent(Intent intent){
        super.onNewIntent(intent);

        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            Log.d("QUESRY", query);

            //Just make toast to check test
            Context context = getApplicationContext();
            CharSequence text = query;
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            doQuery(query);
            //use the query to search our data somehow
        }
    }
    private void doQuery(String query){
        //Do some search to DynamoDB

    }

}
