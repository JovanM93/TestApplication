package com.example.jovan.testapplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.jovan.testapplication.Login.EXTRA_TOKEN;

public class MainActivity extends AppCompatActivity {
    String tokenParameter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Blog list");

        Bundle extras = getIntent().getExtras();
        tokenParameter = extras.getString(EXTRA_TOKEN);
        Log.i("Token",tokenParameter);


        if (isNetworkAvailable()){


        }else Toast.makeText(getApplicationContext(), "No internet connection! Please turn on your internet.", Toast.LENGTH_LONG).show();

    }

    private void settingBlogsList(){
        JsonArrayRequest blogsListRequest = new JsonArrayRequest(Request.Method.GET, "http://blogsdemo.creitiveapps.com:16427/blogs?token="+tokenParameter, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i("Response", response.toString());

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
            }

        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("X-Client-Platform", "Android");
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json");
                headers.put("X-Authorize","eaf57e2cb62755db708144c93b1f319dcda89871");
                return headers;
            }
        };

        MySingleton.getInstance(this).addToRequestQueue(blogsListRequest);

    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
