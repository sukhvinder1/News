package com.learning.sukhu.news;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

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
    }

    protected void onStart(){
        super.onStart();
        webView = (WebView)findViewById(R.id.webView);
        String url = getIntent().getExtras().getString("url");
        if(url==null){
            url = "https://www.google.ca/";
        }

        WebSettings webSettings = webView.getSettings();
        webSettings.setSavePassword(false);
        webSettings.setSaveFormData(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(false);
        webView.setWebViewClient(new myWebClient(this) );
        webView.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        Log.v("Sukh", "Back Button");
        if(webView.canGoBack()){
            webView.goBack();
            return;
        }else {
            Intent main = new Intent(this, MainActivity.class);
            startActivity(main);
        }
    }

    protected void onPause(){
        super.onPause();
        webView = null;
    }

    public class myWebClient extends WebViewClient
    {
        private ProgressDialog dialog;
        private AppCompatActivity activity;
        private Context context;

        public myWebClient(AppCompatActivity activity) {
            this.activity = activity;
            context = activity;
            dialog = new ProgressDialog(context);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            this.dialog.setMessage("Processing...");
            this.dialog.show();
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if(dialog.isShowing()){
                dialog.dismiss();
            }
            super.onPageFinished(view, url);
        }
    }
}
