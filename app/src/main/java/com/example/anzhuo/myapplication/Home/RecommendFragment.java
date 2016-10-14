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
import android.widget.ListView;
import android.widget.Toast;

import com.example.anzhuo.myapplication.Adapter.HomePictureBaseadapter;
import com.example.anzhuo.myapplication.Adapter.RecommendBaseadapter;
import com.example.anzhuo.myapplication.AdapterInfo.HomePictureAdapterInfo;
import com.example.anzhuo.myapplication.AdapterInfo.RecommendAdapterInfo;
import com.example.anzhuo.myapplication.DataInfo.Info;
import com.example.anzhuo.myapplication.DataInfo.RecommendBmobInfo;
import com.example.anzhuo.myapplication.R;
import com.example.anzhuo.myapplication.ReflashListView.ReflashListView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;
import okhttp3.OkHttpClient;

/**
 * Created by anzhuo on 2016/9/9.
 */
public class RecommendFragment extends Fragment implements ReflashListView.IReflashListener {
    OkHttpClient okHttpClient;
    List<RecommendBmobInfo> list;
    RecommendBaseadapter recommendBaseadapter;
    ReflashListView lv_recommend;
    BmobQuery<RecommendBmobInfo> query;
    final static int MSG = 11;
    final static int MSN = 12;
    private int limit = 10;
    private int page = 0;
    boolean isCache;
    final  int RQ=1;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG:
                    query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
                    query.order("-creat_time");
                    query.setLimit(limit);
                    isCache = query.hasCachedResult(RecommendBmobInfo.class);
                    query.setMaxCacheAge(TimeUnit.DAYS.toMillis(30));
                    if (isCache && !checkNetworkInfo()) {
                        //--此为举个例子，并不一定按这种方式来设置缓存策略
                        Log.i("textfragment", "缓存存在");
                        query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ONLY);    // 如果有缓存的话，则设置策略为CACHE_ELSE_NETWORK
                    } else if (isCache && checkNetworkInfo()) {
                        query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);    // 如果没有缓存的话，则设置策略为NETWORK_ELSE_CACHE
                    }
                    query.findObjects(new FindListener<RecommendBmobInfo>() {
                        @Override
                        public void done(List<RecommendBmobInfo> mlist, BmobException e) {
                            if (e == null) {
                                list.addAll(mlist);
                                recommendBaseadapter.notifyDataSetChanged();
                            } else {
                                return;
                            }
                        }
                    });
                    break;

                case MSN:
                    page++;
                    if (count()<limit*page){

                        Toast.makeText(getActivity(),"没有更多数据",Toast.LENGTH_SHORT).show();
                        lv_recommend.onLoadComplete();
                    }else {
                        query.order("-creat_time");
                        query.setLimit(limit);
                        query.setSkip(limit * page);
                        query.findObjects(new FindListener<RecommendBmobInfo>() {
                            @Override
                            public void done(List<RecommendBmobInfo> mlist, BmobException e) {
                                if (e == null) {
                                    list.addAll(mlist);
                                    recommendBaseadapter.notifyDataSetChanged();
                                    lv_recommend.onLoadComplete();
                                } else {
                                    Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        });
                    }
                    break;
            }
        }

    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_recommend_layout, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        query = new BmobQuery<>();
        lv_recommend = (ReflashListView) view.findViewById(R.id.lv_recommend);
        lv_recommend.setInterface(this);
        list = new ArrayList<>();
        lv_recommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int Type = list.get(i-1).getType();
                switch (Type) {
                    case 1:
                        Intent intent1=new Intent(getActivity(),RecommendTextActivity.class);
                        intent1.putExtra("content",list.get(i-1).getContent());
                        startActivityForResult(intent1,RQ);
                        break;
                    case 2:
                        Intent intent2=new Intent(getActivity(),RecomendPicActivity.class);
                        intent2.putExtra("title",list.get(i-1).getTitle());
                        intent2.putExtra("content",list.get(i-1).getContent());
                        startActivityForResult(intent2,RQ);
                        break;
                    case 3:
                        Intent intent3=new Intent(getActivity(),RecomendGifActivity.class);
                        intent3.putExtra("title",list.get(i-1).getTitle());
                        intent3.putExtra("content",list.get(i-1).getContent());
                        startActivityForResult(intent3,RQ);
                        break;
                    case 4:
                        Intent intent4=new Intent(getActivity(),RecomendVideoActivity.class);
                        intent4.putExtra("title",list.get(i-1).getTitle());
                        intent4.putExtra("content",list.get(i-1).getContent());
                        startActivityForResult(intent4,RQ);
                        break;

                }


            }
        });
        recommendBaseadapter = new RecommendBaseadapter(getActivity(), list);
        lv_recommend.setAdapter(recommendBaseadapter);
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
                lv_recommend.reflashComplete();
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

    public int count (){
        query.addQueryKeys("objectId");
        query.findObjects(new FindListener<RecommendBmobInfo>() {
            @Override
            public void done(List<RecommendBmobInfo> list, BmobException e) {

            }
        });

       return  list.size();
    }
}
