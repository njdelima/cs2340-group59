package com.nullpointexecutioners.buzzfilms.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.nullpointexecutioners.buzzfilms.R;

public class RecentReleasesFragmentPagerAdapter extends FragmentPagerAdapter {

    static final int PAGE_COUNT = 2;
    private Context context;

    /**
     * Constructor for the RecentReleasesFragmentPagerAdapter
     * @param fm used for getting the fragment
     * @param context context for the PagerAdapter
     */
    public RecentReleasesFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        return RecentReleasesFragment.newInstance(position + 1);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getResources().getString(R.string.recent_movies_title);
            case 1:
                return context.getResources().getString(R.string.recent_dvds_title);
        }
        return super.getPageTitle(position);
    }
}
