package com.innovationtechnology.iordanis.stampalapasta.ui.printer;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.innovationtechnology.iordanis.stampalapasta.R;

import java.util.List;

/**
 * Created by iordanis on 9/3/2015.
 */
public class PrinterListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<BluetoothDevice> mData;
    private OnPairButtonClickListener mListener;

    public PrinterListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public interface OnPairButtonClickListener {
        public abstract void onPairButtonClick(int position);
    }

    public void setData(List<BluetoothDevice> data) {
        mData = data;
    }

    public void setListener(OnPairButtonClickListener listener) {
        mListener = listener;
    }

    @Override
    public int getCount() {
        return (mData == null) ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View v, ViewGroup parent) {
        ViewHolder holder;

        if( v==null ){
            v = mInflater.inflate(R.layout.printer_list_item, null);

            holder = new ViewHolder();
            holder.printerName = (TextView) v.findViewById(R.id.printerName);
            holder.printerAddress = (TextView) v.findViewById(R.id.printerAddress);
            holder.connectBtn = (Button) v.findViewById(R.id.connectBtn);

            v.setTag(holder);
        }else{
            holder = (ViewHolder) v.getTag();
        }

        BluetoothDevice printer = mData.get(position);

        holder.printerName.setText(printer.getName());
        holder.printerAddress.setText(printer.getAddress());
        holder.connectBtn.setText(printer.getBondState() == BluetoothDevice.BOND_BONDED?"Disconnect":"Connect");
        holder.connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( mListener != null) {
                    mListener.onPairButtonClick(position);
                }
            }
        });



        return v;
    }

    static class ViewHolder{
        TextView printerName;
        TextView printerAddress;
        TextView connectBtn;
    }

}
