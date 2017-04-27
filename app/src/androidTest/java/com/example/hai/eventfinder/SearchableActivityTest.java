package com.example.hai.eventfinder;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Created by Hai on 3/21/2017.
 */
public class SearchableActivityTest {
    @Rule
    public ActivityTestRule<SearchableActivity> myActivityRule = new ActivityTestRule<>(SearchableActivity.class);

    @Before
    public void setUp() throws Exception {
    }

/*
    @Test
    public void testButtonCreation() {
        onView(withId(R.id.button)).check(matches(isDisplayed()));
    }

    @Test
    public void testButtonClick(){
        //onView(withId(R.id.button)).perform(click());
        //ViewInteraction v = onView(withId(R.id.button));
    }
 */

    @Test
    public void testLocationOfButton(){

    }

    @Test
    public void testTextChange(){

    }

    @Test
    public void onCreate() throws Exception {

    }

    @Test
    public void onNewIntent() throws Exception {

    }

}