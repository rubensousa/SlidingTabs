package com.github.rsousa94.slidingtabs;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;


public class CustomViewPagerAdapter extends FragmentPagerAdapter {

    private String[] mTabsNames;
    private View[] mTabViews;
    private LayoutInflater mLayoutInflater;

    public CustomViewPagerAdapter(Activity activity, FragmentManager fm, int tabs) {
        super(fm);
        mLayoutInflater = activity.getLayoutInflater();
        mTabsNames = activity.getResources().getStringArray(R.array.tabs);
        mTabViews = new View[tabs];
    }

    public View getViewAt(int position){
        return mTabViews[position];
    }

    public void initHighlight(int position){
        for(int i = 0; i < mTabViews.length; i++){
            if(i == position) {
                if(Build.VERSION.SDK_INT < 11){
                    AlphaAnimation alpha = new AlphaAnimation(1F,1F);
                    alpha.setDuration(0);
                    alpha.setFillAfter(true);
                    mTabViews[i].startAnimation(alpha);
                }else {
                    mTabViews[i].setAlpha(1);
                }
            }
            else {
                if(Build.VERSION.SDK_INT < 11){
                    AlphaAnimation alpha = new AlphaAnimation(0.7F, 0.7F);
                    alpha.setDuration(0);
                    alpha.setFillAfter(true);
                    mTabViews[i].startAnimation(alpha);
                }else {
                    mTabViews[i].setAlpha((float) 0.7);
                }
            }
        }
    }

    public View createTabView(final int position){
        AppCompatTextView tabview = (AppCompatTextView) mLayoutInflater.inflate(R.layout.tab_view, null);
        tabview.setText(mTabsNames[position]);
        mTabViews[position] = tabview;
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
        args.putString("tab",mTabsNames[i]);
        tabFragment.setArguments(args);
        return tabFragment;
    }

    @Override
    public int getCount() {
        return mTabViews.length;
    }
}
