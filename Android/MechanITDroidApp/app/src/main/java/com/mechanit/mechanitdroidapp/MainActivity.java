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

    public static final String Nickname = "nicknameKey";

    SharedPreferences userInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nickname = (TextView) findViewById(R.id.view_nickname);

        userInfo = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        if (userInfo.contains(Nickname))
        {
            nickname.setText(userInfo.getString(Nickname, ""));

        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
