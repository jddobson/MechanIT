package com.mechanit.mechanitdroidapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.UUID;


public class SyncData extends ActionBarActivity {

    private final static int REQUEST_ENABLE_BT = 1;
    public final static String Data = "dataKey";
    public final static String Mileage = "mileageKey";

    private BluetoothAdapter adapter;
    private BluetoothSocket socket;
    private BluetoothDevice device;

    public SharedPreferences userInfo;

    // Well known SPP UUID
    private static final UUID MY_UUID =
            UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = BluetoothAdapter.getDefaultAdapter();

        btState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sync_data, menu);
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

    public void changeT(String message)
    {
        Toast str = Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT);
        str.show();
    }

    private void errorExit(String title, String message){
        Toast msg = Toast.makeText(getBaseContext(),
                title + " - " + message, Toast.LENGTH_LONG);
        msg.show();
        finish();
    }

    public void btState() {
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
            blueConnect();
        }
    }

    public void blueConnect() {
        makeDevice();
        makeSocket();
        makeConnection();
        recStream();
        closeConnection();
    }

    public void blueSender(int cmd) {
        makeDevice();
        makeSocket();
        makeConnection();
        sendStream(cmd);
        closeConnection();
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
        if (userInfo.contains(Mileage)) {
            int mileage = userInfo.getInt(Mileage,0);}
        InputStreamReader inStream;
        char[] buf = new char[128];
        int n;
        int b1;
        int b2;
        int val;
        String result = "Result for PID: ";

        try {
            inStream = new InputStreamReader(socket.getInputStream(), "UTF-8");
            n = inStream.read(buf);
            if ((buf[n-11]=='1') && (buf[n-9]=='3') && (buf[n-8]=='1')) {
                b1 = (Character.getNumericValue(buf[n-6])*10)+Character.getNumericValue(buf[n-5]);
                b2 = (Character.getNumericValue(buf[n-3])*10)+Character.getNumericValue(buf[n-2]);
                val = ((b1*256)+b2);
                result = result + buf[n-11] + "x" + buf[n-9] + buf[n-8] + " = " + val;
                if (val >= 0) {
                    blueSender(1);}
            } else result = "Failed to retrieve data, please try again.";
            changeT(result);
        } catch (IOException s) {
            errorExit("Fatal Error", "In blueConnect() and input stream creation failed:"
                    + s.getMessage() + ".");}
    }

    public void sendStream(int cmd){
        OutputStream outStream;

        try {
            outStream = socket.getOutputStream();
            outStream.write(cmd);
        } catch (IOException s) {
            errorExit("Fatal Error", "Failed to send command:" + s.getMessage() + ".");}
    }
}