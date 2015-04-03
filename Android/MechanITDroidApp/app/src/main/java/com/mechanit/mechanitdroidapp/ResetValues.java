package com.mechanit.mechanitdroidapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class ResetValues extends ActionBarActivity {

    TextView nickname;
    TextView make;
    TextView model;
    TextView year;

    public static final String Nickname = "nicknameKey";
    public static final String Make = "makeKey";
    public static final String Model = "modelKey";
    public static final String Year = "yearKey";
    public static final String Tire = "tireKey";
    public static final String Oil = "oilKey";
    public static final String Spark = "sparkKey";
    public static final String TireLife = "tireLifeKey";
    public static final String OilLife = "oilLifeKey";
    public static final String SparkLife = "sparkLifeKey";
    public static final String TireChange = "tireChangeKey";
    public static final String OilChange = "oilChangeKey";
    public static final String SparkChange = "sparkChangeKey";

    public SharedPreferences userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_values);

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

    public void resetOil(View view) {

        SharedPreferences.Editor edit = userInfo.edit();
        edit.putInt(OilLife, userInfo.getInt(OilChange, 0));
        edit.putInt(Oil, 0);
        edit.commit();
    }
    public void resetTires(View view) {
        SharedPreferences.Editor edit = userInfo.edit();
        edit.putInt(TireLife, userInfo.getInt(TireChange, 0));
        edit.putInt(Tire, 0);
        edit.commit();
    }
    public void resetSpark(View view) {
        SharedPreferences.Editor edit = userInfo.edit();
        edit.putInt(SparkLife, userInfo.getInt(SparkChange, 0));
        edit.putInt(Spark, 0);
        edit.commit();
    }
}
