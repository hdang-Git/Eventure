package com.example.hai.eventfinder;

import android.app.Activity;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;


public class SearchResult extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        handleIntent(getIntent());
    }

    //this is for the backstack, support singleTop
    @Override
    public void onNewIntent(Intent intent){
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent){

        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
            String query =
                    intent.getStringExtra(SearchManager.QUERY);

            //doSearch(query);
            //use the query to search the database of events
        }
    }

    private void doSearch(String queryString){
        //get a Cursor, prepare ListAdpater
        //and set it up
    }


}
