package com.mechanit.mechanitdroidapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class Maintenance extends ActionBarActivity {
    TextView nickname;
    TextView make;
    TextView model;
    TextView year;
    TextView mileage;
    TextView tire;
    TextView oil;
    TextView spark;

    public static final String Nickname = "nicknameKey";
    public static final String Make = "makeKey";
    public static final String Model = "modelKey";
    public static final String Year = "yearKey";
    public static final String Mileage = "mileageKey";
    public static final String TireLife = "tireLifeKey";
    public static final String OilLife = "oilLifeKey";
    public static final String SparkLife = "sparkLifeKey";

    public SharedPreferences userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maintenance);

        nickname = (TextView) findViewById(R.id.view_nickname);
        make = (TextView) findViewById(R.id.view_make);
        model = (TextView) findViewById(R.id.view_model);
        year = (TextView) findViewById(R.id.view_year);
        mileage = (TextView) findViewById(R.id.view_mileage);
        tire = (TextView) findViewById(R.id.view_tire_life);
        oil = (TextView) findViewById(R.id.view_oil_life);
        spark = (TextView) findViewById(R.id.view_spark_life);

        userInfo = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        if (userInfo.contains(Nickname)) {
            nickname.setText(userInfo.getString(Nickname, ""));}

        if (userInfo.contains(Make)) {
            make.setText(userInfo.getString(Make, ""));}

        if (userInfo.contains(Model)) {
            model.setText(userInfo.getString(Model, ""));}

        if (userInfo.contains(Year)) {
            year.setText(String.valueOf(userInfo.getInt(Year,0)));}

        if (userInfo.contains(Mileage)) {
            mileage.setText(String.valueOf(userInfo.getInt(Mileage, 0)));}

        if (userInfo.contains(TireLife)) {
            tire.setText(String.valueOf(userInfo.getInt(TireLife,0)));}

        if (userInfo.contains(OilLife)) {
            oil.setText(String.valueOf(userInfo.getInt(OilLife, 0)));}

        if (userInfo.contains(SparkLife)) {
            spark.setText(String.valueOf(userInfo.getInt(SparkLife,0)));}
    }
    public void resetValues(View view) {
        Intent intent = new Intent(this, ResetValues.class);
        startActivity(intent);
    }
}