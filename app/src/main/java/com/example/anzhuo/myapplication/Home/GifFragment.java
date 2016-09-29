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

import com.example.anzhuo.myapplication.Adapter.HomeGifBaseadpter;
import com.example.anzhuo.myapplication.AdapterInfo.HomeGifAdapterInfo;
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
public class GifFragment extends Fragment implements ReflashListView.IReflashListener {
    List<HomeGifAdapterInfo> list;
    ReflashListView lv_gif;
    HomeGifBaseadpter homeGifBaseadpter;
    HomeGifAdapterInfo homeGifAdapterInfo;
    OkHttpClient okHttpClient;
    String str;
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
                                            homeGifAdapterInfo = new HomeGifAdapterInfo();
                                            homeGifAdapterInfo.setIv_head(R.drawable.olddriver);
                                            homeGifAdapterInfo.setTv_name("老司机");
                                            homeGifAdapterInfo.setTv_title(jsonObject.getString("title"));
                                            homeGifAdapterInfo.setIv_content(jsonObject.getString("img"));
                                            list.add(homeGifAdapterInfo);
                                        } catch (JSONException e1) {
                                            e1.printStackTrace();
                                        }
                                    }
                                    lv_gif.setAdapter(homeGifBaseadpter);

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
                                            homeGifAdapterInfo = new HomeGifAdapterInfo();
                                            homeGifAdapterInfo.setIv_head(R.drawable.olddriver);
                                            homeGifAdapterInfo.setTv_name("老司机");
                                            homeGifAdapterInfo.setTv_title(jsonObject.getString("title"));
                                            homeGifAdapterInfo.setIv_content(jsonObject.getString("img"));
                                            list.add(homeGifAdapterInfo);
                                        } catch (JSONException e1) {
                                            e1.printStackTrace();
                                        }
                                    }
                                    homeGifBaseadpter.notifyDataSetChanged();
                                    lv_gif.onLoadComplete();

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
        View view = inflater.inflate(R.layout.home_gif_layout, null);
        return view;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Fresco.initialize(getActivity());
        query = new BmobQuery("TextInfo");
        lv_gif = (ReflashListView) view.findViewById(R.id.lv_gif);
        lv_gif.setInterface(this);
        list = new ArrayList<>();
        homeGifBaseadpter = new HomeGifBaseadpter(getActivity(), list);
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
                                    lv_gif.reflashComplete();
                                }
                            }
                , 2000);

    }


    public void onLoad() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ReflashThread();

                Log.i("info", page + "****************" + "///////////////////****");
            }
        }, 2000);

    }

}


