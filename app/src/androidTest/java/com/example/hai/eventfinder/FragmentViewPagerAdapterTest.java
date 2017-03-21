package com.example.hai.eventfinder;

import android.app.Activity;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.lang.ref.WeakReference;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.actionWithAssertions;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;

/**
 * Created by Hai on 3/21/2017.
 */
public class FragmentViewPagerAdapterTest {
    @Rule
    public ActivityTestRule<MainActivity> myActivityRule = new ActivityTestRule<>(MainActivity.class);


    // onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(0).onChildView(withId(R.id.content_title)).check(matches(withText("")));

    @Test
    public void testContainsChildFragments(){
        onView(withId(R.id.viewpager)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void testSwipeOnce(){
        onView(isRoot()).perform(swipeRight());
    }

    @Test
    public void testSwipeToTab2(){
        onView(isRoot()).perform(swipeRight()).check(matches(hasDescendant(withId(R.id.button2))));
    }

    @Test
    public void test_getCount() throws Exception {
        onView(withId(R.id.viewpager)).check(matches((PageMatcher.withPageSize(2))));
    }

    @Test
    public void test_getItem() throws Exception {

    }

    @Test
    public void test_getPageTitle() throws Exception {
    }

    @Test
    public void test_instantiateItem() throws Exception {

    }

    @Test
    public void test_destroyItem() throws Exception {

    }

    @Test
    public void test_getFragment() throws Exception {

    }

}

class PageMatcher {
    public static Matcher<View> withPageSize (final int size) {
        return new TypeSafeMatcher<View>() {
            int length;
            @Override public boolean matchesSafely (final View view) {
                length = ((ViewPager) view).getChildCount();

                return length == size;
            }

            @Override public void describeTo (final Description description) {
                description.appendText ("ListView should have " + size + " items, the actual size is " + length);
            }
        };
    }
}