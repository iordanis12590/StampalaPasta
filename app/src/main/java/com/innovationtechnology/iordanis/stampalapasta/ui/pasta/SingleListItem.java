package com.innovationtechnology.iordanis.stampalapasta.ui.pasta;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.innovationtechnology.iordanis.stampalapasta.R;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;


/**
 * Created by iordanis on 7/3/2015.
 */
public class SingleListItem extends Activity {

    TextView nameView;
    Spinner materialSpinner;
    Spinner amountSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pastashapes_preview);

        getActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        getActionBar().setHomeButtonEnabled(true);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");

        //get/set name
        nameView = (TextView) findViewById(R.id.itemName);
        nameView.setText(name);

        materialSpinner = ((Spinner) findViewById(R.id.materialSpinner));
        ArrayAdapter<CharSequence> materialSpinnerAdapter = ArrayAdapter.createFromResource(getBaseContext(), R.array.material_array, android.R.layout.simple_spinner_dropdown_item);
        materialSpinner.setAdapter(materialSpinnerAdapter);

        amountSpinner = ((Spinner) findViewById(R.id.amountSpinner));
        ArrayAdapter<CharSequence> amountSpinnerAdapter = ArrayAdapter.createFromResource(getBaseContext(), R.array.amount_array, android.R.layout.simple_spinner_dropdown_item);
        amountSpinner.setAdapter(amountSpinnerAdapter);

        /*
        materialSpinner = (Spinner) findViewById(R.id.materialSpinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.material_array, android.R.layout.simple_spinner_dropdown_item);
        materialSpinner.setAdapter(spinnerAdapter);


        //get/set description
        TextView descriptionView = (TextView) findViewById(R.id.itemDescription);
        descriptionView.setText(description);

        //download/set image
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Pasta");
        query.whereEqualTo("name", name);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                parseObject.get("image");
                ParseImageView itemShape= (ParseImageView) findViewById(R.id.itemImage);
                itemShape.setParseFile(parseObject.getParseFile("image"));
            }
        });
        */

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
                Toast.makeText(getApplicationContext(), "Please register a valid 3D printer", Toast.LENGTH_LONG).show();
            }
        });
    }
}
