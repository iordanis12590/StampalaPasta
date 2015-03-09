package com.innovationtechnology.iordanis.stampalapasta.ui.printer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.innovationtechnology.iordanis.stampalapasta.R;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by iordanis on 8/3/2015.
 */
public class BluetoothPastaPrinter extends Activity{

    private TextView statusText;
    private Button switchBtn;
    private Button viewBtn;
    private Button scanBtn;

    private ProgressDialog mProgressDlg;

    private ArrayList<BluetoothDevice> mDeviceList = new ArrayList<BluetoothDevice>();

    private BluetoothAdapter mBluetoothAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.printer_control_board);

        statusText = (TextView) findViewById(R.id.text_status);
        switchBtn = (Button) findViewById(R.id.btn_switch);
        viewBtn = (Button) findViewById(R.id.btn_view_printers);
        scanBtn = (Button) findViewById(R.id.btn_scan_printers);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        mProgressDlg = new ProgressDialog(this);

        mProgressDlg.setMessage("Scanning...");
        mProgressDlg.setCancelable(false);
        mProgressDlg.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                mBluetoothAdapter.cancelDiscovery();
            }
        });

        if( mBluetoothAdapter == null ) {
            showUnsupported();
        }else{

            viewBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Set<BluetoothDevice> registeredPrinters = mBluetoothAdapter.getBondedDevices();

                    // If there are paired devices
                    if (registeredPrinters.size() == 0 || registeredPrinters == null) {
                        showToast("No registered printers found");
                    }else{
                        // Loop through paired devices
                        // Add the printer instances to an array list to show in a ListView
                        ArrayList<BluetoothDevice> list =  new ArrayList<BluetoothDevice>();
                        list.addAll(registeredPrinters);

                        Intent intent = new Intent(BluetoothPastaPrinter.this, PrinterListActivity.class);
                        intent.putParcelableArrayListExtra("printers.list", list);
                        startActivity(intent);
                    }
                }
            });

            scanBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBluetoothAdapter.startDiscovery();
                }
            });

            switchBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mBluetoothAdapter.isEnabled()) {
                        mBluetoothAdapter.disable();
                        showDisabled();
                    } else {
                        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(intent, 1000);
                    }
                }
            });

            if (mBluetoothAdapter.isEnabled()) {
                showEnabled();
            } else {
                showDisabled();
            }

        }

        IntentFilter filter = new IntentFilter();

        filter.addAction(BluetoothDevice.ACTION_FOUND);

        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);


        registerReceiver(mReceiver, filter);
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if( BluetoothDevice.ACTION_FOUND.equals(action)){
                BluetoothDevice printerInstance = (BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                mDeviceList.add(printerInstance);
                showToast("Found " + printerInstance.getName());

            }else  if( BluetoothAdapter.ACTION_STATE_CHANGED.equals(intent.getAction()) ) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
                if (state == BluetoothAdapter.STATE_ON) {
                    showToast("Enabled");
                    showEnabled();
                }

            }else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                mDeviceList = new ArrayList<BluetoothDevice>();
                mProgressDlg.show();

            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {

                mProgressDlg.dismiss();
                Intent newIntent = new Intent(BluetoothPastaPrinter.this, PrinterListActivity.class);
                newIntent.putParcelableArrayListExtra("printers.list", mDeviceList);
                startActivity(newIntent);

            }
        }
    };

    /*
    * change buttons' text and show toast dialogue
    * */
    private void showDisabled() {
        statusText.setText("Bluetooth is off");
        statusText.setTextColor(Color.BLUE);

        switchBtn.setText("Enable");

        switchBtn.setEnabled(true);
        viewBtn.setEnabled(false);
        scanBtn.setEnabled(false);
    }

    private void showEnabled() {
        statusText.setText("Bluetooth is on");
        statusText.setTextColor(Color.BLUE);

        switchBtn.setText("Disable");

        switchBtn.setEnabled(true);
        viewBtn.setEnabled(true);
        scanBtn.setEnabled(true);
    }

    private void showUnsupported() {
        statusText.setText("Your mobile does not support Bluetooth!");

        switchBtn.setText("Enable");

        switchBtn.setEnabled(false);
        viewBtn.setEnabled(false);
        scanBtn.setEnabled(false);
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    /**
     * Important to unregister!!! Don't forget Receiver is a constant
     * */
    @Override
    public void onPause() {
        if (mBluetoothAdapter != null) {
            if (mBluetoothAdapter.isDiscovering()) {
                mBluetoothAdapter.cancelDiscovery();
            }
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

}


