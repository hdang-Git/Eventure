package com.example.hai.eventfinder;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Lenny Ramos on 2/24/2017.
 */

public class SearchableActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        handleIntent(getIntent());
    }

    @Override
    public  void onNewIntent(Intent intent){
        handleIntent(intent);
    }

    private void handleIntent(Intent intent){

        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search our data somehow
        }

    }
}
