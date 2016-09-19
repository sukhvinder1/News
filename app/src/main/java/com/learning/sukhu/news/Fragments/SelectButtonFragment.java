package com.learning.sukhu.news.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.learning.sukhu.news.R;

/**
 * Created by sukhu on 2016-09-18.
 */
public class SelectButtonFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.select_channel_button_fragment, container, false);
        return view;
    }
}
