package com.learning.sukhu.news.Json;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.learning.sukhu.news.Dtos.ArticlesDto;
import com.learning.sukhu.news.Transportation.ArticleDataBus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sukhu on 2016-09-24.
 */
public class GetArticlesJsonData extends GetRawJsonData{
    private Uri destinationUri;
    private String LOG_TAG = "Sukh_tag_GetArticlesJsonData";
    private List<ArticlesDto> articlesDto;
    private ArticleDataBus dataBus;

    public GetArticlesJsonData(String sourceId, ArticleDataBus dataBus) {
        super(null);
        Log.v(LOG_TAG, "Creating object");
        articlesDto = new ArrayList<>();
        this.dataBus = dataBus;
        createAndUpdateUri(sourceId);
    }

    private boolean createAndUpdateUri(String sourceId){
        final String API_BASE_URL="https://newsapi.org/v1/articles";
        final String SOURCE_ID="source";
        final String SORT_BY="sortBy";
        final String API_KEY="apiKey";

        destinationUri = Uri.parse(API_BASE_URL).buildUpon()
                .appendQueryParameter(SOURCE_ID, sourceId)
                .appendQueryParameter(SORT_BY,"")
                .appendQueryParameter(API_KEY, "62d97544228f447f83649dc8c30541b2")
                .build();
        Log.d(LOG_TAG, "Prepared URI " + destinationUri.toString());

        return destinationUri != null;
    }

    public void execute(AppCompatActivity activity){
        super.setRawUrl(destinationUri.toString());
        DownloadJsonData downloadJsonData = new DownloadJsonData(activity);
        downloadJsonData.execute(destinationUri.toString());
    }

    public List<ArticlesDto> processData(){
        final String ARTICLES_Obj="articles";
        final String TITLE_Obj="title";
        final String DESCRIPTION_Ojb = "description";
        final String TIME_Obj = "publishedAt";
        final String ARTICLE_IMAGE_URL_Obj = "urlToImage";
        final String URL_Obj="url";

        try{
            JSONObject jsonObject = new JSONObject(getData());
            JSONArray jsonArray = jsonObject.getJSONArray(ARTICLES_Obj);
            for(int i=0; i<jsonArray.length();i++){
                JSONObject article = jsonArray.getJSONObject(i);
                String title = article.getString(TITLE_Obj);
                String url = article.getString(URL_Obj);
                String description = article.getString(DESCRIPTION_Ojb);
                String article_Image_url = article.getString(ARTICLE_IMAGE_URL_Obj);
                String time = article.getString(TIME_Obj);

                ArticlesDto dto = new ArticlesDto(title, article_Image_url, description, time, url);
                this.articlesDto.add(dto);
            }
        }catch (JSONException e){
            e.printStackTrace();
            Log.e(LOG_TAG, "Error process json data");
        }

        return articlesDto;
    }

    public class DownloadJsonData extends DownloadRawData{

        public DownloadJsonData(AppCompatActivity activity) {
            super(activity);
        }

        @Override
        protected void onPostExecute(String webData) {
            super.onPostExecute(webData);
            dataBus.processedData(processData());
        }

        @Override
        protected String doInBackground(String... params) {
            return super.doInBackground(params);
        }
    }
}
