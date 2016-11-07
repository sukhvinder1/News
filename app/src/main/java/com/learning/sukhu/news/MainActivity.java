package com.learning.sukhu.news;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.learning.sukhu.news.DataBase.DatabaseHandler;
import com.learning.sukhu.news.Dtos.ArticlesDto;
import com.learning.sukhu.news.Json.GetArticlesJsonData;
import com.learning.sukhu.news.Transportation.ArticleDataBus;
import com.learning.sukhu.news.adapters.NewsAdaptor;
import com.learning.sukhu.news.adapters.NewsListAdaptor;
import com.learning.sukhu.news.adapters.RecyclerItemClickListener;
import com.learning.sukhu.news.provider.DataProvider;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ArticleDataBus, NavigationView.OnNavigationItemSelectedListener{
    private Button selectChannels;
    private String LOG_TAG = "Sukh_tag_MainActivity";
    //private ListView listView;
    List<ArticlesDto> articlesList;
    protected DatabaseHandler databaseHandler;
    private DataProvider provider;
    private View selectChannelsPanel;
    //private NewsListAdaptor adaptor;
    private Parcelable state;
    List<String> list;

    private RecyclerView recyclerView;
    private NewsAdaptor adaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setNavigationDrawer();
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
            logIt("Setting Button");
            Intent intent = new Intent(this, NewsViewActivity.class);
            startActivity(intent);
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
        initializingVariable();

        if(!provider.isFirstTime()){
            logIt("Hiding Select Button channels Panel");
            hideSelectChannelsPanel();
            list.addAll(provider.getUserPrefrence());
            networkLoop();
        }else{
            logIt("in Else");
            showSelectChannelsPanel();
            recyclerView.setVisibility(View.GONE);
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

    @Override
    public void processedData(List<ArticlesDto> articlesDtoList) {
        logIt("processing Data");
        for(ArticlesDto articlesDto : articlesDtoList){
            if(articlesDto!=null && articlesList != null){
                articlesList.add(articlesDto);
            }
        }
        adaptor.notifyDataChanged(articlesList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adaptor);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String url = articlesList.get(position).getUrl();
                        Intent intent = new Intent(view.getContext(), NewsViewActivity.class);
                        intent.putExtra("url", url);
                        startActivity(intent);
                    }
                })
        );
    }

    protected void onResume(){
        super.onResume();
        if (state != null) {
            //listView.requestFocus();
            //listView.onRestoreInstanceState(state);
        }
    }

    protected void onPause(){
      //  state = listView.onSaveInstanceState();
        selectChannels = null;
        recyclerView = null;
        articlesList = null;
        super.onPause();
    }

    protected void onStop(){
        super.onStop();
    }

    private void networkLoop(){
        if(isNetworkAvailable()){
            getArticles(list);
        }else{
            Log.v(LOG_TAG, "No network available");
            Snackbar.make(findViewById(android.R.id.content), "No Network Available", Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry ?", new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        networkLoop();
                    }
                }).show();
        }
    }

    private void getArticles(List<String> list){
        for (int i=0; i<list.size(); i++) {
            GetArticlesJsonData getArticlesJsonData = new GetArticlesJsonData(list.get(i), this);
            getArticlesJsonData.execute(this);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void setNavigationDrawer(){
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

    private void initializingVariable(){
        selectChannels = (Button) findViewById(R.id.selectChannelButton);
        list = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setVisibility(View.VISIBLE);
        articlesList = new ArrayList<ArticlesDto>();
        databaseHandler = new DatabaseHandler(this);
        provider = new DataProvider(databaseHandler);
        adaptor = new NewsAdaptor(articlesList);
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
}
