package com.learning.sukhu.news.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sinsukhv on 9/27/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper{

    public static final String COLUMN_ROWID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SOURCE_ID = "sourceid";

    private static final String DATABASE_NAME = "sourcesDb";
    private static final String SOURCES_TABLE = "sourcesTable";
    private static final int DATABASE_VERSION = 1;

    private int size = 0;
    private Cursor genCursor;
    private String LOG_TAG = "Sukh_tag_DatabaseHandler";

    private Context context;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + SOURCES_TABLE + "("
                + COLUMN_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME + " TEXT,"
                + COLUMN_SOURCE_ID + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + SOURCES_TABLE);

        // Create tables again
        onCreate(db);
    }

    /**
     * Adding Source to database
     * @param source
     */
    public void addSource(Source source){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, source.getName());
        values.put(COLUMN_SOURCE_ID, source.getSourceId());

        db.insert(SOURCES_TABLE, null, values);
        db.close();
        //getAllSources();
    }

    /**
     * Removing Source from database
     * @param source
     */
    public void deleteSource(Source source){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SOURCES_TABLE, COLUMN_SOURCE_ID + " = ?",
                new String[] { String.valueOf(source.getSourceId()) });
        db.close();
        //getAllSources();
    }

    /**
     * Reading all Sources
     */
    private void getAllSources(){
        // Select All Query
        String selectQuery = "SELECT  * FROM " + SOURCES_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        genCursor = db.rawQuery(selectQuery, null);
        size = genCursor.getCount();
        /*if (cursor.moveToFirst()) {
            do {
                Log.v("Sukh", "id = " + cursor.getString(0) + " name = " + cursor.getString(1) + " Sid = " + cursor.getString(2));
            } while (cursor.moveToNext());
        }*/
    }

    public boolean isFirstTime(){
        getAllSources();
        Log.v(LOG_TAG, "Size of cursor : " + size);
        if(size == 0){
            return true;
        }
        return false;
    }

    public List<Source> getSourcesList(){
        getAllSources();
        List<Source> sourcesList = new ArrayList<Source>();
        if (genCursor.moveToFirst()) {
            do {
                sourcesList.add(new Source(genCursor.getString(1), genCursor.getString(2)));
            } while (genCursor.moveToNext());
        }
        return sourcesList;
    }

    /**
     * checking if data exist in database or not
     * @param param
     * @return
     */
    public boolean checkIfExist(String param){
        int count = getCount(param);
        if(count == 1){
            return true;
        }
        return false;
    }

    /**
     * @param param
     * @return number of rows
     */
    private int getCount(String param){
        String selectQuery = "SELECT  * FROM " + SOURCES_TABLE +" WHERE " + COLUMN_SOURCE_ID + " ='" + param + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int count =  cursor.getCount();
        cursor.close();
        return count;
    }
}