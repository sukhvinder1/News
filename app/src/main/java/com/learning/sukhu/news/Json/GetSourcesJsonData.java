package com.learning.sukhu.news.Json;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.learning.sukhu.news.Dtos.SourcesDto;
import com.learning.sukhu.news.Transportation.SourcesDataBus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sukhu on 2016-09-22.
 */
public class GetSourcesJsonData extends GetRawJsonData{

    private String LOG_TAG = "Sukh_tag_GetSourcesJsonData";

    private List<SourcesDto> sourcesList;
    private Uri destinationUri;
    private SourcesDataBus sourcesDataBus;

    public GetSourcesJsonData(SourcesDataBus sourcesDataBus) {
        super(null);
        sourcesList = new ArrayList<SourcesDto>();
        this.sourcesDataBus = sourcesDataBus;
        createUri();
    }

    public void createUri(){
        final String BASE_URL = "https://newsapi.org/v1/sources/";
        destinationUri = Uri.parse(BASE_URL).buildUpon().build();
    }

    public void execute(AppCompatActivity activity){
        super.setRawUrl(destinationUri.toString());
        DownloadJsonData downloadJsonData = new DownloadJsonData(activity);
        downloadJsonData.execute(destinationUri.toString());
    }

    public List<SourcesDto> processData(){

        final String SOURCES = "sources";
        final String ID = "id";
        final String NAME = "name";
        final String CATEGORY = "category";
        final String URLS_TO_LOGOS = "urlsToLogos";
        final String URL_SIZE = "small";

        try{
            JSONObject jsonObject = new JSONObject(getData());
            JSONArray jsonArray = jsonObject.getJSONArray(SOURCES);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonSource = jsonArray.getJSONObject(i);
                String id = jsonSource.getString(ID);
                String name = jsonSource.getString(NAME);
                String categoty = jsonSource.getString(CATEGORY);

                JSONObject urls = jsonSource.getJSONObject(URLS_TO_LOGOS);
                String urlSmall = urls.getString(URL_SIZE);

                SourcesDto sourcesObject = new SourcesDto(id, name, categoty, urlSmall);

                this.sourcesList.add(sourcesObject);
            }
        }catch (JSONException jsone){
            jsone.printStackTrace();
            Log.e(LOG_TAG, "Error process json data");
        }

        return sourcesList;
    }

    public class DownloadJsonData extends DownloadRawData{
        public DownloadJsonData(AppCompatActivity activity) {
            super(activity);
        }

        @Override
        protected void onPostExecute(String webData) {
            super.onPostExecute(webData);
            sourcesDataBus.processedData(processData());
        }

        @Override
        protected String doInBackground(String... params) {
            return super.doInBackground(params);
        }
    }
}
