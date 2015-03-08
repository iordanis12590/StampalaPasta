package com.innovationtechnology.iordanis.stampalapasta.ui.pasta;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.innovationtechnology.iordanis.stampalapasta.R;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;


/**
 * Created by iordanis on 7/3/2015.
 */
public class SingleListItem extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pastashapes_preview);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");


        TextView textView = (TextView) findViewById(R.id.check);
        textView.setText(name);
    }

    public void onClickPrintButton(View view){

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Pasta");
        String name = getIntent().getStringExtra("name");

        query.whereEqualTo("name", name);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if (parseObject == null) {
                    Log.d("pasta", "The getFirst request failed.");
                } else {
                    Log.d("pasta", "Retrieved the object.");
                    parseObject.increment("counter");
                    parseObject.saveInBackground();
                    //checking git
                }
            }
        });
    }
}
