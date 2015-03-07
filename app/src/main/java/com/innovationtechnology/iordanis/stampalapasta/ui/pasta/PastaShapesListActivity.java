package com.innovationtechnology.iordanis.stampalapasta.ui.pasta;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.innovationtechnology.iordanis.stampalapasta.model.Pasta;

/**
 * Created by iordanis on 25/2/2015.
 */
public class PastaShapesListActivity extends ListActivity {

    private PastaShapesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                Intent intent = new Intent(getApplicationContext(), SingleListItem.class);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }


}
