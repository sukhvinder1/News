package com.learning.sukhu.news;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.learning.sukhu.news.Dtos.SourcesDto;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by quocnguyen on 03/08/2016.
 */
public class CustomListAdapter extends ArrayAdapter<SourcesDto> {

    List<SourcesDto> sources;
    Context context;
    int resource;
    List<String> userPref;


    public CustomListAdapter(Context context, int resource, List<SourcesDto> sources, List<String> userPref) {
        super(context, resource, sources);
        this.sources = sources;
        this.context = context;
        this.resource = resource;
        this.userPref = userPref;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) getContext()
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.channels_custom_list, null, true);

        SourcesDto sources = getItem(position);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
        Picasso.with(context).load(sources.getUrlSize()).into(imageView);

        TextView txtName = (TextView) convertView.findViewById(R.id.sourcesList);
        txtName.setText(sources.getName());

        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);

        if(userPref.contains(sources.getId())){
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
