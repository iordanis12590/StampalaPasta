package com.innovationtechnology.iordanis.stampalapasta.ui.pasta;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.innovationtechnology.iordanis.stampalapasta.R;
import com.innovationtechnology.iordanis.stampalapasta.model.Pasta;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

/**
 * Created by iordanis on 26/2/2015.
 */
public class PastaShapesAdapter extends ParseQueryAdapter<Pasta>  {

    public PastaShapesAdapter(Context context) {
        super(context, new ParseQueryAdapter.QueryFactory<Pasta>() {
            public ParseQuery<Pasta> create() {
                // Here we can configure a ParseQuery to display

                ParseQuery query = new ParseQuery("Pasta");
                return query;
            }
        });
    }

    @Override
    public View getItemView(Pasta pasta, View v, ViewGroup parent) {

        if(v == null){
            //TODO
            v = View.inflate(getContext(), R.layout.pastashapes_list, null);
        }

        super.getItemView(pasta, v, parent);

        TextView pastaName = (TextView) v.findViewById(R.id.pasta_name);
        pastaName.setText(pasta.getName());

        TextView pastaDescription = (TextView) v.findViewById(R.id.pasta_description);
        pastaDescription.setText(pasta.getDescription());

        ParseImageView parseImageView = (ParseImageView) v.findViewById(R.id.pasta_image);
        ParseFile imageFile = pasta.getParseFile("image");
        if (imageFile != null) {
            parseImageView.setParseFile(imageFile);
            parseImageView.loadInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] bytes, ParseException e) {
                    //
                }
            });
        }

        return v;
    }
}
