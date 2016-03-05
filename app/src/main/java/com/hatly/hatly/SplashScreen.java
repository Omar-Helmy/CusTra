package com.hatly.hatly;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

/**
 * Created by Omar on 24/02/2016.
 */
public class SplashScreen extends Activity {

    private final int SPLASH_DISPLAY_LENGTH = 500; // .5 sec
    private boolean preLoginDataSaved = false;
    private Context context = this;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);



        // create keys for loginName and loginPassword, first check if they created already,:
        SharedPreferences.Editor editor = Hatly.sharedPref.edit(); // request editing shared pref file
        if(Hatly.sharedPref.getString("loginName","null").equals("null")) // create new key
        {
            editor.putString("loginName", "null");
            preLoginDataSaved = false;
        } else preLoginDataSaved=true;

        if(Hatly.sharedPref.getString("loginPassword","null").equals("null")) // create new key
        {
            editor.putString("loginPassword", "null");
            preLoginDataSaved = false;
        } else preLoginDataSaved=true;

        if(Hatly.sharedPref.getString("userID","null").equals("null")) // create new key
        {
            editor.putString("userID", "null");
            preLoginDataSaved = false;
        } else preLoginDataSaved=true;

        editor.apply(); // save

        // check for user pre login:
        if(preLoginDataSaved){
            // get saved data
            String email = Hatly.sharedPref.getString("loginName", "null");
            String password = Hatly.sharedPref.getString("loginPassword", "null");
            // try login first:
            Hatly.rootRef.authWithPassword(email, password, new Firebase.AuthResultHandler() {
                @Override
                public void onAuthenticated(AuthData authData) {
                    //System.out.println("User ID: " + authData.getUid() + ", Provider: " + authData.getProvider());
                    Toast.makeText(context, "Login Succeed!", Toast.LENGTH_SHORT).show();
                    // create user node
                    Hatly.userRef = Hatly.rootRef.child("users").child(authData.getUid());
                    // create request and respond nodes
                    Hatly.requestsNode = Hatly.userRef.child("requests");
                    Hatly.respondsNode = Hatly.userRef.child("responds");
                    startMainActivity();
                }

                @Override
                public void onAuthenticationError(FirebaseError firebaseError) {
                    // there was an error, start LoginActivity
                    Toast.makeText(context, "Login Failed! you must login again", Toast.LENGTH_SHORT).show();
                    startLoginActivity();
                }
            });

        }else { // first time opening the app, must login first via LoginActivity
            Toast.makeText(context, "Hello, Please login for first time!", Toast.LENGTH_SHORT).show();
            startLoginActivity();

        }




    }

    private void startLoginActivity(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // New Handler to start the LoginActivity and close this SplashScreen after some seconds.
                Intent mainIntent = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(mainIntent);
                //To prevent the user from using the back button to go back to the Login activity
                // you have to finish() the activity after starting a new one.
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void startMainActivity(){
        // New Handler to start the MainActivity and close this SplashScreen after some seconds.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                        /* Create an Intent that will start the MainActivity. */
                Intent mainIntent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(mainIntent);
                //To prevent the user from using the back button to go back to the Login activity
                // you have to finish() the activity after starting a new one.
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

}
