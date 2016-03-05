package com.hatly.hatly;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.firebase.client.Firebase;

/**
 * Created by Omar on 26/02/2016.
 */
public class Hatly extends Application {

    private static Context context;

    //Firebase:
    public static final String FIREBASE_URL = "https://hatly.firebaseio.com";
    public static Firebase rootRef,userRef;

    public static Firebase requestsNode;
    public static Firebase respondsNode;

    // shared pref
    public static final String SHARED_PREF_FILE = "com.hatly.hatly.SharedPreferences";
    // create or get shared preferences unique file:
    public static SharedPreferences sharedPref;

    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
        sharedPref = getContext().getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE);
        Firebase.setAndroidContext(getContext());
        rootRef = new Firebase(FIREBASE_URL);
    }

    public static Context getContext(){
        return context;
    }
}
