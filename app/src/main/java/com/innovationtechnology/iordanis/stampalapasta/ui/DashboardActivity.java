package com.innovationtechnology.iordanis.stampalapasta.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.innovationtechnology.iordanis.stampalapasta.R;
import com.innovationtechnology.iordanis.stampalapasta.ui.best_of.PastaMealListActivity;
import com.innovationtechnology.iordanis.stampalapasta.ui.pasta.PastaShapesListActivity;
import com.innovationtechnology.iordanis.stampalapasta.ui.printer.BluetoothPastaPrinter;

/**
 * Created by iordanis on 18/2/2015.
 */
public class DashboardActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_layout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pastameal_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    public void onClickPasta(View view) { startActivity(new Intent(this, PastaShapesListActivity.class)); }

    public void onClickPrinter(View view) { startActivity(new Intent(this, BluetoothPastaPrinter.class)); }

    public void onClickBestOf(View view) { startActivity(new Intent(this, PastaMealListActivity.class)); }
}
