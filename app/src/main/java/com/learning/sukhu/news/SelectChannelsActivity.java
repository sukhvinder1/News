package com.learning.sukhu.news;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.learning.sukhu.news.DataBase.DatabaseHandler;
import com.learning.sukhu.news.Dtos.SourcesDto;
import com.learning.sukhu.news.Json.GetSourcesJsonData;
import com.learning.sukhu.news.Transportation.SourcesDataBus;
import com.learning.sukhu.news.adapters.SourceListAdapter;
import com.learning.sukhu.news.provider.DataProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sukhu on 2016-09-18.
 */
public class SelectChannelsActivity extends AppCompatActivity implements SourcesDataBus{

    private String Log_Tag = "Sukh_tag_SelectChannelsActivity";

    private GetSourcesJsonData getSourcesJsonData;
    private List<SourcesDto> sourcesList;
    List<String> userPref;
    private ListView listView;
    protected DatabaseHandler databaseHandler;
    private DataProvider provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_channels);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void onStart(){
        super.onStart();
        databaseHandler = new DatabaseHandler(this);
        sourcesList = new ArrayList<>();
        userPref = new ArrayList<String>();
        listView = (ListView)findViewById(R.id.sourcesListView);
        provider = new DataProvider(databaseHandler);
        userPref = provider.getUserPrefrence();
        networkLoop();
    }

    @Override
    public void processedData(final List<SourcesDto> sources) {
        //creating sourceList of Sources
        sourcesList.addAll(sources);

        final SourceListAdapter adapter = new SourceListAdapter(getApplicationContext(), sourcesList, userPref);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                final String sourceName = sourcesList.get(position).getName();
                final String sourceId = sourcesList.get(position).getId();
                if(provider.checkIfPreferenceExists(sourcesList.get(position).getId())){
                    provider.deleteSource(sourceName, sourceId);
                    Snackbar.make(view, sourceName + " removed from your list", Snackbar.LENGTH_SHORT).show();
                }else{
                    provider.addPreference(sourcesList.get(position).getName(), sourcesList.get(position).getId());
                    Snackbar.make(view, sourceName +" Added", Snackbar.LENGTH_SHORT).show();
                }
                adapter.setCheckBox(position, provider.getUserPrefrence());
            }
        });
    }

    private void networkLoop(){
        if(isNetworkAvailable()){
            Log.v(Log_Tag, "Network available");
            getSources();
        }else{
            Log.v(Log_Tag, "No network available");
            Snackbar.make(findViewById(android.R.id.content), "No Network Available", Snackbar.LENGTH_LONG)
                .setAction("Retry ?", new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        networkLoop();
                    }
                }).show();
        }
    }

    private void getSources(){
        getSourcesJsonData = new GetSourcesJsonData(this);
        getSourcesJsonData.execute();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
