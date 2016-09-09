package com.example.anzhuo.myapplication.Home;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anzhuo.myapplication.R;

/**
 * Created by anzhuo on 2016/9/9.
 */
public class HomeActivity  extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.oldcar_home_layout,null);
        return view;
    }
}
