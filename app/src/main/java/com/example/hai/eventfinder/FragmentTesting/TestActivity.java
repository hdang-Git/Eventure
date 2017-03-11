package com.example.hai.eventfinder.FragmentTesting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.hai.eventfinder.R;

//This class is used solely for testing for setup of fragments
public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setId(R.id.contentTestView);
        setContentView(R.layout.activity_test);
    }
}
