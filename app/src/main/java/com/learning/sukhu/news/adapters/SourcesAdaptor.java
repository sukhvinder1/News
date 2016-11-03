package com.learning.sukhu.news.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.learning.sukhu.news.Dtos.SourcesDto;
import com.learning.sukhu.news.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by sukhu on 2016-11-02.
 */

public class SourcesAdaptor extends RecyclerView.Adapter<SourcesAdaptor.MyViewHolder> {

    private final List<SourcesDto> sources;
    private List<String> userPref;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView sourceTitle;
        public ImageView sourceImage;
        public CheckBox checkBox;

        public MyViewHolder(View view) {
            super(view);
            sourceTitle = (TextView) view.findViewById(R.id.myImageViewText);
            sourceImage = (ImageView) view.findViewById(R.id.myImageView);
            checkBox = (CheckBox) view.findViewById(R.id.checkBox);
        }
    }

    public SourcesAdaptor(List<SourcesDto> sources, List<String> userPref) {
        this.sources = sources;
        this.userPref = userPref;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.channels_custom_list, parent, false);
        context = parent.getContext();
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        SourcesDto source = sources.get(position);
        Picasso.with(context).load(source.getUrlSize()).into(holder.sourceImage);
        holder.sourceTitle.setText(source.getName());
        if(userPref.contains(source.getId())){
            holder.checkBox.setChecked(true);
        }else{
            holder.checkBox.setChecked(false);
        }

    }

    @Override
    public int getItemCount() {
        return sources.size();
    }

    public void setCheckBox(int position, List<String> userPref){
        this.userPref = userPref;
        notifyDataSetChanged();
    }
}
