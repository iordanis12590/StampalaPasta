package com.innovationtechnology.iordanis.stampalapasta.ui.printer;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.innovationtechnology.iordanis.stampalapasta.R;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by iordanis on 8/3/2015.
 */
public class PrinterListActivity extends Activity {

    private ListView mListView;
    private PrinterListAdapter mAdapter;
    private ArrayList<BluetoothDevice> mDeviceList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.printer_list);

        getActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        getActionBar().setHomeButtonEnabled(true);

        mDeviceList = getIntent().getExtras().getParcelableArrayList("printers.list");
        mListView = (ListView) findViewById(R.id.printers_list);
        mAdapter = new PrinterListAdapter(this);

        mAdapter.setData(mDeviceList);
        mAdapter.setListener(new PrinterListAdapter.OnPairButtonClickListener() {
            @Override
            public void onPairButtonClick(int position) {
                BluetoothDevice printer = mDeviceList.get(position);

                if (printer.getBondState() == BluetoothDevice.BOND_BONDED) {
                    unpairDevice(printer);
                } else {
                    showToast("Connecting...");
                    pairDevice(printer);
                }
            }
        });

        mListView.setAdapter(mAdapter);
        registerReceiver(mPairReceiver,  new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED));
    }

    private final BroadcastReceiver mPairReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if( BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)){
                final int state 		= intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.ERROR);
                final int prevState	= intent.getIntExtra(BluetoothDevice.EXTRA_PREVIOUS_BOND_STATE, BluetoothDevice.ERROR);

                if (state == BluetoothDevice.BOND_BONDED && prevState == BluetoothDevice.BOND_BONDING) {
                    showToast("Connected");
                } else if (state == BluetoothDevice.BOND_NONE && prevState == BluetoothDevice.BOND_BONDED){
                    showToast("Disconnected");
                }

                mAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    public void onDestroy() {
        unregisterReceiver(mPairReceiver);
        super.onDestroy();
    }

    private void pairDevice(BluetoothDevice printer){
        try {
            Method method = printer.getClass().getMethod("createBond", (Class[]) null);
            method.invoke(printer, (Object[]) null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void unpairDevice(BluetoothDevice printer){
        try {
            Method method = printer.getClass().getMethod("removeBond", (Class[]) null);
            method.invoke(printer, (Object[]) null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

}
