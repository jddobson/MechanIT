package com.mechanit.mechanitdroidapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    TextView nickname;
    TextView make;
    TextView model;
    TextView year;

    public static final String Nickname = "nicknameKey";
    public static final String Make = "makeKey";
    public static final String Model = "modelKey";
    public static final String Year = "yearKey";
    public SharedPreferences userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        nickname = (TextView) findViewById(R.id.view_nickname);
        make = (TextView) findViewById(R.id.view_make);
        model = (TextView) findViewById(R.id.view_model);
        year = (TextView) findViewById(R.id.view_year);


        userInfo = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        if (userInfo.contains(Nickname)) {
            nickname.setText(userInfo.getString(Nickname, ""));}

        if (userInfo.contains(Make)) {
            make.setText(userInfo.getString(Make, ""));}

        if (userInfo.contains(Model)) {
            model.setText(userInfo.getString(Model, ""));}

        if (userInfo.contains(Year)) {
            year.setText(String.valueOf(userInfo.getInt(Year, 0)));}

        // Navigation drawer
        final String[] values = getResources().getStringArray(R.array.nav_drawer_items);
        ((ListView) findViewById(R.id.navList0)).setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, values));
        NavigationDrawerSetup nds = new NavigationDrawerSetup((ListView) findViewById(R.id.navList0),
                (DrawerLayout) findViewById(R.id.drawer_layout), values, getSupportActionBar(), this);
        nds.configureDrawer();
    }

    /** Called when the user clicks the User Information button */
    public void viewUserInfo (View view) {
        Intent intent = new Intent(this, Maintenance.class);
        startActivity(intent);
    }

    /** Called when the user clicks the Settings button */
    public void editSettings (View view) {
        Intent intent = new Intent(this, EditSettings.class);
        startActivity(intent);
    }

    /** Called when the user clicks the Sync Data button */
    public void syncData (View view) {
        Intent intent = new Intent(this, SyncData.class);
        startActivity(intent);
    }
    /** Called when the user clicks the Trip Data button */
    public void tripData (View view) {
        Intent intent = new Intent(this, TripData.class);
        startActivity(intent);
    }

    /** Called when the user clicks the Notes button */
    public void notes (View view) {
        Intent intent = new Intent(this, Notes.class);
        startActivity(intent);
    }
}
