package com.learning.sukhu.news;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.learning.sukhu.news.Dtos.SourcesDto;
import com.learning.sukhu.news.Json.GetSourcesJsonData;
import com.learning.sukhu.news.Transportation.SourcesDataBus;

import java.util.List;

/**
 * Created by sukhu on 2016-09-18.
 */
public class SelectChannelsActivity extends AppCompatActivity implements SourcesDataBus{

    private String Log_Tag = "Sukh_tag_SelectChannelsActivity";
    private GetSourcesJsonData getSourcesJsonData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_channels);
    }

    public void onStart(){
        super.onStart();

        getSourcesJsonData = new GetSourcesJsonData("Hello", this);
        getSourcesJsonData.execute();
    }

    @Override
    public void processedData(List<SourcesDto> sources) {
        Log.v("Sukh_tag/******", sources.get(0).getName());
    }
}
