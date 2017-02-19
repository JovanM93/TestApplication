package com.example.jovan.testapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import static com.example.jovan.testapplication.MainActivity.EXTRA_CONTENT;

public class Blog extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);
        setTitle("Blog Display");

        Bundle extras = getIntent().getExtras();
        String blogContent = extras.getString(EXTRA_CONTENT);

        WebView webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setHorizontalScrollBarEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);


        webView.loadData(blogContent, "text/html; charset=utf-8", "UTF-8");

    }
}
