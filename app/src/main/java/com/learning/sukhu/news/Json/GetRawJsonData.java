package com.learning.sukhu.news.Json;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by sukhu on 2016-09-22.
 *
 * Generic class for get raw json data
 * just pass url in the constructor and trigger execute method
 */
public class GetRawJsonData {

    private String LOG_TAG = "Sukh_tag_GetRawJsonData";

    private String rawUrl;
    private String data;

    public GetRawJsonData(String rawUrl) {
        this.rawUrl = rawUrl;
    }

    public String getRawUrl() {
        return rawUrl;
    }

    public void setRawUrl(String rawUrl) {
        this.rawUrl = rawUrl;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    private void logIt(String logText){
        Log.v(LOG_TAG, logText);
    }

    public void execute(AppCompatActivity activity){
        logIt("Inside execute of GetRawData");
        DownloadRawData downloadRawData = new DownloadRawData(activity);
        downloadRawData.execute(rawUrl);
    }


    public class DownloadRawData extends AsyncTask<String, Void, String>{
        private ProgressDialog dialog;
        private AppCompatActivity activity;
        private Context context;

        public DownloadRawData(AppCompatActivity activity) {
            this.activity = activity;
            context = activity;
            dialog = new ProgressDialog(context);
        }

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Progressing...");
            this.dialog.show();
        }

        protected void onPostExecute(String webData) {
            Log.v(LOG_TAG, "Inside On Post Execute of DownloadRawData");
            data = webData;
            if(dialog.isShowing()){
                dialog.dismiss();
            }
            Log.v(LOG_TAG, "Data returned was : " + data);
        }

        @Override
        protected String doInBackground(String... params) {

            logIt("Inside doInBackground of GetRawData");
            HttpURLConnection urlConn = null;
            BufferedReader reader = null;

            if(params == null){
                Log.d(LOG_TAG, "param is null");
                return null;
            }

            try{
                URL url = new URL(params[0]);

                urlConn = (HttpURLConnection) url.openConnection();
                urlConn.setRequestMethod("GET");
                urlConn.connect();

                InputStream inputStream = urlConn.getInputStream();
                if(inputStream == null){
                    Log.d(LOG_TAG, "inputStream is null");
                    return null;
                }
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;

                while((line = reader.readLine()) != null){
                    buffer.append(line + "\n");
                }

                return buffer.toString();

            }catch (IOException e){
                Log.d(LOG_TAG, "Error ", e);
                return null;
            }finally {
                if(urlConn != null){
                    urlConn.disconnect();
                }
                if(reader != null){
                    try {
                        reader.close();
                    } catch (final IOException e){
                        Log.e(LOG_TAG, "Error closing Stream ", e);
                    }
                }
            }
        }
    }
}
