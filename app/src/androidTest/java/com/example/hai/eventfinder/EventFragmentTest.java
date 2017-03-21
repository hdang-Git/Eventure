package com.example.hai.eventfinder;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

import com.example.hai.eventfinder.FragmentTesting.FragmentTestRule;


/**
 * Created by Hai on 3/20/2017.
 */
public class EventFragmentTest {
    @Rule
    public FragmentTestRule<EventFragment> mFragmentTestRule = new FragmentTestRule<>(EventFragment.class);

    @Test
    public void fragment_can_be_instantiated() {
        // Launch the activity to make the fragment visible
        mFragmentTestRule.launchActivity(null);
    }

    @Test
    public void testIsDisplaying(){
        // Launch the activity to make the fragment visible
        mFragmentTestRule.launchActivity(null);
        // Then use Espresso to test the Fragment
        onView(withId(R.id.button2)).check(matches(isDisplayed()));
    }

    @Test
    public void testText(){
        // Launch the activity to make the fragment visible
        mFragmentTestRule.launchActivity(null);
        onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(0).onChildView(withId(R.id.content_title)).check(matches(withText("")));
    }
}