package com.example.john.mechanit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class Maintenance extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);
        findViewById(R.id.button7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Maintenance.this.startActivity(new Intent(Maintenance.this, TireLife.class));
            }
        });
        findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Maintenance.this.startActivity(new Intent(Maintenance.this, OilLife.class));
            }
        });
        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Maintenance.this.startActivity(new Intent(Maintenance.this, EngineCode.class));
            }
        });
        findViewById(R.id.button8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Maintenance.this.startActivity(new Intent(Maintenance.this, SparkPlug.class));
            }
        });
        // Navigation drawer
        final String[] values = getResources().getStringArray(R.array.nav_drawer_items);
        ((ListView) findViewById(R.id.navList1)).setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, values));
        NavigationDrawerSetup nds = new NavigationDrawerSetup((ListView) findViewById(R.id.navList1),
                (DrawerLayout) findViewById(R.id.drawer_layout), values, getSupportActionBar(), this);
        nds.configureDrawer();

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
}
