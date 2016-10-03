package com.learning.sukhu.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.webkit.WebView;

/**
 * Created by sukhu on 2016-10-02.
 */
public class NewsViewActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_view);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    protected void onStart(){
        super.onStart();
        webView = (WebView)findViewById(R.id.webView);
        String url = getIntent().getExtras().getString("url");
        if(url==null){
            url = "https://www.google.ca/";
        }
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.loadUrl(url);
    }

    protected void onPause(){
        super.onPause();
        webView = null;
    }
}
