package com.example.hai.eventfinder;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;

import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity implements Tab1.SenderInterface  {

    TabLayout tabLayout;
    FragmentViewPagerAdapter adapter;
    Logger log = Logger.getAnonymousLogger();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        if(fragment != null){
            log.info("fragment isn't null");
            fragment.setNumber(num);
        } else {
            Log.d("Not Initialized", "Fragment 2 isn't initialized");
        }
    }
}
