package com.learning.sukhu.news.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.learning.sukhu.news.Dtos.ArticlesDto;
import com.learning.sukhu.news.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by sukhu on 2016-10-02.
 */
public class NewsListAdaptor extends BaseAdapter {

    private Context context;
    private List<ArticlesDto> articlesList;

    public NewsListAdaptor(Context context, List<ArticlesDto> articlesList) {
        this.articlesList = articlesList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return articlesList.size();
    }

    @Override
    public ArticlesDto getItem(int position) {
        return articlesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void notifyDataChanged(List<ArticlesDto> articlesList){
        this.articlesList = articlesList;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView ==null){
            convertView  = LayoutInflater.from(context).inflate(R.layout.news_list, parent, false);
        }

        ArticlesDto article = getItem(position);

        TextView headline = (TextView)convertView.findViewById(R.id.myImageViewText);
        headline.setText(article.getHeading());

        /*TextView description = (TextView)convertView.findViewById(R.id.headingDescription);
        description.setText(article.getDescription());

        TextView time = (TextView)convertView.findViewById(R.id.headlineTime);
        time.setText(article.getTime());*/

        ImageView imageView = (ImageView) convertView.findViewById(R.id.myImageView);
        Picasso.with(context).load(article.getArticleImageUrl()).into(imageView);

        return convertView;
    }
}
