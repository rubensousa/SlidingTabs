package com.github.rsousa94.slidingtabs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity implements TabsParentFragment.TabListener{

    private ActionBar mActionBar;
    private int mCurrentMode = -1;
    private int mCurrentTab = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setTitle(R.string.tabmode1);

        if(savedInstanceState!=null){
            onRestoreInstanceState(savedInstanceState);
        }else {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            Fragment fragment = new TabsParentFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("mode", TabsParentFragment.SCROLLABLE_TABS);
            bundle.putInt("gravity", TabsParentFragment.LEFT_ALIGNED_TABS);
            bundle.putInt("current_tab", mCurrentTab);
            fragment.setArguments(bundle);
            transaction.add(R.id.container, fragment);
            transaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        // restore the current selected mode
        if(mCurrentMode!=-1) {
            onOptionsItemSelected(menu.findItem(mCurrentMode));
            mActionBar.setTitle(menu.findItem(mCurrentMode).getTitle());
        }

        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("current_mode",mCurrentMode);
        outState.putInt("current_tab",mCurrentTab);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCurrentMode = savedInstanceState.getInt("current_mode");
        mCurrentTab = savedInstanceState.getInt("current_tab");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if(mCurrentMode == menuItem.getItemId())
            return true;

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment previous = fragmentManager.findFragmentByTag(TabsParentFragment.class.getName());

        if(previous!=null) {
            transaction.remove(previous);
            transaction.addToBackStack(null);
        }

        Fragment fragment = new TabsParentFragment();
        Bundle bundle = new Bundle();
        mCurrentMode = menuItem.getItemId();

        if (menuItem.getItemId() == R.id.mode1) {
            bundle.putInt("mode", TabsParentFragment.SCROLLABLE_TABS);
            bundle.putInt("gravity", TabsParentFragment.LEFT_ALIGNED_TABS);
            mActionBar.setTitle(R.string.tabmode1);
        }

        if (menuItem.getItemId() == R.id.mode2) {
            bundle.putInt("mode", TabsParentFragment.FIXED_TABS);
            bundle.putInt("gravity", TabsParentFragment.CENTERED_TABS);
            mActionBar.setTitle(R.string.tabmode2);
        }

        if (menuItem.getItemId() == R.id.mode3) {
            bundle.putInt("mode", TabsParentFragment.FIXED_TABS);
            bundle.putInt("gravity", TabsParentFragment.FILLED_TABS);
            mActionBar.setTitle(R.string.tabmode3);
        }

        if (menuItem.getItemId() == R.id.mode4) {
            bundle.putInt("mode", TabsParentFragment.SCROLLABLE_TABS);
            bundle.putInt("gravity", -1);
            mActionBar.setTitle(R.string.tabmode4);
        }

        if (menuItem.getItemId() == R.id.mode5) {
            bundle.putInt("mode", TabsParentFragment.FIXED_TABS);
            bundle.putInt("gravity", TabsParentFragment.CENTERED_TABS);
            bundle.putBoolean("animate", true);
            mActionBar.setTitle(R.string.tabmode5);
        }

        fragment.setArguments(bundle);
        transaction.add(R.id.container, fragment);
        transaction.commit();

        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void setCurrentTab(int tab) {
        mCurrentTab = tab;
    }
}
