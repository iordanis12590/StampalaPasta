package com.innovationtechnology.iordanis.stampalapasta.ui.pasta;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.innovationtechnology.iordanis.stampalapasta.model.Pasta;
import com.innovationtechnology.iordanis.stampalapasta.ui.DashboardActivity;

/**
 * Created by iordanis on 25/2/2015.
 */
public class PastaShapesListActivity extends ListActivity {

    private PastaShapesAdapter adapter;

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                Intent homeIntent = new Intent(this, DashboardActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        adapter = new PastaShapesAdapter(this);
        setListAdapter(adapter);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        ListView listView = getListView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Pasta pasta = (Pasta) adapter.getItem(position);

                String name = pasta.getName();
                String description = pasta.getDescription();
                //ParseFile image = pasta.getImageFile();

                Intent intent = new Intent(getApplicationContext(), SingleListItem.class);
                intent.putExtra("name", name);
                intent.putExtra("description", description);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }


}
