package com.example.hai.eventfinder.FragmentTesting;

import com.example.hai.eventfinder.Tab2;
import com.example.hai.eventfinder.R;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Hai on 3/10/2017.
 */

//This test class tests the Tab2 view
public class FragmentTestTab2 {
    @Rule
    public FragmentTestRule<Tab2> mFragmentTestRule = new FragmentTestRule<>(Tab2.class);

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
        onView(withId(R.id.submit)).check(matches(isDisplayed()));
    }

    @Test
    public void testText(){

    }
}
