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


    public static final String Nickname = "nicknameKey";
    public static final String Make = "makeKey";
    public static final String Model = "modelKey";
    public static final String Year = "yearKey";
    public static final String Mileage = "mileageKey";
    public static final String Tire = "tireKey";
    public static final String Oil = "oilKey";
    public static final String Spark = "sparkKey";

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

        userInfo = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        if (userInfo.contains(Nickname))
        {
            nickname.setText(userInfo.getString(Nickname, ""));

        }
        if (userInfo.contains(Make))
        {
            make.setText(userInfo.getString(Make, ""));

        }
        if (userInfo.contains(Model))
        {
            model.setText(userInfo.getString(Model, ""));

        }
        if (userInfo.contains(Year))
        {
            year.setText(userInfo.getString(Year, ""));

        }
        if (userInfo.contains(Mileage))
        {
            mileage.setText(userInfo.getString(Mileage,""));

        }

        if (userInfo.contains(Tire))
        {
            tire.setText(userInfo.getString(Tire,""));

        }

        if (userInfo.contains(Oil))
        {
            oil.setText(userInfo.getString(Oil,""));

        }

        if (userInfo.contains(Spark))
        {
            spark.setText(userInfo.getString(Spark,""));

        }
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

    public void main(String[] arg) {
        SharedPreferences.Editor edit = userInfo.edit();




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



        SharedPreferences.Editor edit = userInfo.edit();

        if (NameValidator(editNickname.getText().toString().trim())) {
            if (NameValidator(editMake.getText().toString().trim())) {
                if (NameValidator(editModel.getText().toString().trim())) {
                    if (NumberValidator(editYear.getText().toString().trim())) {
                        if (NumberValidator(editMileage.getText().toString().trim())) {
                            edit.putString("nicknameKey", editNickname.getText().toString().trim());
                            edit.putString("makeKey", editMake.getText().toString().trim());
                            edit.putString("modelKey", editModel.getText().toString().trim());
                            edit.putString("yearKey", editYear.getText().toString().trim());
                            edit.putString("mileageKey", editMileage.getText().toString().trim());
                            edit.putString("tireKey", editTire.getText().toString().trim());
                            edit.putString("oilKey", editOil.getText().toString().trim());
                            edit.putString("sparkKey", editSpark.getText().toString().trim());

                            edit.commit();

                            Toast.makeText(this, "User info has been saved.", Toast.LENGTH_SHORT)
                                    .show();
                        } else {
                            Toast.makeText(this, "Please only use numbers.", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    } else {
                        Toast.makeText(this, "Please only use numbers.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Please only use letters.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please only use letters.", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "Please only use letters.", Toast.LENGTH_SHORT).show();
        }

    }
    /** Called when user clicks the Send button */
    public void sendMessage(View view) {
        // Do Something in response to button
        saveUserInfo();

    }

}
