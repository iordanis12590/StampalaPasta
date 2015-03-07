package com.innovationtechnology.iordanis.stampalapasta.ui.best_of;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.innovationtechnology.iordanis.stampalapasta.R;
import com.innovationtechnology.iordanis.stampalapasta.model.PastaMeal;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * Created by iordanis on 23/2/2015.
 */
public class NewPastaMealFragment extends Fragment {

    private TextView mealName;
    private TextView mealDescription;

    private Spinner mealRating;

    private ImageButton photoButton;
    private Button saveButton;
    private Button cancelButton;

    private ParseImageView mealPreview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bestof_fragment_new_pastameal, parent, false);

        mealName = (EditText) v.findViewById(R.id.meal_name);
        mealDescription = (EditText) v.findViewById(R.id.meal_description);

        mealRating = ((Spinner) v.findViewById(R.id.rating_spinner));
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter
                .createFromResource(getActivity(), R.array.ratings_array,
                        android.R.layout.simple_spinner_dropdown_item);
        mealRating.setAdapter(spinnerAdapter);

        photoButton = ((ImageButton) v.findViewById(R.id.photo_button));
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getActivity()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mealName.getWindowToken(), 0);
                // !!!!!!
                startCamera();
            }
        });

        saveButton = ((Button) v.findViewById(R.id.save_button));
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PastaMeal pastaMeal = ((NewPastaMealActivity) getActivity()).getCurrentPastaMeal();

                pastaMeal.setTitle(mealName.getText().toString());
                pastaMeal.setDescription(mealDescription.getText().toString());
                pastaMeal.setAuthor(ParseUser.getCurrentUser());
                pastaMeal.setRating(mealRating.getSelectedItem().toString());
                //photo is added in the CameraFragment

                pastaMeal.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            getActivity().setResult(Activity.RESULT_OK);
                            getActivity().finish();
                        } else {
                            Toast.makeText(
                                    getActivity().getApplicationContext(),
                                    "Error saving: " + e.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        cancelButton = ((Button) v.findViewById(R.id.cancel_button));
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();
            }
        });

        mealPreview = (ParseImageView) v.findViewById(R.id.meal_preview_image);
        mealPreview.setVisibility(View.INVISIBLE);

        return v;
    }

    private void startCamera() {
        Fragment cameraFragment = new CameraFragment();
        FragmentTransaction transaction = getActivity().getFragmentManager()
                .beginTransaction();
        transaction.replace(R.id.fragmentContainer, cameraFragment);
        transaction.addToBackStack("NewPastaMealFragment");
        transaction.commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        ParseFile photoFile = ((NewPastaMealActivity) getActivity())
                .getCurrentPastaMeal().getPhotoFile();
        if (photoFile != null) {
            mealPreview.setParseFile(photoFile);
            mealPreview.loadInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    mealPreview.setVisibility(View.VISIBLE);
                }
            });
        }
    }
}


