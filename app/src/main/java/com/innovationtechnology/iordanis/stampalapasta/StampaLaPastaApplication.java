package com.innovationtechnology.iordanis.stampalapasta;

import android.app.Application;

import com.innovationtechnology.iordanis.stampalapasta.model.Pasta;
import com.innovationtechnology.iordanis.stampalapasta.model.PastaMeal;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by iordanis on 18/2/2015.
 */
public class StampaLaPastaApplication extends Application {
    @Override
    public void onCreate(){
        super.onCreate();

        /*
		 * subclass ParseObject for convenience to
		 * create and modify PastaMeal objects
		 */
        ParseObject.registerSubclass(Pasta.class);
        ParseObject.registerSubclass(PastaMeal.class);

        Parse.initialize(this, "mVs7aEJ2nL4e8kYMbcXenbBOuEGNiODB6HqkBDGv", "oq007rpkSWz1bwuJQ83pvfreptgApZmz6JGMmZNl");

        /*
		 * This app lets an anonymous user create and save photos of meals
		 * they've eaten. An anonymous user is a user that can be created
		 * without a username and password but still has all of the same
		 * capabilities as any other ParseUser.
		 *
		 * After logging out, an anonymous user is abandoned, and its data is no
		 * longer accessible. In your own app, you can convert anonymous users
		 * to regular users so that data persists.
		 *
		 * Learn more about the ParseUser class:
		 * https://www.parse.com/docs/android_guide#users
		 */
        ParseUser.enableAutomaticUser();

        		/*
		 * For more information on app security and Parse ACL:
		 * https://www.parse.com/docs/android_guide#security-recommendations
		 */
        ParseACL defaultACL = new ParseACL();

		/*
		 * If you would like all objects to be private by default, remove this
		 * line
		 */
        defaultACL.setPublicReadAccess(true);

        ParseACL.setDefaultACL(defaultACL, true);

    }
}