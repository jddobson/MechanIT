package com.mechanit.mechanitdroidapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;


public class SyncData extends ActionBarActivity {

    private final static int REQUEST_ENABLE_BT = 1;

    private BluetoothAdapter adapter;
    private BluetoothSocket socket;
    private OutputStream outStream;

    TextView syncSuccess;

    String cmd;

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

    public void closeConnection() {
        changeT("...In closeConnection()...");

        if (outStream != null) {
            try {
                outStream.flush();
            } catch (IOException e) {
                errorExit("Fatal Error", "In onPause() and failed to flush output stream: " + e.getMessage() + ".");
            }
        }

        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e2) {
                errorExit("Fatal Error", "In onPause() and failed to close socket." + e2.getMessage() + ".");
            }
        }
    }

    public void changeT(String str)
    {
        syncSuccess.setText(str);
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

    private void sendData(String cmd) {
        byte[] msgBuffer = cmd.getBytes();

        changeT("...Sending command: " + cmd + "...");

        try {
            outStream.write(msgBuffer);
        } catch (IOException e) {
            String msg = "In sendData() and an exception occurred during write: " + e.getMessage();
            msg = msg +  ".\n\nCheck that the SPP UUID: " + MY_UUID.toString() + " exists on server.\n\n";

            errorExit("Fatal Error", msg);
        }
    }

    public void blueConnect(View view) {

        // Server's Bluetooth address
        String address = "00:12:6F:20:37:9D";

        changeT("...In blueConnect - Attempting client connect...");

        // Set up a pointer to the remote node using it's address.
        BluetoothDevice device = adapter.getRemoteDevice(address);

        // Two things are needed to make a connection:
        //   A MAC address, which we got above.
        //   A Service ID or UUID.  In this case we are using the
        //     UUID for SPP.
        try {
            socket = device.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e) {
            errorExit("Fatal Error", "In blueConnect() and socket create failed: " + e.getMessage() + ".");
        }

        // Discovery is resource intensive.  Make sure it isn't going on
        // when you attempt to connect and pass your message.
        adapter.cancelDiscovery();

        // Establish the connection.  This will block until it connects.
        changeT("...Connecting to Remote...");
        try {
            socket.connect();
            changeT("...Connection established and data link opened...");
        } catch (IOException e) {
            try {
                outStream = socket.getOutputStream();
            } catch (IOException s) {
                errorExit("Fatal Error", "In blueConnect() and output stream creation failed:" + e.getMessage() + ".");
            }
            if (cmd != null) {
                try {
                    sendData(cmd);
                } finally {
                    closeConnection();
                }
            }
            try {
               socket.close();
            } catch (IOException e2) {
                errorExit("Fatal Error", "In blueConnect() and unable to close socket during connection failure" + e2.getMessage() + ".");
            }
        }
        try {
            socket.close();
        } catch (IOException e2) {
            errorExit("Fatal Error", "In blueConnect() and failed to close socket." + e2.getMessage() + ".");
        }
    }
}
