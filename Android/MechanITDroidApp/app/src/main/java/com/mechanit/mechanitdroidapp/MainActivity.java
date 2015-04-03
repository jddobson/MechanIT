package com.mechanit.mechanitdroidapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
            year.setText(String.valueOf(userInfo.getInt(Year,0)));}
    }



   

    /** Called when the user clicks the User Information button */
    public void viewUserInfo (View view) {
        Intent intent = new Intent(this, ViewUserInfo.class);
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
}
