package com.mechanit.mechanitdroidapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.UUID;


public class SyncData extends ActionBarActivity {

    private final static int REQUEST_ENABLE_BT = 1;

    private BluetoothAdapter adapter;
    private BluetoothSocket socket;
    private BluetoothDevice device;

    TextView syncSuccess;

    public SharedPreferences userInfo;

    //byte SYNC = 0;
    byte CDTC = 1;
    //byte RDTC = 2;

    public static final String LastVal = "lastValue";
    public static final String Mileage = "mileageKey";
    public static final String Tire = "tireKey";
    public static final String Oil = "oilKey";
    public static final String Spark = "sparkKey";
    public static final String TireLife = "tireLifeKey";
    public static final String OilLife = "oilLifeKey";
    public static final String SparkLife = "sparkLifeKey";

    // Well known SPP UUID
    private static final UUID MY_UUID =
            UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_data);

        syncSuccess = (TextView) findViewById(R.id.view_syncSuccess);

        adapter = BluetoothAdapter.getDefaultAdapter();

        userInfo = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
    }


    public void changeT(String message)
    {
        Toast str = Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT);
        str.show();
    }

    public void btState(View view) {
        // Check for Bluetooth support and then check to make sure it is turned on

        // Emulator doesn't support Bluetooth and will return null
        if(adapter==null) {
            errorExit("Fatal Error", "Bluetooth Not supported. Aborting.");
        } else {
            if (adapter.isEnabled()) {
                changeT("...Bluetooth is enabled...");
            } else {
                //Prompt user to turn on Bluetooth
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }
    }

    private void errorExit(String title, String message){
        Toast msg = Toast.makeText(getBaseContext(),
                title + " - " + message, Toast.LENGTH_LONG);
        msg.show();
        finish();
    }

    public void blueConnect(View view) {
        makeDevice();
        makeSocket();
        makeConnection();
        recStream();
        closeConnection();
    }

    public void blueSender(View view) {
        makeDevice();
        makeSocket();
        makeConnection();
        sendStream(CDTC);
        closeConnection();
    }

    public void updateInfo(int n) {
        SharedPreferences.Editor edit = userInfo.edit();
        int lVal = userInfo.getInt(LastVal, 0);
        int mileage=userInfo.getInt(Mileage,0);
        int tire=userInfo.getInt(Tire,0);
        int oil=userInfo.getInt(Oil,0);
        int spark=userInfo.getInt(Spark,0);
        int tireLife=userInfo.getInt(TireLife,0);
        int oilLife=userInfo.getInt(OilLife,0);
        int sparkLife=userInfo.getInt(SparkLife,0);
        int inc = n - lVal;
        edit.putInt(Mileage, mileage + inc);
        edit.putInt(Tire, tire + inc);
        edit.putInt(Oil, oil + inc);
        edit.putInt(Spark, spark + inc);
        edit.putInt(TireLife, tireLife - inc);
        edit.putInt(OilLife, oilLife - inc);
        edit.putInt(SparkLife, sparkLife - inc);
        edit.putInt(LastVal, n);

        edit.commit();
    }

    public void makeDevice() {
        // Server's Bluetooth address
        String address = "00:12:6F:20:37:9D";

        // Set up a pointer to the remote node using it's address.
        device = adapter.getRemoteDevice(address);
    }

    public void makeSocket() {
        // Two things are needed to make a connection:
        //   A MAC address, which we got above.
        //   A Service ID or UUID.  In this case we are using the
        //     UUID for SPP.
        try {
            socket = device.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e) {
            errorExit("Fatal Error", "In blueConnect() and socket create failed: "
                    + e.getMessage() + ".");
        }
    }

    public void makeConnection() {
        // Establish the connection.  This will block until it connects.
        try {
            socket.connect();
            //changeT("...Connection established and data link opened...");
        } catch (IOException e) {
            changeT("Connection Failed");}
    }

    public void closeConnection() {

        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e2) {
                errorExit("Fatal Error", "In onPause() and failed to close socket."
                        + e2.getMessage() + ".");
            }
        }

    }

    public void recStream(){
        InputStreamReader inStream;
        char[] buf = new char[32];
        int n;
        int b1;
        int b2;
        int val;
        String result = "Result for PID: ";

        try {
            inStream = new InputStreamReader(socket.getInputStream(), "UTF-8");
            n = inStream.read(buf, 0, buf.length);

            if ((n >= 8)&&((buf[n-7]=='1') && (buf[n-6]=='3') && (buf[n-5]=='1'))) {
                b1 = (Character.getNumericValue(buf[n-4])*10)+Character.getNumericValue(buf[n-3]);
                b2 = (Character.getNumericValue(buf[n-2])*10)+Character.getNumericValue(buf[n-1]);
                val = (int) (((b1*256)+b2)*.6214);
                result = result + buf[n-7] + "x" + buf[n-6] + buf[n-5] + " = " + val;
                updateInfo(val);
            } else result = "bytes read: " + n;

            syncSuccess.setText(result);
        } catch (IOException s) {
            errorExit("Fatal Error", "In recStream() and input stream creation failed:"
                    + s.getMessage() + ".");}
    }

    public void sendStream(byte b){
        OutputStream outStream;
        try {
            outStream = socket.getOutputStream();
            outStream.write(b);
        } catch (IOException s) {
            errorExit("Fatal Error", "Failed in sendStream() :" + s.getMessage() + ".");}
    }
}