package com.example.anzhuo.myapplication.Home;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anzhuo.myapplication.R;

/**
 * Created by anzhuo on 2016/9/9.
 */
public class GifActivity extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.home_gif_layout,null);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
