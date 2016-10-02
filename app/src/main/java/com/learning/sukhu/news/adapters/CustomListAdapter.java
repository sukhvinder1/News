package com.learning.sukhu.news.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.learning.sukhu.news.Dtos.SourcesDto;
import com.learning.sukhu.news.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by quocnguyen on 03/08/2016.
 */
public class CustomListAdapter extends BaseAdapter {

    private final List<SourcesDto> sources;
    private final Context context;
    private List<String> userPref;

    @Override
    public int getCount() {
        return sources.size();
    }

    @Override
    public SourcesDto getItem(int position) {
        return sources.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public CustomListAdapter(Context context, List<SourcesDto> sources, List<String> userPref) {
        this.sources = sources;
        this.context = context;
        this.userPref = userPref;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.channels_custom_list, parent, false);
        }

        SourcesDto source = getItem(position);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
        Picasso.with(context).load(source.getUrlSize()).into(imageView);

        TextView txtName = (TextView) convertView.findViewById(R.id.sourcesList);
        txtName.setText(source.getName());

        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);

        if(userPref.contains(source.getId())){
            checkBox.setChecked(true);
        }else{
            checkBox.setChecked(false);
        }

        return convertView;
    }

    public void setCheckBox(int position, List<String> userPref){
        this.userPref = userPref;
        notifyDataSetChanged();
    }


}
