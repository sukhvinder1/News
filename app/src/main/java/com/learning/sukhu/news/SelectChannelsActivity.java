package com.learning.sukhu.news;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.learning.sukhu.news.DataBase.DatabaseHandler;
import com.learning.sukhu.news.DataBase.Source;
import com.learning.sukhu.news.Dtos.SourcesDto;
import com.learning.sukhu.news.Json.GetSourcesJsonData;
import com.learning.sukhu.news.Transportation.SourcesDataBus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sukhu on 2016-09-18.
 */
public class SelectChannelsActivity extends AppCompatActivity implements SourcesDataBus{

    private String Log_Tag = "Sukh_tag_SelectChannelsActivity";

    private GetSourcesJsonData getSourcesJsonData;
    private List<SourcesDto> sourcesList;
    private ListView listView;
    protected DatabaseHandler databaseHandler;
    private SelectChannelsHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_channels);
    }

    public void onStart(){
        super.onStart();
        databaseHandler = new DatabaseHandler(this);
        sourcesList = new ArrayList<>();
        listView = (ListView)findViewById(R.id.sourcesListView);
        helper = new SelectChannelsHelper(databaseHandler);

        getSourcesJsonData = new GetSourcesJsonData(this);
        getSourcesJsonData.execute();
    }

    @Override
    public void processedData(final List<SourcesDto> sources) {
        //creating sourceList of Sources
        sourcesList.addAll(sources);

        CustomListAdapter adapter = new CustomListAdapter(getApplicationContext(), R.layout.channels_custom_list, sourcesList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(helper.checkIfPreferenceExists(sourcesList.get(position).getId())){
                    Toast.makeText(view.getContext(), "Preference already added to your list", Toast.LENGTH_SHORT).show();
                }else{
                    helper.addPreference(sourcesList.get(position).getName(), sourcesList.get(position).getId());
                    Toast.makeText(view.getContext(), sourcesList.get(position).getName()+" Added", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
