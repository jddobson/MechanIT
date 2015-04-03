package com.example.john.mechanit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
/**
 * Sets up Navigation Drawer for switching between activities, instead of fragments
 * http://stackoverflow.com/q/22661405
 * http://stackoverflow.com/a/19457345
 */
public class NavigationDrawerSetup extends Activity {
    protected ListView drawerView;
    protected String[] drawerList;
    protected DrawerLayout drawerLayout;
    protected ActionBar actionBar;
    protected Activity currentActivity;
    protected ActionBarDrawerToggle mDrawerToggle;
    public NavigationDrawerSetup(ListView mDrawerView, DrawerLayout mDrawerLayout, String[] mDrawerList, ActionBar actionBar, Context currentActivity) {
        drawerView = mDrawerView;
        drawerLayout = mDrawerLayout;
        drawerList = mDrawerList;
        this.actionBar = actionBar;
        this.currentActivity = (Activity) currentActivity;
    }
    // TODO: Remove highlight effect from last selected item.
// TODO: Add the header, "Categories", above the items
    public void configureDrawer() {
//Android doesn't accept the ListView.setAdapter here
        drawerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(currentActivity, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                switch (position) {
                    case 0:
                        intent = new Intent(currentActivity, MainActivity.class);
                        break;
                    case 1:
                        intent = new Intent(currentActivity, Maintenance.class);
                        break;
                    case 2:
                        intent = new Intent(currentActivity, TripData.class);
                        break;
                    case 3:
                        intent = new Intent(currentActivity, Settings.class);
                        break;
                    case 4:
                        intent = new Intent(currentActivity, Notes.class);
                        break;

                }

                currentActivity.startActivity(intent);
            }
        });
        mDrawerToggle = new ActionBarDrawerToggle(
                currentActivity,
                drawerLayout,
                R.drawable.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close
        ) {
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }
            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        actionBar.setHomeAsUpIndicator(R.drawable.ic_drawer);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
// Sync toggle state after onRestoreInstanceState
        mDrawerToggle.syncState();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
// Pass the event to ActionBarDrawerToggle, if it returns
// true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
// Handle other action bar items
        return super.onOptionsItemSelected(item);
    }
}
