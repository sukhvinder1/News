package com.learning.sukhu.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;

import com.learning.sukhu.news.DataBase.DatabaseHandler;
import com.learning.sukhu.news.Dtos.ArticlesDto;
import com.learning.sukhu.news.Json.GetArticlesJsonData;
import com.learning.sukhu.news.Transportation.ArticleDataBus;
import com.learning.sukhu.news.adapters.NewsListAdaptor;
import com.learning.sukhu.news.provider.DataProvider;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ArticleDataBus, NavigationView.OnNavigationItemSelectedListener{
    private Button selectChannels;
    private String LOG_TAG = "Sukh_tag_MainActivity";
    private ListView listView;
    List<String> titlesList;
    protected DatabaseHandler databaseHandler;
    private DataProvider provider;
    private View selectChannelsPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.settings) {
            Log.v("sukh", "setting button ");
        } else if (id == R.id.updateSources) {
            Intent selectChannelsIntent = new Intent(this, SelectChannelsActivity.class);
            startActivity(selectChannelsIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void onStart(){
        super.onStart();
        logIt("ON START");
        selectChannels = (Button) findViewById(R.id.selectChannelButton);
        List<String> list = new ArrayList<>();
        listView = (ListView) findViewById(R.id.articlesTitlesListView);
        listView.setVisibility(View.VISIBLE);
        titlesList = new ArrayList<>();
        databaseHandler = new DatabaseHandler(this);
        provider = new DataProvider(databaseHandler);

        if(!provider.isFirstTime()){
            logIt("Hiding Select Button channels Panel");
            hideSelectChannelsPanel();
            list.addAll(provider.getUserPrefrence());
            for (int i=0; i<list.size(); i++){
                GetArticlesJsonData getArticlesJsonData = new GetArticlesJsonData(list.get(i), this);
                getArticlesJsonData.execute();
            }
        }else{
            logIt("in Else");
            showSelectChannelsPanel();
            listView.setVisibility(View.GONE);
        }
    }

    // on click for select channel button
    public void selectChannels(View v){
        Intent selectChannelsIntent = new Intent(this, SelectChannelsActivity.class);
        startActivity(selectChannelsIntent);
    }

    /**
     * Generic method to log inside class
     * @param logText
     */
    private void logIt(String logText){
        Log.v(LOG_TAG, logText);
    }

    /**
     * Hiding slect channel panel
     */
    private void hideSelectChannelsPanel(){
        selectChannelsPanel = findViewById(R.id.selectButtonFragment);

        if(selectChannelsPanel.getVisibility() == View.VISIBLE){
            selectChannelsPanel.setVisibility(View.GONE);
        }
    }

    private void showSelectChannelsPanel(){
        selectChannelsPanel = findViewById(R.id.selectButtonFragment);
        if(selectChannelsPanel.getVisibility() == View.GONE){
            selectChannelsPanel.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void processedData(List<ArticlesDto> articlesDtoList) {
        logIt("processing Data");
        for(ArticlesDto articlesDto : articlesDtoList){
            titlesList.add(articlesDto.getHeading());
        }
        NewsListAdaptor adaptor = new NewsListAdaptor(this, articlesDtoList);
        //ArrayAdapter<String> myarrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titlesList);
        listView.setAdapter(adaptor);
    }

    protected void onPause(){
        super.onPause();
        selectChannels = null;
        listView = null;
        titlesList = null;
    }

    protected void onStop(){
        super.onStop();
    }
}
