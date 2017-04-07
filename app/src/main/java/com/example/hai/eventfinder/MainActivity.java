package com.example.hai.eventfinder;

import android.app.SearchManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.Toast;

//This are imports for Amazon DynamoDB

import java.util.logging.Logger;


public class MainActivity extends AppCompatActivity implements Tab1.SenderInterface {

    TabLayout tabLayout;
    FragmentViewPagerAdapter adapter;
    Logger log = Logger.getAnonymousLogger();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this,"hi",Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_main);

        //This is to run Dynamo thread,
        //So far only for three instances
        //
        DynamoThread databaseTask = new DynamoThread(this.getApplicationContext());
        //databaseTask.runDynamo();

        //Alert user to turn on network connectivity
        if(!isNetworkAvailable()){
            log.info("No network connectivity");
            String message = "Please turn on network wifi/data to use";
            //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.Theme_AppCompat_NoActionBar))
                    .setTitle("No Network Connectivity").setMessage(message);
            AlertDialog alert = builder.create();
            alert.show();
        }
        //Handle no network connectivity problem to prevent crashing of app
        StrictMode.ThreadPolicy policy = new StrictMode.
        ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        adapter = new FragmentViewPagerAdapter(getSupportFragmentManager(),
                MainActivity.this);
        viewPager.setAdapter(adapter);
        // Give the TabLayout the ViewPager
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);


    }

    @Override
    public void passNumber(int num) {
        Tab2 fragment = (Tab2) adapter.getFragment(1);
        if (fragment != null) {
            log.info("fragment isn't null");
            fragment.setNumber(num);
        } else {
            Log.d("Not Initialized", "Fragment 2 isn't initialized");
        }
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.main, menu);

        //Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

            return true;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }


    }
