package com.innovationtechnology.iordanis.stampalapasta.ui.best_of;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.innovationtechnology.iordanis.stampalapasta.R;
import com.innovationtechnology.iordanis.stampalapasta.model.PastaMeal;
import com.parse.ParseQueryAdapter;

/**
 * Created by iordanis on 23/2/2015.
 */
public class PastaMealListActivity extends ListActivity {

    private ParseQueryAdapter<PastaMeal> mainAdapter;
    private FavoritePastaMealAdapter favoritePastaMealAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getListView().setClickable(false);

        mainAdapter = new ParseQueryAdapter<PastaMeal>(this, PastaMeal.class);
        mainAdapter.setTextKey("title");
        mainAdapter.setImageKey("photo");

        favoritePastaMealAdapter = new FavoritePastaMealAdapter(this);
        setListAdapter(mainAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pastameal_list, menu);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            getMenuInflater().inflate(R.menu.menu_pastameal_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_refresh: {
                updatePastaMealList();
                break;
            }

            case R.id.action_favorites: {
                showFavorites();
                break;
            }

            case R.id.action_new: {
                newPastaMeal();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

   private void updatePastaMealList(){
       mainAdapter.loadObjects();
       setListAdapter(mainAdapter);
   }

   private void showFavorites(){
       //
       favoritePastaMealAdapter.loadObjects();
       setListAdapter(favoritePastaMealAdapter);
    }

   private void newPastaMeal(){
       startActivityForResult(new Intent(this, NewPastaMealActivity.class), 0);
   }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            // If a new post has been added, update
            // the list of posts
            updatePastaMealList();
        }
    }

}
