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

import com.example.anzhuo.myapplication.Adapter.HomeTextBaseadpter;
import com.example.anzhuo.myapplication.AdapterInfo.HomeTextAdapterInfo;
import com.example.anzhuo.myapplication.DataInfo.TextBmobInfo;
import com.example.anzhuo.myapplication.R;
import com.example.anzhuo.myapplication.ReflashListView.ReflashListView;

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
public class TextFragment extends Fragment implements ReflashListView.IReflashListener {

    List<HomeTextAdapterInfo> list;
    ReflashListView lv_text;
    HomeTextBaseadpter homeTextBaseadpter;
    HomeTextAdapterInfo homeTextAdapterInfo;
    OkHttpClient okHttpClient;
    String str;
    final static int MSG = 11;
    final static int MSN = 12;
    TextBmobInfo textBmobInfo;
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
                                        homeTextAdapterInfo = new HomeTextAdapterInfo();
                                        homeTextAdapterInfo.setIv_head(R.drawable.olddriver);
                                        homeTextAdapterInfo.setTv_name("老司机");
                                        homeTextAdapterInfo.setTv_content(jsonObject.getString("content"));
//                                        homeTextAdapterInfo.setTv_good(jsonObject.getInt("good_count")+"");
//                                        homeTextAdapterInfo.setTv_bad(jsonObject.getInt("bad_count")+"");
//                                        homeTextAdapterInfo.setTv_comment(jsonObject.getInt("comment_count")+"");
                                        list.add(homeTextAdapterInfo);
                                    } catch (JSONException e1) {
                                        e1.printStackTrace();
                                    }

                                }
                                lv_text.setAdapter(homeTextBaseadpter);
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
                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    homeTextAdapterInfo = new HomeTextAdapterInfo();
                                    homeTextAdapterInfo.setIv_head(R.drawable.olddriver);
                                    homeTextAdapterInfo.setTv_name("老司机");
                                    homeTextAdapterInfo.setTv_content(jsonObject.getString("content"));
//                                        homeTextAdapterInfo.setTv_good(jsonObject.getString("good_count"));
//                                        homeTextAdapterInfo.setTv_bad(jsonObject.getString("bad_count"));
//                                        homeTextAdapterInfo.setTv_comment(jsonObject.getString("comment_count"));
                                    list.add(homeTextAdapterInfo);
                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                }
                            }
                            homeTextBaseadpter.notifyDataSetChanged();
                            lv_text.onLoadComplete();
                        }
                    });
                    break;

            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_text_layout, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv_text = (ReflashListView) view.findViewById(R.id.lv_text);
        query = new BmobQuery("TextBmobInfo");
        lv_text.setInterface(this);
        textBmobInfo = new TextBmobInfo();
        list = new ArrayList<>();
        okHttpClient = new OkHttpClient();
        homeTextBaseadpter = new HomeTextBaseadpter(getActivity(), list);
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

    public void ReOnLoad() {

        new Thread() {
            public void run() {
                handler.sendEmptyMessage(MSN);
            }

        }.start();
    }



    @Override
    public void onReflash() {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                lv_text.reflashComplete();

            }
        }, 2000);
    }


    @Override
    public void onLoad() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ReOnLoad();
                Log.i("info", page + "****************" + limit*page + "///////////////////****");
            }
        }, 2000);

    }

}
