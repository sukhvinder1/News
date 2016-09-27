package com.learning.sukhu.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.learning.sukhu.news.DataBase.DatabaseHandler;
import com.learning.sukhu.news.DataBase.Source;
import com.learning.sukhu.news.Dtos.ArticlesDto;
import com.learning.sukhu.news.Json.GetArticlesJsonData;
import com.learning.sukhu.news.Transportation.ArticleDataBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements ArticleDataBus{
    private Button selectChannels;
    private String LOG_TAG = "Sukh_tag_MainActivity";
    private Set<String> userPref;
    private ListView listView;
    List<String> titlesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    protected void onStart(){
        super.onStart();
        selectChannels = (Button) findViewById(R.id.selectChannelButton);
        List<String> list = new ArrayList<>();
        listView = (ListView) findViewById(R.id.articlesTitlesListView);
        listView.setVisibility(View.VISIBLE);
        titlesList = new ArrayList<>();

        getUserPref();
        if(false){
            logIt("Hiding Select Button channels Panel");
            hideSelectChannelsPanel();
            list.addAll(userPref);
            for (int i=0; i<list.size(); i++){
                GetArticlesJsonData getArticlesJsonData = new GetArticlesJsonData(list.get(i), this);
                getArticlesJsonData.execute();
            }
        }else{
            listView.setVisibility(View.GONE);
        }
    }

    // on click for select channel button
    public void selectChannels(View v){
        Intent selectChannelsIntent = new Intent(this, SelectChannelsActivity.class);
        startActivity(selectChannelsIntent);
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

    private void getUserPref(){

    }

    @Override
    public void processedData(List<ArticlesDto> articlesDtoList) {
        logIt("processing Data");
        for(ArticlesDto articlesDto : articlesDtoList){
            titlesList.add(articlesDto.getTitle());
        }
        ArrayAdapter<String> myarrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titlesList);
        listView.setAdapter(myarrayAdapter);
    }

    protected void onPause(){
        super.onPause();
        selectChannels = null;
        listView = null;
        titlesList = null;
    }

    protected void onStop(){
        super.onStop();
    }
}
