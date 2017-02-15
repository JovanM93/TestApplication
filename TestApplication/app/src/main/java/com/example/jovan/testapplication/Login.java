package com.example.jovan.testapplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    EditText mailText;
    EditText passText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");

        mailText = (EditText) findViewById(R.id.editText);
        passText = (EditText) findViewById(R.id.editText2);
    }
    public void logIn(View view) {
        if(isNetworkAvailable()){
            String mail = mailText.getText().toString().trim();
            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
            String pass = passText.getText().toString();

            int errors = 0;
             if (!mail.matches(emailPattern)) {
                mailText.setError("Enter a valid email!");
                errors++;
            }if (pass.length() < 6) {
                passText.setError("Password should be at least 6 characters long!");
                errors++;
            }
            if(errors <1){


            }

        }else Toast.makeText(getApplicationContext(), "No internet connection! Please turn on your internet.", Toast.LENGTH_LONG).show();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
