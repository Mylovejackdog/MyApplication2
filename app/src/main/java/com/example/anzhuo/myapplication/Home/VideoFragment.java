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

import com.example.anzhuo.myapplication.Adapter.HomeVideoBaseadpter;
import com.example.anzhuo.myapplication.AdapterInfo.HomeVideoAdapterInfo;
import com.example.anzhuo.myapplication.DataInfo.TextBmobInfo;
import com.example.anzhuo.myapplication.DataInfo.VideoBmobInfo;
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
public class VideoFragment extends Fragment implements ReflashListView.IReflashListener {
    List<HomeVideoAdapterInfo> list;
    ReflashListView lv_video;
    HomeVideoBaseadpter homeVideoBaseadpter;
    HomeVideoAdapterInfo homeVideoAdapterInfo;
    OkHttpClient okHttpClient;
    final static int MSG = 11;
    final static int MSN = 12;
    BmobQuery<VideoBmobInfo> query;
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
                    isCache = query.hasCachedResult(VideoBmobInfo.class);
                    query.setMaxCacheAge(TimeUnit.DAYS.toMillis(30));
                    if (isCache&&!checkNetworkInfo()) {
                        //--此为举个例子，并不一定按这种方式来设置缓存策略
                        Log.i("textfragment","缓存存在");
                        query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ONLY);    // 如果有缓存的话，则设置策略为CACHE_ELSE_NETWORK
                    } else if(isCache&&checkNetworkInfo()) {
                        query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);    // 如果没有缓存的话，则设置策略为NETWORK_ELSE_CACHE
                    }
                    query.findObjects(new FindListener<VideoBmobInfo>() {
                        @Override
                        public void done(List<VideoBmobInfo> mlist, BmobException e) {
                            if(e==null){
                                for (VideoBmobInfo info:mlist){
                                    homeVideoAdapterInfo = new HomeVideoAdapterInfo();
                                    homeVideoAdapterInfo.setIv_head(R.drawable.olddriver);
                                    homeVideoAdapterInfo.setTv_name("老司机");
                                    homeVideoAdapterInfo.setTv_title(info.getTitle());
                                    homeVideoAdapterInfo.setIv_content(info.getContent());
                                    list.add(homeVideoAdapterInfo);
                                }
                                homeVideoBaseadpter.notifyDataSetChanged();
                            }else {
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
                    query.findObjects(new FindListener<VideoBmobInfo>() {
                        @Override
                        public void done(List<VideoBmobInfo> mlist, BmobException e) {
                            if(e!=null){
                                Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_SHORT).show();
                                return;
                            }
                                for (VideoBmobInfo info:mlist){
                                    homeVideoAdapterInfo = new HomeVideoAdapterInfo();
                                    homeVideoAdapterInfo.setIv_head(R.drawable.olddriver);
                                    homeVideoAdapterInfo.setTv_name("老司机");
                                    homeVideoAdapterInfo.setTv_title(info.getTitle());
                                    homeVideoAdapterInfo.setIv_content(info.getContent());
                                    list.add(homeVideoAdapterInfo);

                                }

                        }
                    });
                    homeVideoBaseadpter.notifyDataSetChanged();
                    lv_video.onLoadComplete();
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
        query = new BmobQuery<VideoBmobInfo>();
        lv_video = (ReflashListView) view.findViewById(R.id.lv_video);
        lv_video.setInterface(this);
        list = new ArrayList<>();
        homeVideoBaseadpter = new HomeVideoBaseadpter(getActivity(), list);
        lv_video.setAdapter(homeVideoBaseadpter);
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
