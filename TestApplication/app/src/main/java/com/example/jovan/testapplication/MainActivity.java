package com.example.jovan.testapplication;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.jovan.testapplication.Login.EXTRA_TOKEN;

public class MainActivity extends AppCompatActivity {
    String tokenParameter;

    Context context;

    ArrayList<Blogs> blogsArrayList;
    ListView lv;

    public final static String EXTRA_CONTENT = "com.example.jovan.testapplication.CONTENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Blog list");

        Bundle extras = getIntent().getExtras();
        tokenParameter = extras.getString(EXTRA_TOKEN);
        Log.i("Token",tokenParameter);

        blogsArrayList = new ArrayList<>();
        context = this;
        lv = (ListView)findViewById(R.id.listView);


        if (isNetworkAvailable()){
            settingBlogsList();
        }else Toast.makeText(getApplicationContext(), "No internet connection! Please turn on your internet.", Toast.LENGTH_LONG).show();

    }

    private void settingBlogsList(){
        JsonArrayRequest logInRequest = new JsonArrayRequest(Request.Method.GET, "http://blogsdemo.creitiveapps.com:16427/blogs?token="+tokenParameter, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i("Response", response.toString());
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        blogsArrayList.add(new Blogs(
                                jsonObject.getInt("id"),
                                jsonObject.getString("title"),
                                jsonObject.getString("image_url"),
                                jsonObject.getString("description")));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    CustomListAdapter adapter = new CustomListAdapter(
                            getApplicationContext(), R.layout.blogs_list, blogsArrayList
                    );
                    lv.setAdapter(adapter);
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Blogs blog = blogsArrayList.get(i);
                            int blogId = blog.getId();
                            if (isNetworkAvailable()){
                                gettingBlogContent(tokenParameter,blogId);

                            }else Toast.makeText(getApplicationContext(), "No internet connection! Please turn on your internet.", Toast.LENGTH_LONG).show();

                        }
                    });

                }

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

        MySingleton.getInstance(this).addToRequestQueue(logInRequest);

    }

    private void gettingBlogContent(String token, int id){
        JsonObjectRequest gettingBlogRequest = new JsonObjectRequest(Request.Method.GET, "http://blogsdemo.creitiveapps.com:16427/blogs/"+id+"?token="+ token, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("Responsee", response.toString());

                Intent intent = new Intent(getApplicationContext(), Blog.class);
                try {
                    intent.putExtra(EXTRA_CONTENT, response.getString("content"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                startActivity(intent);

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

        MySingleton.getInstance(this).addToRequestQueue(gettingBlogRequest);

    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
