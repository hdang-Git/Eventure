package com.example.hai.eventfinder;

import android.app.DialogFragment;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.logging.Logger;


public class MainActivity extends AppCompatActivity implements Tab1.SenderInterface  {

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
        databaseTask.runDynamo();


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
    }
