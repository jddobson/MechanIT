package com.mechanit.mechanitdroidapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
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
    TextView rotation;
    TextView tire;
    TextView oil;
    TextView spark;
    TextView rotationChange;
    TextView tireChange;
    TextView oilChange;
    TextView sparkChange;

    public static final String Nickname = "nicknameKey";
    public static final String Make = "makeKey";
    public static final String Model = "modelKey";
    public static final String Year = "yearKey";
    public static final String Mileage = "mileageKey";
    public static final String Rotation = "rotationKey";
    public static final String Tire = "tireKey";
    public static final String Oil = "oilKey";
    public static final String Spark = "sparkKey";
    public static final String RotLife = "rotLifeKey";
    public static final String TireLife = "tireLifeKey";
    public static final String OilLife = "oilLifeKey";
    public static final String SparkLife = "sparkLifeKey";
    public static final String RotationChange = "rotationChangeKey";
    public static final String TireChange = "tireChangeKey";
    public static final String OilChange = "oilChangeKey";
    public static final String SparkChange = "sparkChangeKey";

    public int checker = 0;

    public SharedPreferences userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_settings);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        nickname = (TextView) findViewById(R.id.edit_nickname);
        make = (TextView) findViewById(R.id.edit_make);
        model = (TextView) findViewById(R.id.edit_model);
        year = (TextView) findViewById(R.id.edit_year);
        mileage = (TextView) findViewById(R.id.edit_mileage);
        rotation = (TextView) findViewById(R.id.edit_rotation);
        tire = (TextView) findViewById(R.id.edit_tire_para);
        oil = (TextView) findViewById(R.id.edit_oil_para);
        spark = (TextView) findViewById(R.id.edit_spark_para);
        rotationChange = (TextView) findViewById(R.id.edit_rotation_change);
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

        if (userInfo.contains(Rotation)) {
            rotation.setText(String.valueOf(userInfo.getInt(Rotation, 0)));}

        if (userInfo.contains(Tire)) {
            tire.setText(String.valueOf(userInfo.getInt(Tire,0)));}

        if (userInfo.contains(Oil)) {
            oil.setText(String.valueOf(userInfo.getInt(Oil,0)));}

        if (userInfo.contains(Spark)) {
            spark.setText(String.valueOf(userInfo.getInt(Spark,0)));}

        if (userInfo.contains(RotationChange)) {
            rotationChange.setText(String.valueOf(userInfo.getInt(RotationChange,0)));}

        if (userInfo.contains(TireChange)) {
            tireChange.setText(String.valueOf(userInfo.getInt(TireChange,0)));}

        if (userInfo.contains(OilChange)) {
            oilChange.setText(String.valueOf(userInfo.getInt(OilChange,0)));}

        if (userInfo.contains(SparkChange)) {
            sparkChange.setText(String.valueOf(userInfo.getInt(SparkChange,0)));}
    }



    public void changeT(String message) {
        Toast str = Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT);
        str.show();
    }

    public boolean NameValidator(EditText et) {
        String str = et.getText().toString().trim();
        for (int i = 0; i < str.length(); ++i) {
            char ch = str.charAt(i);
            if (!Character.isLetter(ch)) {
                checker = 1;
                return false;
            }
        }
        return true;
    }

    public boolean NumberValidator (EditText et) {
        String str = et.getText().toString().trim();
        for (int i = 0; i < str.length(); ++i) {
            char ch = str.charAt(i);
            if (!Character.isDigit(ch)) {
                checker = 1;
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
        EditText editRotation = (EditText) findViewById(R.id.edit_rotation);
        EditText editTire = (EditText) findViewById(R.id.edit_tire_para);
        EditText editOil = (EditText) findViewById(R.id.edit_oil_para);
        EditText editSpark = (EditText) findViewById(R.id.edit_spark_para);
        EditText eRotChange = (EditText) findViewById(R.id.edit_rotation_change);
        EditText eTireChange = (EditText) findViewById(R.id.edit_tire_change);
        EditText eOilChange = (EditText) findViewById(R.id.edit_oil_change);
        EditText eSparkChange = (EditText) findViewById(R.id.edit_spark_change);

        SharedPreferences.Editor edit = userInfo.edit();

        edit.putString(Nickname, editNickname.getText().toString().trim());
        if (NameValidator(editMake)) {
            edit.putString(Make, editMake.getText().toString().trim());}
        if (NameValidator(editModel)) {
            edit.putString(Model, editModel.getText().toString().trim());}


        if (NumberValidator(editYear)) {
            edit.putInt(Year, Integer.parseInt(editYear.getText().toString().trim()));}
        if (NumberValidator(editMileage)) {
            edit.putInt(Mileage, Integer.parseInt(editMileage.getText().toString().trim()));}
        if (NumberValidator(editRotation)) {
            edit.putInt(Rotation, Integer.parseInt(editRotation.getText().toString().trim()));}
        if (NumberValidator(editTire)) {
            edit.putInt(Tire, Integer.parseInt(editTire.getText().toString().trim()));}
        if (NumberValidator(editOil)) {
            edit.putInt(Oil, Integer.parseInt(editOil.getText().toString().trim()));}
        if (NumberValidator(editSpark)) {
            edit.putInt(Spark, Integer.parseInt(editSpark.getText().toString().trim()));}
        if (NumberValidator(eRotChange)) {
            edit.putInt(RotationChange, Integer.parseInt(eRotChange.getText().toString().trim()));}
        if (NumberValidator(eTireChange)) {
            edit.putInt(TireChange, Integer.parseInt(eTireChange.getText().toString().trim()));}
        if (NumberValidator(eOilChange)) {
            edit.putInt(OilChange, Integer.parseInt(eOilChange.getText().toString().trim()));}
        if (NumberValidator(eSparkChange)) {
            edit.putInt(SparkChange, Integer.parseInt(eSparkChange.getText().toString().trim()));}

        if (checker == 0) {
            edit.commit();
            edit.putInt(RotLife, (userInfo.getInt(RotationChange,0)-userInfo.getInt(Rotation, 0)));
            edit.putInt(TireLife, (userInfo.getInt(TireChange, 0)-userInfo.getInt(Tire, 0)));
            edit.putInt(OilLife, (userInfo.getInt(OilChange, 0)-userInfo.getInt(Oil, 0)));
            edit.putInt(SparkLife, (userInfo.getInt(SparkChange, 0)-userInfo.getInt(Spark, 0)));
            edit.commit();
            changeT("User info saved.");
        } else changeT("Inputs did not pass validator");}

    /** Called when user clicks the Send button */
    public void sendMessage(View view) {
        // Do Something in response to button
        saveUserInfo();

    }

}
