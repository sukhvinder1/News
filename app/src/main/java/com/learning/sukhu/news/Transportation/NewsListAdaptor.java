package com.learning.sukhu.news.Transportation;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.learning.sukhu.news.R;

/**
 * Created by sinsukhv on 9/30/2016.
 */
public class NewsListAdaptor extends BaseAdapter {

    Context context;
    int resource;
    private static LayoutInflater inflater = null;
    private View view;

    public NewsListAdaptor(Context context, int resource) {
        this.context = context;
        this.resource = resource;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        view = convertView;
        view = inflater.inflate(R.layout.news_list, null);


        return null;
    }
}
