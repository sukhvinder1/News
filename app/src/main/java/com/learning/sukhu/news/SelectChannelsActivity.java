package com.learning.sukhu.news;

import android.content.Context;
import android.content.SharedPreferences;
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
    private Set<String> userPref;
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
        getUserPref();
        if(userPref ==null || userPref.isEmpty()){
            Log.v(Log_Tag, "Initializing user pref");
            userPref = new HashSet<>();
        }
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

                if(!userPref.contains(sourcesList.get(position).getId())){
                    Log.v(Log_Tag, "Inside if");
                    Toast.makeText(view.getContext(), sourcesList.get(position).getName(), Toast.LENGTH_SHORT).show();
                    userPref.add(sourcesList.get(position).getId());
                    updateUserPreferences();
                }else {
                    Toast.makeText(view.getContext(), "Pref already added to your list", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateUserPreferences(){
        Log.v(Log_Tag, "Updating user preferences");
        SharedPreferences sharedPref = getSharedPreferences(AppConstants.USER_PREF_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putStringSet(AppConstants.USER_PREF, userPref);
        editor.commit();
    }

    private void getUserPref(){
        SharedPreferences sharedPref = getSharedPreferences(AppConstants.USER_PREF_FILE, Context.MODE_PRIVATE);
        userPref = sharedPref.getStringSet(AppConstants.USER_PREF, null);
    }

    protected void onPause(){
         super.onPause();
    }


}
