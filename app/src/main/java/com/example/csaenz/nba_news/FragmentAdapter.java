package com.example.csaenz.nba_news;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by csaenz on 4/7/2017.
 */

public class FragmentAdapter extends FragmentPagerAdapter {

    Context mContext;

    public FragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Western_Conference_Fragment();
            default:
                return new Eastern_Conference_Fragment();
        }
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.western_fragment_title);
            case 1:
                return mContext.getString(R.string.eastern_fragment_title);
        }
        return null;
    }

    @Override
    public int getItemPosition(Object object) {
        // Allows Pager to re-create fragments
        return POSITION_NONE;
    }
}
