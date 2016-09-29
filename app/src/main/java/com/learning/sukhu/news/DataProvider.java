package com.learning.sukhu.news;

import com.learning.sukhu.news.DataBase.DatabaseHandler;
import com.learning.sukhu.news.DataBase.Source;

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

}
