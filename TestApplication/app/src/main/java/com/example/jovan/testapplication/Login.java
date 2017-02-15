package com.example.jovan.testapplication;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
        if(isNetworkAvailable()){                                             //checking network connection
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
            if(errors <1){                                                         // checking for input errors
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("email", mail);                                 // adding email and password inputs
                    jsonObject.put("password", pass);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JsonObjectRequest logInRequest = new JsonObjectRequest(Request.Method.POST, "http://blogsdemo.creitiveapps.com:16427/login", jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String resp;
                        try {
                            resp = response.getString("token");
                            Log.i("Response",resp);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse response = error.networkResponse;
                        int responseCode = response.statusCode;
                        switch (responseCode) {
                            case 400:
                                Toast.makeText(getApplicationContext(), "You forgot email or password", Toast.LENGTH_SHORT).show();
                                break;
                            case 401:
                                Toast.makeText(getApplicationContext(), "Wrong email or password", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        error.printStackTrace();
                    }

                }) {

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("X-Client-Platform", "Android");
                        headers.put("Accept", "application/json");
                        headers.put("Content-Type", "application/json");
                        return headers;
                    }
                };
                MySingleton.getInstance(this).addToRequestQueue(logInRequest);

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
