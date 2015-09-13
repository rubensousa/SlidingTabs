package com.github.rsousa94.slidingtabs;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;

public class CustomViewPagerAdapter extends FragmentPagerAdapter {

    private String[] mTabsNames;
    private LayoutInflater mLayoutInflater;
    private int mTabCount;
    private TabLayout mTabLayout;

    public CustomViewPagerAdapter(Activity activity, FragmentManager fm, TabLayout tabLayout, int tabs) {
        super(fm);
        mLayoutInflater = activity.getLayoutInflater();
        mTabsNames = activity.getResources().getStringArray(R.array.tabs);
        mTabLayout = tabLayout;
        mTabCount = tabs;
    }

    public View createTabView(final int position) {
        AppCompatTextView tabview = (AppCompatTextView) mLayoutInflater.inflate(R.layout.tab_view, mTabLayout, false);
        tabview.setText(mTabsNames[position]);
        return tabview;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabsNames[position];
    }

    @Override
    public Fragment getItem(int i) {
        TabFragment tabFragment = new TabFragment();
        Bundle args = new Bundle();
        args.putString("tab", mTabsNames[i]);
        tabFragment.setArguments(args);
        return tabFragment;
    }

    @Override
    public int getCount() {
        return mTabCount;
    }
}
