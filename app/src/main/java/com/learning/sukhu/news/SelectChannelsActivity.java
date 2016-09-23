package com.learning.sukhu.news;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.learning.sukhu.news.Dtos.SourcesDto;
import com.learning.sukhu.news.Json.GetSourcesJsonData;
import com.learning.sukhu.news.Transportation.SourcesDataBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sukhu on 2016-09-18.
 */
public class SelectChannelsActivity extends AppCompatActivity implements SourcesDataBus{

    private String Log_Tag = "Sukh_tag_SelectChannelsActivity";
    private GetSourcesJsonData getSourcesJsonData;
    private ArrayList<SourcesDto> sourcesList;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_channels);
    }

    public void onStart(){
        super.onStart();

        sourcesList = new ArrayList<>();
        listView = (ListView)findViewById(R.id.sourcesListView);

        getSourcesJsonData = new GetSourcesJsonData("Hello", this);
        getSourcesJsonData.execute();
    }

    @Override
    public void processedData(List<SourcesDto> sources) {
        Log.v("Sukh_tag/******", sources.get(0).getName());

        //creating sourceList of Sources
        sourcesList.addAll(sources);

        CustomListAdapter adapter = new CustomListAdapter(getApplicationContext(), R.layout.channels_custom_list, sourcesList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(view.getContext(), sourcesList.get(position).getName(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
