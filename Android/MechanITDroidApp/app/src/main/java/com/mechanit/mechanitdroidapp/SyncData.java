package com.mechanit.mechanitdroidapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;


public class SyncData extends ActionBarActivity {

    private final static int REQUEST_ENABLE_BT = 1;

    private BluetoothAdapter adapter;
    private BluetoothSocket socket;
    private BluetoothDevice device;

    TextView syncSuccess;

    // Well known SPP UUID
    private static final UUID MY_UUID =
            UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_data);

        syncSuccess = (TextView) findViewById(R.id.view_syncSuccess);

        adapter = BluetoothAdapter.getDefaultAdapter();
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
        sendStream();
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
        InputStream inStream;
        byte[] buffer = new byte[4];
        int bytes;
        int result;

        try {
            inStream = socket.getInputStream();
            bytes = inStream.read(buffer, 0, 4);
            int val1 = buffer[2];
            changeT(Integer.toString(val1));
            int val2 = buffer[3];
            changeT(Integer.toString(val2));
            if (bytes == 2) {
                result = ((val1 * 256) + val2);
                syncSuccess.setText(Integer.toString(result));
            } else {syncSuccess.setText("didn't work.");}
        } catch (IOException s) {
            errorExit("Fatal Error", "In blueConnect() and input stream creation failed:"
                    + s.getMessage() + ".");}
    }

    public void sendStream(){
        OutputStream outStream;
        byte[] cmd = {1};

        try {
            outStream = socket.getOutputStream();
            outStream.write(cmd);
        } catch (IOException s) {
            errorExit("Fatal Error", "Failed to send command:" + s.getMessage() + ".");}
    }
}