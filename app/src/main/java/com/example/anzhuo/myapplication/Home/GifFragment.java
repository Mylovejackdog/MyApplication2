package com.example.anzhuo.myapplication.Home;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.anzhuo.myapplication.Adapter.HomeGifBaseadpter;
import com.example.anzhuo.myapplication.AdapterInfo.HomeGifAdapterInfo;
import com.example.anzhuo.myapplication.DataInfo.TextBmobInfo;
import com.example.anzhuo.myapplication.DataInfo.TextInfo;
import com.example.anzhuo.myapplication.R;
import com.example.anzhuo.myapplication.ReflashListView.ReflashListView;
import com.facebook.drawee.backends.pipeline.Fresco;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
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
    final static int MSG = 11;
    final static int MSN = 12;
    BmobQuery<TextInfo> query;
    private int limit = 10;
    private int page = 0;
    boolean isCache;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG:
                    query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
                    query.order("-creat_time");
                    query.setLimit(limit);
                    isCache = query.hasCachedResult(TextInfo.class);
                    query.setMaxCacheAge(TimeUnit.DAYS.toMillis(30));
                    if (isCache&&!checkNetworkInfo()) {
                        //--此为举个例子，并不一定按这种方式来设置缓存策略
                        Log.i("textfragment","缓存存在");
                        query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ONLY);    // 如果有缓存的话，则设置策略为CACHE_ELSE_NETWORK
                    } else if(isCache&&checkNetworkInfo()) {
                        query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);    // 如果没有缓存的话，则设置策略为NETWORK_ELSE_CACHE
                    }
                    query.findObjects(new FindListener<TextInfo>() {
                        @Override
                        public void done(List<TextInfo> mlist, BmobException e) {
                            if (e == null) {
                                for (TextInfo info : mlist) {
                                    homeGifAdapterInfo = new HomeGifAdapterInfo();
                                    homeGifAdapterInfo.setIv_head(R.drawable.olddriver);
                                    homeGifAdapterInfo.setTv_name("老司机");
                                    homeGifAdapterInfo.setTv_title(info.getTitle());
                                    homeGifAdapterInfo.setIv_content(info.getImg());
                                    list.add(homeGifAdapterInfo);
                                }
                                homeGifBaseadpter.notifyDataSetChanged();
                            } else {
                                return;
                            }
                        }
                    });
                    break;
                case MSN:
                    page++;
                    query.order("-creat_time");
                    query.setLimit(limit);
                    query.setSkip(limit * page);
                    query.findObjects(new FindListener<TextInfo>() {
                        @Override
                        public void done(List<TextInfo> mlist, BmobException e) {
                            if (e == null) {
                                for (TextInfo info : mlist) {
                                    homeGifAdapterInfo = new HomeGifAdapterInfo();
                                    homeGifAdapterInfo.setIv_head(R.drawable.olddriver);
                                    homeGifAdapterInfo.setTv_name("老司机");
                                    homeGifAdapterInfo.setTv_title(info.getTitle());
                                    homeGifAdapterInfo.setIv_content(info.getImg());
                                    list.add(homeGifAdapterInfo);
                                }
                            } else if (e!=null){
                                Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    });
                    homeGifBaseadpter.notifyDataSetChanged();
                    lv_gif.onLoadComplete();

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
        query = new BmobQuery<TextInfo>();
        lv_gif = (ReflashListView) view.findViewById(R.id.lv_gif);
        lv_gif.setInterface(this);
        list = new ArrayList<>();
        homeGifBaseadpter = new HomeGifBaseadpter(getActivity(), list);
        lv_gif.setAdapter(homeGifBaseadpter);
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
    public boolean checkNetworkInfo() {
        ConnectivityManager conMan = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo.State mobile = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        NetworkInfo.State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        if (mobile == NetworkInfo.State.CONNECTED || mobile == NetworkInfo.State.CONNECTING)
            return true;
        if (wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING)
            return true;
        return false;
    }
}


