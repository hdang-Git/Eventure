package com.example.hai.eventfinder;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;

/**
 * Created by Hai on 3/9/2017.
 */

public class FragmentViewPagerAdapter extends FragmentStatePagerAdapter {
    final int PAGE_COUNT = 3;       //number of tabs
    //set titles for tab here
    private String tabTitles[] = new String[] { "Tab1", "Tab2", "Tab3" };
    private Context context;
    Fragment fragment;
    private final SparseArray<WeakReference<Fragment>> instantiatedFragments = new SparseArray<>();


    public FragmentViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0: fragment = EventFragment.newInstance();
                    break;
            case 1: fragment = Tab2.newInstance();
                    break;
            case 2: fragment = SearchFragment.newInstance();
                    break;
            default: fragment = Tab1.newInstance(position + 1);
                    break;
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final Fragment fragment = (Fragment) super.instantiateItem(container, position);
        instantiatedFragments.put(position, new WeakReference<>(fragment));
        return fragment;
    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        instantiatedFragments.remove(position);
        super.destroyItem(container, position, object);
    }


    @Nullable
    public Fragment getFragment(final int position) {
        final WeakReference<Fragment> wr = instantiatedFragments.get(position);
        if (wr != null) {
            return wr.get();
        } else {
            return null;
        }
    }

}
