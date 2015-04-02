package com.mechanit.mechanitdroidapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class EditSettings extends ActionBarActivity {
    
    //Setup Text Views
    TextView nickname;
    TextView make;
    TextView model;
    TextView year;
    TextView mileage;
    TextView tire;
    TextView oil;
    TextView spark;
    TextView tireChange;
    TextView oilChange;
    TextView sparkChange;

    public static final String Nickname = "nicknameKey";
    public static final String Make = "makeKey";
    public static final String Model = "modelKey";
    public static final String Year = "yearKey";
    public static final String Mileage = "mileageKey";
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
        setContentView(R.layout.activity_edit_settings);

        nickname = (TextView) findViewById(R.id.edit_nickname);
        make = (TextView) findViewById(R.id.edit_make);
        model = (TextView) findViewById(R.id.edit_model);
        year = (TextView) findViewById(R.id.edit_year);
        mileage = (TextView) findViewById(R.id.edit_mileage);
        tire = (TextView) findViewById(R.id.edit_tire_para);
        oil = (TextView) findViewById(R.id.edit_oil_para);
        spark = (TextView) findViewById(R.id.edit_spark_para);
        tireChange = (TextView) findViewById(R.id.edit_tire_change);
        oilChange = (TextView) findViewById(R.id.edit_oil_change);
        sparkChange = (TextView) findViewById(R.id.edit_spark_change);

        userInfo = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        if (userInfo.contains(Nickname)) {
            nickname.setText(userInfo.getString(Nickname, ""));}

        if (userInfo.contains(Make)) {
            make.setText(userInfo.getString(Make, ""));}

        if (userInfo.contains(Model)) {
            model.setText(userInfo.getString(Model, ""));}

        if (userInfo.contains(Year)) {
            year.setText(String.valueOf(userInfo.getInt(Year, 0)));}

        if (userInfo.contains(Mileage)) {
            mileage.setText(String.valueOf(userInfo.getInt(Mileage,0)));}

        if (userInfo.contains(Tire)) {
            tire.setText(String.valueOf(userInfo.getInt(Tire,0)));}

        if (userInfo.contains(Oil)) {
            oil.setText(String.valueOf(userInfo.getInt(Oil,0)));}

        if (userInfo.contains(Spark)) {
            spark.setText(String.valueOf(userInfo.getInt(Spark,0)));}

        if (userInfo.contains(TireChange)) {
            tireChange.setText(String.valueOf(userInfo.getInt(TireChange,0)));}

        if (userInfo.contains(OilChange)) {
            oilChange.setText(String.valueOf(userInfo.getInt(OilChange,0)));}

        if (userInfo.contains(SparkChange)) {
            sparkChange.setText(String.valueOf(userInfo.getInt(SparkChange,0)));}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void changeT(String message) {
        Toast str = Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT);
        str.show();
    }

    public boolean NameValidator(String str) {
        for (int i = 0; i < str.length(); ++i) {
            char ch = str.charAt(i);
            if (!Character.isLetter(ch)) {
                return false;
            }
        }
        return true;
    }

    public boolean NumberValidator(String str) {
        for (int i = 0; i < str.length(); ++i) {
            char ch = str.charAt(i);
            if (!Character.isDigit(ch)) {
                return false;
            }
        }
        return true;
    }




    public void saveUserInfo() {
        EditText editNickname = (EditText) findViewById(R.id.edit_nickname);
        EditText editMake = (EditText) findViewById(R.id.edit_make);
        EditText editModel = (EditText) findViewById(R.id.edit_model);
        EditText editYear = (EditText) findViewById(R.id.edit_year);
        EditText editMileage = (EditText) findViewById(R.id.edit_mileage);
        EditText editTire = (EditText) findViewById(R.id.edit_tire_para);
        EditText editOil = (EditText) findViewById(R.id.edit_oil_para);
        EditText editSpark = (EditText) findViewById(R.id.edit_spark_para);
        EditText eTireChange = (EditText) findViewById(R.id.edit_tire_change);
        EditText eOilChange = (EditText) findViewById(R.id.edit_oil_change);
        EditText eSparkChange = (EditText) findViewById(R.id.edit_spark_change);

        SharedPreferences.Editor edit = userInfo.edit();

        if (NameValidator(editMake.getText().toString().trim())) {
            if (NameValidator(editModel.getText().toString().trim())) {
                if (NumberValidator(editYear.getText().toString().trim())) {
                    if (NumberValidator(editMileage.getText().toString().trim())) {
                        edit.putString(Nickname, editNickname.getText().toString().trim());
                        edit.putString(Make, editMake.getText().toString().trim());
                        edit.putString(Model, editModel.getText().toString().trim());
                        edit.putInt(Year, Integer.parseInt(editYear.getText().toString().trim()));
                        edit.putInt(Mileage,
                                Integer.parseInt(editMileage.getText().toString().trim()));
                        edit.putInt(Tire, Integer.parseInt(editTire.getText().toString().trim()));
                        edit.putInt(Oil, Integer.parseInt(editOil.getText().toString().trim()));
                        edit.putInt(Spark, Integer.parseInt(editSpark.getText().toString().trim()));

                        edit.putInt(TireChange,
                                Integer.parseInt(eTireChange.getText().toString().trim()));
                        edit.putInt(OilChange,
                                Integer.parseInt(eOilChange.getText().toString().trim()));
                        edit.putInt(SparkChange,
                                Integer.parseInt(eSparkChange.getText().toString().trim()));

                        edit.commit();
                        changeT("User info saved.");
                    } else changeT("Only use numbers for current mileage.");
                } else changeT("Only use numbers for the year of your vehicle.");
            } else changeT("Only use letters for the model of your vehicle.");
        } else changeT("Only use letters for the make of your vehicle.");
        edit.putInt(TireLife, (userInfo.getInt(TireChange,0) - userInfo.getInt(Tire,0)));
        edit.putInt(OilLife, (userInfo.getInt(OilChange,0) - userInfo.getInt(Oil,0)));
        edit.putInt(SparkLife, (userInfo.getInt(SparkChange,0) - userInfo.getInt(Spark,0)));
        edit.commit();
    }
    /** Called when user clicks the Send button */
    public void sendMessage(View view) {
        // Do Something in response to button
        saveUserInfo();

    }

}
