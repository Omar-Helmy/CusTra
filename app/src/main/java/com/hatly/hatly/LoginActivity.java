package com.hatly.hatly;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private String email, password;
    private EditText usernameBox, passwordBox;
    private Button loginButton,createNewButton;
    private Context context=this;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameBox = (EditText) findViewById(R.id.email_text);
        passwordBox = (EditText) findViewById(R.id.password_text);
        loginButton = (Button) findViewById(R.id.login_button);
        createNewButton = (Button) findViewById(R.id.creatnew_button);

        loginButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                email = usernameBox.getText().toString();
                password = passwordBox.getText().toString();

                //try login first:
                Hatly.rootRef.authWithPassword(email, password, new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {

                        //System.out.println("User ID: " + authData.getUid() + ", Provider: " + authData.getProvider());
                        // store new username and value in shared pref:
                        SharedPreferences.Editor editor = Hatly.sharedPref.edit(); // request editing shared pref file
                        editor.putString("loginName", email);
                        editor.putString("loginPassword", password);
                        editor.putString("userID", authData.getUid());
                        editor.apply();
                        // create user node
                        Hatly.userRef = Hatly.rootRef.child("users").child(authData.getUid());
                        // create request and respond nodes
                        Hatly.requestsNode = Hatly.userRef.child("requests");
                        Hatly.respondsNode = Hatly.userRef.child("responds");

                        Toast.makeText(context, "Loin Succeed!", Toast.LENGTH_SHORT).show();
                        startMainActivity();
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        // there was an error, start LoginActivity
                        Toast.makeText(context, "Loin Failed!", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        createNewButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                email = usernameBox.getText().toString();
                password = passwordBox.getText().toString();

                Hatly.rootRef.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
                    @Override
                    public void onSuccess(Map<String, Object> result) {
                        //System.out.println("Successfully created user account with uid: " + result.get("uid"));
                        Toast.makeText(context, "Login Succeed!", Toast.LENGTH_SHORT).show();
                        //Creating an account will not log that new account in, so I must click the login button
                        // virtually here!
                        loginButton.performClick();
                    }
                    @Override
                    public void onError(FirebaseError firebaseError) {
                        // there was an error
                        Toast.makeText(context, "Creating new account failed!", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }

    private void startMainActivity(){
        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up loginButton, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
