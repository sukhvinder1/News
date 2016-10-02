package com.learning.sukhu.news.provider;

import android.util.Log;

import com.learning.sukhu.news.DataBase.DatabaseHandler;
import com.learning.sukhu.news.DataBase.Source;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sinsukhv on 9/27/2016.
 */
public class DataProvider {

    private DatabaseHandler databaseHandler;

    public DataProvider(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
    }

    public boolean checkIfPreferenceExists(String param){
        return databaseHandler.checkIfExist(param);
    }

    public void addPreference(String name, String id){
        Source source = new Source(name, id);
        databaseHandler.addSource(source);
    }

    public void deleteSource(String name, String sourceId){
        Source source = new Source(name, sourceId);
        databaseHandler.deleteSource(source);
    }

    public boolean isFirstTime(){
        boolean result = databaseHandler.isFirstTime();
        Log.v("Sukh", "Result : " + result);
        return result;
    }

    public List<String> getUserPrefrence(){
        List<String> sourceList = new ArrayList<>();
        List <Source> sourcesList = databaseHandler.getSourcesList();
        for(Source source : sourcesList){
            sourceList.add(source.getSourceId());
            Log.v("Sukh", "Adding " + source.getName());
        }
        return sourceList;
    }

}
