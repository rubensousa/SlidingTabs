package com.github.rsousa94.slidingtabs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {

    private static final String CURRENT_MODE = "current_mode";
    private int mCurrentMode = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        } else {
            // Add the default mode fragment first
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            Fragment fragment = new TabsParentFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(TabsParentFragment.MODE, TabsParentFragment.LEFT_ALIGNED_TABS);
            bundle.putString(TabsParentFragment.MODE_TITLE, getString(R.string.tabmode1));
            fragment.setArguments(bundle);
            transaction.add(R.id.container, fragment);
            transaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        // restore the current selected mode
        if (mCurrentMode != -1) {
            onOptionsItemSelected(menu.findItem(mCurrentMode));
        }

        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_MODE, mCurrentMode);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCurrentMode = savedInstanceState.getInt(CURRENT_MODE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (mCurrentMode == menuItem.getItemId())
            return true;

        if (menuItem.getTitle() == null) {
            return true;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment = new TabsParentFragment();
        Bundle bundle = new Bundle();
        mCurrentMode = menuItem.getItemId();

        if (menuItem.getItemId() == R.id.mode1) {
            bundle.putInt(TabsParentFragment.MODE, TabsParentFragment.LEFT_ALIGNED_TABS);
            bundle.putString(TabsParentFragment.MODE_TITLE, getString(R.string.tabmode1));
        }

        if (menuItem.getItemId() == R.id.mode2) {
            bundle.putInt(TabsParentFragment.MODE, TabsParentFragment.FIXED_CENTERED_TABS);
            bundle.putString(TabsParentFragment.MODE_TITLE, getString(R.string.tabmode2));
        }

        if (menuItem.getItemId() == R.id.mode3) {
            bundle.putInt(TabsParentFragment.MODE, TabsParentFragment.FIXED_FILLED_TABS);
            bundle.putString(TabsParentFragment.MODE_TITLE, getString(R.string.tabmode3));
        }

        if (menuItem.getItemId() == R.id.mode4) {
            bundle.putInt(TabsParentFragment.MODE, TabsParentFragment.SCROLLABLE_TABS);
            bundle.putString(TabsParentFragment.MODE_TITLE, getString(R.string.tabmode4));
        }

        if (menuItem.getItemId() == R.id.mode5) {
            bundle.putInt(TabsParentFragment.MODE, TabsParentFragment.ICON_TABS);
            bundle.putString(TabsParentFragment.MODE_TITLE, getString(R.string.tabmode5));
        }

        if (menuItem.getItemId() == R.id.mode6) {
            bundle.putInt(TabsParentFragment.MODE, TabsParentFragment.ICON_TEXT_TABS);
            bundle.putString(TabsParentFragment.MODE_TITLE, getString(R.string.tabmode6));
        }

        if (menuItem.getItemId() == R.id.mode7) {
            bundle.putInt(TabsParentFragment.MODE, TabsParentFragment.ANIMATED_TABS);
            bundle.putString(TabsParentFragment.MODE_TITLE, getString(R.string.tabmode7));
        }

        fragment.setArguments(bundle);
        transaction.replace(R.id.container, fragment, TabsParentFragment.class.getName());
        transaction.commit();

        return super.onOptionsItemSelected(menuItem);
    }

}
