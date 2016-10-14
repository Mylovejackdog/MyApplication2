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

import com.example.anzhuo.myapplication.Adapter.HomePictureBaseadapter;
import com.example.anzhuo.myapplication.AdapterInfo.HomePictureAdapterInfo;
import com.example.anzhuo.myapplication.DataInfo.Info;
import com.example.anzhuo.myapplication.DataInfo.TextBmobInfo;
import com.example.anzhuo.myapplication.R;
import com.example.anzhuo.myapplication.ReflashListView.ReflashListView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;

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
public class PictureFragment extends Fragment implements ReflashListView.IReflashListener {
    List<HomePictureAdapterInfo> list;
    ReflashListView lv_pic;
    HomePictureBaseadapter homePictureBaseadapter;
    HomePictureAdapterInfo homePictureAdapterInfo;
    OkHttpClient okHttpClient;
    final static int MSG = 11;
    final static int MSN = 12;
    BmobQuery<Info> query;
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
                    isCache = query.hasCachedResult(Info.class);
                    query.setMaxCacheAge(TimeUnit.DAYS.toMillis(30));
                    if (isCache&&!checkNetworkInfo()) {
                        //--此为举个例子，并不一定按这种方式来设置缓存策略
                        Log.i("textfragment","缓存存在");
                        query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ONLY);    // 如果有缓存的话，则设置策略为CACHE_ELSE_NETWORK
                    } else if(isCache&&checkNetworkInfo()) {
                        query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);    // 如果没有缓存的话，则设置策略为NETWORK_ELSE_CACHE
                    }
                    query.findObjects(new FindListener<Info>() {
                        @Override
                        public void done(List<Info> mlist, BmobException e) {
                            if (e == null) {
                                for (Info info : mlist) {
                                    homePictureAdapterInfo = new HomePictureAdapterInfo();
                                    homePictureAdapterInfo.setIv_head(R.drawable.olddriver);
                                    homePictureAdapterInfo.setTv_name("老司机");
                                    homePictureAdapterInfo.setTv_title(info.getTitle());
                                    homePictureAdapterInfo.setIv_content(info.getImg());
                                    list.add(homePictureAdapterInfo);
                                }
                                homePictureBaseadapter.notifyDataSetChanged();
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
                    query.findObjects(new FindListener<Info>() {
                        @Override
                        public void done(List<Info> mlist, BmobException e) {
                            if (e == null) {
                                for (Info info : mlist) {
                                    homePictureAdapterInfo = new HomePictureAdapterInfo();
                                    homePictureAdapterInfo.setIv_head(R.drawable.olddriver);
                                    homePictureAdapterInfo.setTv_name("老司机");
                                    homePictureAdapterInfo.setTv_title(info.getTitle());
                                    homePictureAdapterInfo.setIv_content(info.getImg());
                                    list.add(homePictureAdapterInfo);
                                }
                                homePictureBaseadapter.notifyDataSetChanged();
                                lv_pic.onLoadComplete();
                            } else {
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
        View view = inflater.inflate(R.layout.home_picture_layout, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        query = new BmobQuery<Info>();
        lv_pic = (ReflashListView) view.findViewById(R.id.lv_pic);
        lv_pic.setInterface(this);
        list = new ArrayList<>();
        lv_pic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getActivity(), PictureCommentActivity.class);
                intent.putExtra("head",list.get(i-1).getIv_head());
                intent.putExtra("name",list.get(i-1).getTv_name());
                intent.putExtra("title",list.get(i-1).getTv_title());
                intent.putExtra("content",list.get(i-1).getIv_content());
                startActivity(intent);

            }
        });
        homePictureBaseadapter = new HomePictureBaseadapter(getActivity(), list);
        lv_pic.setAdapter(homePictureBaseadapter);
        okHttpClient = new OkHttpClient();
        startThread();
    }

    public void startThread() {
        new Thread() {

            @Override
            public void run() {
                //               requestUrl("https://route.showapi.com/341-2?maxResult=50&page=&showapi_appid=24171&showapi_timestamp=20160919160521&time=2016-1-1&showapi_sign=bed59e2c8f354caf02960a97c2eb4a28");
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
                lv_pic.onReflashComplete();
            }

        }, 2000);

    }


    public void onLoad() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ReflashThread();
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
