package com.example.anzhuo.myapplication.Home;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.example.anzhuo.myapplication.Adapter.HomeAdapter;
import com.example.anzhuo.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anzhuo on 2016/9/9.
 */
public class HomeFragment extends Fragment {
    List<Fragment> list;
    RecommendFragment recommendActivity;
    TextFragment textActivity;
    PictureFragment pictureActivity;
    GifFragment gifActivity;
    VideoFragment videoActivity;
    ViewPager vp_main;
    HomeAdapter homeAdapter;
    RadioGroup rg_head;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.oldcar_home_layout, null);
        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vp_main = (ViewPager) view.findViewById(R.id.vp_main);
        rg_head = (RadioGroup) view.findViewById(R.id.rg_head);
        list = new ArrayList<>();
        recommendActivity = new RecommendFragment();
        textActivity = new TextFragment();
        pictureActivity = new PictureFragment();
        gifActivity = new GifFragment();
        videoActivity = new VideoFragment();
        list.add(recommendActivity);
        list.add(textActivity);
        list.add(pictureActivity);
        list.add(gifActivity);
        list.add(videoActivity);
        homeAdapter = new HomeAdapter(getFragmentManager(), list);
        vp_main.setAdapter(homeAdapter);
        rg_head.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_recommend:
                        vp_main.setCurrentItem(0);
                        break;
                    case R.id.rb_text:
                        vp_main.setCurrentItem(1);
                        break;
                    case R.id.rb_pic:
                        vp_main.setCurrentItem(2);
                        break;
                    case R.id.rb_gif:
                        vp_main.setCurrentItem(3);
                        break;
                    case R.id.rb_video:
                        vp_main.setCurrentItem(4);
                        break;
                }
            }
        });

        vp_main.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                switch (position) {
                    case 0:
                        rg_head.check(R.id.rb_recommend);
                        break;
                    case 1:
                        rg_head.check(R.id.rb_text);
                        break;
                    case 2:
                        rg_head.check(R.id.rb_pic);
                        break;
                    case 3:
                        rg_head.check(R.id.rb_gif);
                        break;
                    case 4:
                        rg_head.check(R.id.rb_video);
                        break;


                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
