package com.example.anzhuo.myapplication.Home;


import android.content.Context;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.anzhuo.myapplication.Adapter.HomeTextBaseadpter;
import com.example.anzhuo.myapplication.AdapterInfo.HomeTextAdapterInfo;
import com.example.anzhuo.myapplication.DataInfo.TextBmobInfo;
import com.example.anzhuo.myapplication.DataInfo.TextInfo;

import com.example.anzhuo.myapplication.R;
import com.example.anzhuo.myapplication.ReflashListView.ReflashListView;

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
public class TextFragment extends Fragment implements ReflashListView.IReflashListener {

    List<HomeTextAdapterInfo> list;
    ReflashListView lv_text;
    HomeTextBaseadpter homeTextBaseadpter;
    HomeTextAdapterInfo homeTextAdapterInfo;
    OkHttpClient okHttpClient;
    final static int MSG = 11;
    final static int MSN = 12;
    TextBmobInfo textBmobInfo;
    BmobQuery<TextBmobInfo> query;
    private int limit = 10;
    private int page = 0;
    public static int RQ=1;
    boolean hasCache;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG:
                    query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
                    query.order("-creat_time");
                    query.setLimit(limit);
                    //判断是否有缓存，该方法必须放在查询条件（如果有的话）都设置完之后再来调用才有效，就像这里一样。
                    hasCache = query.hasCachedResult(TextBmobInfo.class);
                    query.setMaxCacheAge(TimeUnit.DAYS.toMillis(30));
                    if (hasCache&&!checkNetworkInfo()) {
                        //--此为举个例子，并不一定按这种方式来设置缓存策略
                        Log.i("textfragment","缓存存在");
                        query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ONLY);    // 如果有缓存的话，则设置策略为CACHE_ELSE_NETWORK
                    } else if(hasCache&&checkNetworkInfo()) {
                        query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);    // 如果没有缓存的话，则设置策略为NETWORK_ELSE_CACHE
                    }
                    query.findObjects(new FindListener<TextBmobInfo>(){

                        @Override
                        public void done(List<TextBmobInfo> list2, BmobException e) {
                            if (e==null)
                            {
                                for(TextBmobInfo info: list2){
                                    homeTextAdapterInfo = new HomeTextAdapterInfo();
                                    homeTextAdapterInfo.setIv_head(R.drawable.olddriver);
                                    homeTextAdapterInfo.setTv_name("老司机");
                                    homeTextAdapterInfo.setTv_content(info.getContent());
                                    list.add(homeTextAdapterInfo);
                                }
                                homeTextBaseadpter.notifyDataSetChanged();

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
                    query.findObjects(new FindListener<TextBmobInfo>(){
                                         @Override
                                         public void done(List<TextBmobInfo> list2, BmobException e) {
                                             if (e==null){
                                             for(TextBmobInfo info: list2){
                                                 HomeTextAdapterInfo homeTextAdapterInfo = new HomeTextAdapterInfo();
                                                 homeTextAdapterInfo.setIv_head(R.drawable.olddriver);
                                                 homeTextAdapterInfo.setTv_name("老司机");
                                                 homeTextAdapterInfo.setTv_content(info.getContent());
                                                 list.add(homeTextAdapterInfo);
                                             }
                                                 homeTextBaseadpter.notifyDataSetChanged();
                                                 lv_text.onLoadComplete();
                                         }else {
                                                 Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_SHORT).show();
                                                 return;
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
        View view = inflater.inflate(R.layout.home_text_layout, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv_text = (ReflashListView) view.findViewById(R.id.lv_text);
        query = new BmobQuery<TextBmobInfo>();
        lv_text.setInterface(this);
        textBmobInfo = new TextBmobInfo();
        list = new ArrayList<>();
        okHttpClient = new OkHttpClient();
        lv_text.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getActivity(), TextCommentActivity.class);
               intent.putExtra("head",list.get(i-1).getIv_head());
               intent.putExtra("name",list.get(i-1).getTv_name());
                intent.putExtra("content",list.get(i-1).getTv_content());
                startActivityForResult(intent,RQ);
            }
        });
        homeTextBaseadpter = new HomeTextBaseadpter(getActivity(), list);
        lv_text.setAdapter(homeTextBaseadpter);
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

                lv_text.onReflashComplete();

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
