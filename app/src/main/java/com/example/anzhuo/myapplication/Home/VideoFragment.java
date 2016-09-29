package com.example.anzhuo.myapplication.Home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anzhuo.myapplication.Adapter.HomeVideoBaseadpter;
import com.example.anzhuo.myapplication.AdapterInfo.HomeVideoAdapterInfo;
import com.example.anzhuo.myapplication.R;
import com.example.anzhuo.myapplication.ReflashListView.ReflashListView;
import com.facebook.drawee.backends.pipeline.Fresco;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import okhttp3.OkHttpClient;

/**
 * Created by anzhuo on 2016/9/9.
 */
public class VideoFragment extends Fragment implements ReflashListView.IReflashListener {
    List<HomeVideoAdapterInfo> list;
    ReflashListView lv_video;
    HomeVideoBaseadpter homeVideoBaseadpter;
    HomeVideoAdapterInfo homeVideoAdapterInfo;
    OkHttpClient okHttpClient;
    final static int MSG = 11;
    final static int MSN = 12;
    BmobQuery query;
    private int limit = 10;
    private int page = 0;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG:
                    query.order("-creat_time");
                    query.setLimit(limit);
                    query.findObjectsByTable(new QueryListener<JSONArray>() {
                        @Override
                        public void done(JSONArray jsonArray, BmobException e) {
                            if (e == null) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    try {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        homeVideoAdapterInfo = new HomeVideoAdapterInfo();
                                        homeVideoAdapterInfo.setIv_head(R.drawable.olddriver);
                                        homeVideoAdapterInfo.setTv_name("老司机");
                                        homeVideoAdapterInfo.setTv_title(jsonObject.getString("title"));
                                        homeVideoAdapterInfo.setIv_content(jsonObject.getString("content"));
                                        list.add(homeVideoAdapterInfo);
                                    } catch (JSONException e1) {
                                        e1.printStackTrace();
                                    }
                                }
                                lv_video.setAdapter(homeVideoBaseadpter);
                            }
                        }
                    });
                    break;
                case MSN:
                    page++;
                    query.order("-creat_time");
                    query.setLimit(limit);
                    query.setSkip(limit * page);
                    query.findObjectsByTable(new QueryListener<JSONArray>() {
                        @Override
                        public void done(JSONArray jsonArray, BmobException e) {
                            if (e == null) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    try {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        homeVideoAdapterInfo = new HomeVideoAdapterInfo();
                                        homeVideoAdapterInfo.setIv_head(R.drawable.olddriver);
                                        homeVideoAdapterInfo.setTv_name("老司机");
                                        homeVideoAdapterInfo.setTv_title(jsonObject.getString("title"));
                                        homeVideoAdapterInfo.setIv_content(jsonObject.getString("content"));
                                        list.add(homeVideoAdapterInfo);
                                    } catch (JSONException e1) {
                                        e1.printStackTrace();
                                    }
                                }
                                homeVideoBaseadpter.notifyDataSetChanged();
                                lv_video.onLoadComplete();

                            }
                        }
                    });
                    break;
            }
        }

    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_video_layout, null);
        return view;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Fresco.initialize(getActivity());
        query = new BmobQuery("VideoBmobInfo");
        lv_video = (ReflashListView) view.findViewById(R.id.lv_video);
        lv_video.setInterface(this);
        list = new ArrayList<>();
        homeVideoBaseadpter = new HomeVideoBaseadpter(getActivity(), list);
        okHttpClient = new OkHttpClient();
        startThread();
    }

    public void startThread() {
        new Thread() {
            @Override
            public void run() {

                handler.sendEmptyMessage(MSG);

            }
        }.start();
    }

    public void ReflashThread() {

        new Thread() {
            public void run() {

                handler.sendEmptyMessage(MSN);

            }

        }.start();
    }


    public void onReflash() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                lv_video.reflashComplete();
            }
        }, 2000);
    }


    public void onLoad() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ReflashThread();

                Log.i("info", page + "****************");
            }
        }, 2000);
    }
}
