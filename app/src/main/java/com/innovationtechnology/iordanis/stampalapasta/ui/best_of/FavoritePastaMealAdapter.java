package com.innovationtechnology.iordanis.stampalapasta.ui.best_of;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.innovationtechnology.iordanis.stampalapasta.R;
import com.innovationtechnology.iordanis.stampalapasta.model.PastaMeal;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.util.Arrays;

/**
 * Created by iordanis on 25/2/2015.
 */
public class FavoritePastaMealAdapter extends ParseQueryAdapter<PastaMeal> {

    public FavoritePastaMealAdapter(Context context) {
        super(context, new ParseQueryAdapter.QueryFactory<PastaMeal>() {
            public ParseQuery<PastaMeal> create() {
                // Here we can configure a ParseQuery to display
                // only top-rated meals.
                ParseQuery query = new ParseQuery("PastaMeal");
                query.whereContainedIn("rating", Arrays.asList("5", "4"));
                query.orderByDescending("rating");
                return query;
            }
        });
    }

    @Override
    public View getItemView(PastaMeal pastaMeal, View v, ViewGroup parent) {

        if (v == null) {
            v = View.inflate(getContext(), R.layout.bestof_list_favorites, null);
        }

        super.getItemView(pastaMeal, v, parent);

        ParseImageView mealImage = (ParseImageView) v.findViewById(R.id.icon);
        ParseFile photoFile = pastaMeal.getParseFile("photo");
        if (photoFile != null) {
            mealImage.setParseFile(photoFile);
            mealImage.loadInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    // nothing to do
                }
            });
        }

        TextView titleTextView = (TextView) v.findViewById(R.id.text1);
        titleTextView.setText(pastaMeal.getTitle());
        TextView ratingTextView = (TextView) v
                .findViewById(R.id.favorite_meal_rating);
        ratingTextView.setText(pastaMeal.getRating());
        return v;
    }
}
