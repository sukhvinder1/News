package com.learning.sukhu.news;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button selectChannels;
    private String LOG_TAG = "Sukh_tag_MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    protected void onStart(){
        super.onStart();
        logIt("INSIDE ON START");

        selectChannels = (Button) findViewById(R.id.selectChannelButton);

        // if user preference is saved, hiding select button channel
        // TODO if statement logic still pending
        if(false){
            logIt("Hiding Select Button channels Panel");
            hideSelectChannelsPanel();
        }
    }

    // on click for select channel button
    public void selectChannels(View v){
        Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
    }

    /**
     * Generic method to log inside class
     * @param logText
     */
    private void logIt(String logText){
        Log.v(LOG_TAG, logText);
    }

    /**
     * Hiding slect channel panel
     */
    private void hideSelectChannelsPanel(){
        View selectChannelsPanel = findViewById(R.id.selectButtonFragment);

        if(selectChannelsPanel.getVisibility() == View.VISIBLE){
            selectChannelsPanel.setVisibility(View.GONE);
        }
    }
}
