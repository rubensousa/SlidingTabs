package com.github.rsousa94.slidingtabs;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


public class TabsParentFragment extends Fragment implements TabLayout.OnTabSelectedListener {

    public static final int FIXED_TABS = 0;
    public static final int SCROLLABLE_TABS = 1;
    public static final int LEFT_ALIGNED_TABS = 0;
    public static final int CENTERED_TABS = 1;
    public static final int FILLED_TABS = 2;

    private boolean mAnimate;
    private Animation mTabAnimation;
    private CustomViewPagerAdapter mAdapter;
    private ViewPager mViewPager;
    private int mTabMode = 0;
    private int mTabGravity = 0;
    private TabListener mTabListener;
    public ActionBar mActionBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Prepare Animation
        mTabAnimation = AnimationUtils.loadAnimation(getActivity(),R.anim.abc_grow_fade_in_from_bottom);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.tabs_parent, container, false);

        Toolbar toolbar = (Toolbar) layout.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        mActionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();

        // Get tab mode
        Bundle bundle = getArguments();
        int currentTab = bundle.getInt("current_tab");
        mTabMode = bundle.getInt("mode");
        mTabGravity = bundle.getInt("gravity");
        mAnimate = bundle.getBoolean("animate");
        mActionBar.setTitle(bundle.getString("title"));

        mViewPager = (ViewPager) layout.findViewById(R.id.viewpager);
        TabLayout tabLayout = (TabLayout) layout.findViewById(R.id.tabs);

        if(mTabMode == SCROLLABLE_TABS) {
            mAdapter = new CustomViewPagerAdapter(getActivity(),getChildFragmentManager(),7);
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }

        if(mTabMode == FIXED_TABS) {
            mAdapter = new CustomViewPagerAdapter(getActivity(),getChildFragmentManager(),2);
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
            tabLayout.setPadding(getActivity().getResources()
                            .getDimensionPixelSize(R.dimen.navigation_icon_padding), 0,
                    getActivity().getResources()
                            .getDimensionPixelSize(R.dimen.navigation_icon_padding), 0);
        }

        if(mTabGravity == LEFT_ALIGNED_TABS){
            mAdapter = new CustomViewPagerAdapter(getActivity(),getChildFragmentManager(),2);
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }

        if(mTabGravity == CENTERED_TABS) {
            mAdapter = new CustomViewPagerAdapter(getActivity(),getChildFragmentManager(),4);
            tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        }

        if(mTabGravity == FILLED_TABS) {
            mAdapter = new CustomViewPagerAdapter(getActivity(),getChildFragmentManager(),2);
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        }

        mViewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(mViewPager);

        // Add custom view to all tabs
        for(int i = 0; i< tabLayout.getTabCount(); i++){
            final TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(mAdapter.createTabView(i));
        }

        // If a custom view was set, initialize the alpha of the views
        mAdapter.initHighlight(0);

        // Set this fragment as this TabLayout listener
       tabLayout.setOnTabSelectedListener(this);

        // If the state was restored, select the previous selected tab
        if(currentTab != 0)
            tabLayout.getTabAt(currentTab).select();

        return layout;
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        // Get this tab view
        View v = mAdapter.getViewAt(tab.getPosition());
        mTabListener.setCurrentTab(tab.getPosition());

        // Animate it
        if(mAnimate)
            v.startAnimation(mTabAnimation);

        // Change the viewpager item to this tab position
        if(mViewPager.getCurrentItem()!=tab.getPosition())
            mViewPager.setCurrentItem(tab.getPosition(), true);


        if(Build.VERSION.SDK_INT < 11){
            AlphaAnimation alpha = new AlphaAnimation(1F, 1F);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            v.startAnimation(alpha);
        }else{
            v.setAlpha(1);
        }

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        View v = mAdapter.getViewAt(tab.getPosition());

        /*
           Stop the animation if the tab was animating.
            This makes the transition smooth
         */
        Animation viewAnimation = v.getAnimation();

        if(viewAnimation!=null)
            viewAnimation.cancel();

        /* Set the transparency to the one recommended on the guidelines
           found on: http://www.google.com/design/spec/components/tabs.html
        */

        if(Build.VERSION.SDK_INT < 11){
            AlphaAnimation alpha = new AlphaAnimation(0.7F, 0.7F);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            v.startAnimation(alpha);
        }else{
            v.setAlpha((float)0.7);
        }
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mTabListener = (TabListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement TabListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mTabListener = null;
    }

    public interface TabListener {
        void setCurrentTab(int tab);
    }



}
