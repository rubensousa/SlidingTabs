package com.github.rubensousa.slidingtabs;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class CustomViewPagerAdapter extends FragmentPagerAdapter {

    public static final int TEXT_ONLY = 0;
    public static final int ICONS_ONLY = 1;
    public static final int TEXT_AND_ICONS = 2;

    private List<Fragment> mFragments;
    private List<String> mTabsNames;
    private List<Integer> mTabsIcons;
    private LayoutInflater mLayoutInflater;
    private TabLayout mTabLayout;
    private int mMode;

    public CustomViewPagerAdapter(LayoutInflater layoutInflater, FragmentManager fm, TabLayout tabLayout, int mode) {
        super(fm);
        mLayoutInflater = layoutInflater;
        mTabsNames = new ArrayList<>();
        mFragments = new ArrayList<>();
        mTabsIcons = new ArrayList<>();
        mTabLayout = tabLayout;
        mMode = mode;
    }

    /**
     * Creates a custom tab view
     *
     * @param position the position of the tab
     * @return The tab's view
     */
    public View createTabView(int position) {
        View tabView = null;
        AppCompatTextView tabText;
        ImageView tabIcon;

        if (mMode == TEXT_ONLY) {
            tabView = mLayoutInflater.inflate(com.github.rubensousa.slidingtabs.R.layout.tabs, mTabLayout, false);
            tabText = (AppCompatTextView) tabView;
            tabText.setText(mTabsNames.get(position));
        }

        if (mMode == ICONS_ONLY) {
            tabView = mLayoutInflater.inflate(com.github.rubensousa.slidingtabs.R.layout.tabs_icons, mTabLayout, false);
            tabIcon = (ImageView) tabView.findViewById(com.github.rubensousa.slidingtabs.R.id.tab_icon);
            tabIcon.setImageResource(mTabsIcons.get(position));
        }

        if (mMode == TEXT_AND_ICONS) {
            tabView = mLayoutInflater.inflate(com.github.rubensousa.slidingtabs.R.layout.tabs_icons_text, mTabLayout, false);
            tabText = (AppCompatTextView) tabView.findViewById(com.github.rubensousa.slidingtabs.R.id.tab_text);
            tabText.setText(mTabsNames.get(position));
            tabIcon = (ImageView) tabView.findViewById(com.github.rubensousa.slidingtabs.R.id.tab_icon);
            tabIcon.setImageResource(mTabsIcons.get(position));
        }

        return tabView;
    }

    /**
     * Add a Fragment to this adapter with a tab's name and tab icon
     *
     * @param fragment Fragment to be added
     * @param tabName  Name of the tab where the fragment will be put
     * @param tabIcon  resource id of the icon to be used
     */
    public void addFragment(Fragment fragment, String tabName, int tabIcon) {
        mFragments.add(fragment);
        mTabsNames.add(tabName);
        mTabsIcons.add(tabIcon);
    }

    /**
     * Add a Fragment to this adapter with an icon
     *
     * @param fragment Fragment to be added
     * @param tabIcon  resource id of the icon to be used
     */
    public void addFragment(Fragment fragment, int tabIcon) {
        mFragments.add(fragment);
        mTabsIcons.add(tabIcon);
    }

    /**
     * Add a Fragment to this adapter with a tab's name
     *
     * @param fragment Fragment to be added
     * @param tabName  Name of the tab where the fragment will be put
     */
    public void addFragment(Fragment fragment, String tabName) {
        mFragments.add(fragment);
        mTabsNames.add(tabName);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabsNames.isEmpty() ? null : mTabsNames.get(position);
    }

    @Override
    public Fragment getItem(int i) {
        return mFragments.get(i);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
