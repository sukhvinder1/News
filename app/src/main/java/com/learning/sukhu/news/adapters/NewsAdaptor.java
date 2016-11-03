package com.learning.sukhu.news.adapters;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.learning.sukhu.news.Dtos.ArticlesDto;
import com.learning.sukhu.news.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by sinsukhv on 11/1/2016.
 */
public class NewsAdaptor extends RecyclerView.Adapter<NewsAdaptor.MyViewHolder> {

    private List<ArticlesDto> articlesDtoList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.myImageViewText);
            imageView = (ImageView) view.findViewById(R.id.myImageView);
        }
    }

    public NewsAdaptor(List<ArticlesDto> articlesDtoList){
        this.articlesDtoList = articlesDtoList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list, parent, false);
        context = parent.getContext();
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ArticlesDto articlesDto = articlesDtoList.get(position);
        holder.textView.setText(articlesDto.getHeading());
        Picasso.with(context).load(articlesDto.getArticleImageUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return articlesDtoList.size();
    }

    public void notifyDataChanged(List<ArticlesDto> articlesList){
        this.articlesDtoList = articlesList;
        notifyDataSetChanged();
    }



}
