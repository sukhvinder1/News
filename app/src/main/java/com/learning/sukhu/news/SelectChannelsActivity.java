package com.learning.sukhu.news;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.learning.sukhu.news.DataBase.DatabaseHandler;
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
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                final String sourceName = sourcesList.get(position).getName();
                final String sourceId = sourcesList.get(position).getId();
                if(helper.checkIfPreferenceExists(sourcesList.get(position).getId())){
                    Snackbar.make(view, "Its already added to your list", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Delete ?", new View.OnClickListener(){
                                @Override
                                public void onClick(View v) {
                                    helper.deleteSource(sourceName,sourceId);
                                    showSnackBar(v);

                                }
                            }).show();
                }else{
                    helper.addPreference(sourcesList.get(position).getName(), sourcesList.get(position).getId());
                    Snackbar.make(view, sourceName +" Added", Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener(){
                                @Override
                                public void onClick(View v) {
                                    helper.deleteSource(sourceName,sourceId);
                                    showSnackBar(v);
                                }
                            }).show();
                }
            }
        });
    }

    public void showSnackBar(View v){
        Snackbar snackbar1 = Snackbar.make(v, "Item has been deleted!", Snackbar.LENGTH_SHORT);
        View sbView = snackbar1.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.GREEN);
        snackbar1.show();
    }
}
