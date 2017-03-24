package com.example.hai.eventfinder.FragmentTesting;


import com.example.hai.eventfinder.EventFragment;
import com.example.hai.eventfinder.R;
import com.example.hai.eventfinder.Tab2;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by Van on 3/20/2017.
 */
public class EventFragmentTest_APIget {

    @Rule
    public FragmentTestRule<EventFragment> mFragmentTestRule = new FragmentTestRule<>(EventFragment.class);

    @Test
    public void fragment_can_be_instantiated() {
        // Launch the activity to make the fragment visible
        mFragmentTestRule.launchActivity(null);
    }

    @Test
    public void testButton(){
        // Launch the activity to make the fragment visible
        mFragmentTestRule.launchActivity(null);
        // Then use Espresso to test the Fragment
        //Click the folding cell element of the adapter
        onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(0).perform(click());
        //Check the text inside of it
        onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(0).onChildView(withId(R.id.content_title)).check(matches(not(withText(("Title")))));
    }
}