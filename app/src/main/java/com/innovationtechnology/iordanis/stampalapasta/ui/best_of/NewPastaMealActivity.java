package com.innovationtechnology.iordanis.stampalapasta.ui.best_of;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.innovationtechnology.iordanis.stampalapasta.R;
import com.innovationtechnology.iordanis.stampalapasta.model.PastaMeal;

/**
 * Created by iordanis on 23/2/2015.
 */
public class NewPastaMealActivity extends Activity{

    private PastaMeal pastaMeal;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        pastaMeal = new PastaMeal();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.bestof_activity_new_pastameal);
        FragmentManager manager = getFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);

        if (fragment == null) {
            fragment = new NewPastaMealFragment();
            manager.beginTransaction().add(R.id.fragmentContainer, fragment)
                    .commit();
        }
    }

    public PastaMeal getCurrentPastaMeal() {
        return pastaMeal;
    }
}
