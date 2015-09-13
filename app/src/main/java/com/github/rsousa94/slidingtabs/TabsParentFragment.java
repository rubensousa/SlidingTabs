package com.github.rsousa94.slidingtabs;

import android.app.Activity;
import android.content.Context;
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
    private TabListener mTabListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Prepare Animation
        mTabAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.grow_fade_in_from_bottom);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.tabs_parent, container, false);

        Toolbar toolbar = (Toolbar) layout.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_24dp);
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.setSupportActionBar(toolbar);
        ActionBar actionBar = appCompatActivity.getSupportActionBar();

        int currentTab = 0;
        int tabMode = TabLayout.MODE_FIXED;
        int tabGravity = TabLayout.GRAVITY_CENTER;

        // Get tab mode
        Bundle bundle = getArguments();

        if (bundle != null) {
            currentTab = bundle.getInt("current_tab");
            tabMode = bundle.getInt("mode");
            tabGravity = bundle.getInt("gravity");
            mAnimate = bundle.getBoolean("animate");
            if (actionBar != null) {
                actionBar.setTitle(bundle.getString("title"));
            }
        }


        mViewPager = (ViewPager) layout.findViewById(R.id.viewpager);
        TabLayout tabLayout = (TabLayout) layout.findViewById(R.id.tabs);

        if (tabMode == SCROLLABLE_TABS) {
            mAdapter = new CustomViewPagerAdapter(getActivity(), getChildFragmentManager(), tabLayout, 7);
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }

        if (tabMode == FIXED_TABS) {
            mAdapter = new CustomViewPagerAdapter(getActivity(), getChildFragmentManager(), tabLayout, 2);
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
        }

        if (tabGravity == LEFT_ALIGNED_TABS) {
            mAdapter = new CustomViewPagerAdapter(getActivity(), getChildFragmentManager(), tabLayout, 2);
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }

        if (tabGravity == CENTERED_TABS) {
            mAdapter = new CustomViewPagerAdapter(getActivity(), getChildFragmentManager(), tabLayout, 4);
            tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        }

        if (tabGravity == FILLED_TABS) {
            mAdapter = new CustomViewPagerAdapter(getActivity(), getChildFragmentManager(), tabLayout, 2);
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        }

        mViewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(mViewPager);

        // Add custom view to all tabs
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            final TabLayout.Tab tab = tabLayout.getTabAt(i);
            View view = mAdapter.createTabView(i);
            if (view != null && tab != null) {
                tab.setCustomView(view);

                /* Set the transparency to the one recommended on the guidelines
                   found on: http://www.google.com/design/spec/components/tabs.html
                 */
                if (i == 0) {
                    setAlpha(view, true);
                } else {
                    setAlpha(view, false);
                }
            }
        }

        // Set this fragment as this TabLayout listener
        tabLayout.setOnTabSelectedListener(this);

        // If the state was restored, select the previous selected tab
        if (currentTab != 0) {
            TabLayout.Tab tab = tabLayout.getTabAt(currentTab);

            if (tab != null) {
                tab.select();
            }
        }

        return layout;
    }

    private void setAlpha(View view, boolean selected) {
        if (selected) {
            if (Build.VERSION.SDK_INT < 11) {
                AlphaAnimation alpha = new AlphaAnimation(1F, 1F);
                alpha.setDuration(0);
                alpha.setFillAfter(true);
                view.startAnimation(alpha);
            } else {
                view.setAlpha(1);
            }
        } else {
            if (Build.VERSION.SDK_INT < 11) {
                AlphaAnimation alpha = new AlphaAnimation(0.7F, 0.7F);
                alpha.setDuration(0);
                alpha.setFillAfter(true);
                view.startAnimation(alpha);
            } else {
                view.setAlpha((float) 0.7);
            }
        }
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        // Get this tab view
        View v = tab.getCustomView();

        if (v != null) {
            setAlpha(v, true);
            // Animate it
            if (mAnimate)
                v.startAnimation(mTabAnimation);
        }

        mTabListener.setCurrentTab(tab.getPosition());


        // Change the viewpager item to this tab position
        if (mViewPager.getCurrentItem() != tab.getPosition())
            mViewPager.setCurrentItem(tab.getPosition(), true);

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

        // Get this tab view
        View v = tab.getCustomView();

        if (v != null) {
            setAlpha(v, false);

            // Stop the animation if the tab was animating.
            // This makes the transition smooth

            Animation viewAnimation = v.getAnimation();

            if (viewAnimation != null)
                viewAnimation.cancel();
        }


    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity = (Activity) context;

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
