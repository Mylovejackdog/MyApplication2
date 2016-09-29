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

import com.example.anzhuo.myapplication.Adapter.HomePictureBaseadapter;
import com.example.anzhuo.myapplication.AdapterInfo.HomePictureAdapterInfo;
import com.example.anzhuo.myapplication.R;
import com.example.anzhuo.myapplication.ReflashListView.ReflashListView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;

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
public class PictureFragment extends Fragment implements ReflashListView.IReflashListener {
    List<HomePictureAdapterInfo> list;
    ReflashListView lv_pic;
    HomePictureBaseadapter homePictureBaseadapter;
    HomePictureAdapterInfo homePictureAdapterInfo;
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
                                            homePictureAdapterInfo = new HomePictureAdapterInfo();
                                            homePictureAdapterInfo.setIv_head(R.drawable.olddriver);
                                            homePictureAdapterInfo.setTv_name("老司机");
                                            homePictureAdapterInfo.setTv_title(jsonObject.getString("title"));
                                            homePictureAdapterInfo.setIv_content(jsonObject.getString("img"));
                                            list.add(homePictureAdapterInfo);
                                        } catch (JSONException e1) {
                                            e1.printStackTrace();
                                        }
                                    }

                                    lv_pic.setAdapter(homePictureBaseadapter);

                                }
                            }
                        });
                        break;

//                        gson = new Gson();
//                        info = gson.fromJson(str, HomePictureJsonInfo.class);
//                        for (int i = 0; i < info.getShowapi_res_body().getContentlist().toArray().length; i++) {
//                            homePictureAdapterInfo = new HomePictureAdapterInfo();
//                            homePictureAdapterInfo.setIv_head(R.drawable.olddriver);
//                            homePictureAdapterInfo.setTv_name("老司机");
//                            homePictureAdapterInfo.setIv_content(info.getShowapi_res_body().getContentlist().get(i).getImg());
//                            homePictureAdapterInfo.setTv_title(info.getShowapi_res_body().getContentlist().get(i).getTitle());
//                            list.add(0, homePictureAdapterInfo);
//                        }


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
                                            homePictureAdapterInfo = new HomePictureAdapterInfo();
                                            homePictureAdapterInfo.setIv_head(R.drawable.olddriver);
                                            homePictureAdapterInfo.setTv_name("老司机");
                                            homePictureAdapterInfo.setTv_title(jsonObject.getString("title"));
                                            homePictureAdapterInfo.setIv_content(jsonObject.getString("img"));
                                            list.add(homePictureAdapterInfo);
                                        } catch (JSONException e1) {
                                            e1.printStackTrace();
                                        }

                                    }
                                    homePictureBaseadapter.notifyDataSetChanged();
                                    lv_pic.onLoadComplete();

                                }
                            }
                        });


                        break;
//                        gson = new Gson();
//                        info = gson.fromJson(str, HomePictureJsonInfo.class);
//                        for (int i = 0; i < info.getShowapi_res_body().getContentlist().toArray().length; i++) {
//                            homePictureAdapterInfo = new HomePictureAdapterInfo();
//                            homePictureAdapterInfo.setIv_head(R.drawable.olddriver);
//                            homePictureAdapterInfo.setTv_name("老司机");
//                            homePictureAdapterInfo.setIv_content(info.getShowapi_res_body().getContentlist().get(i).getImg());
//                            homePictureAdapterInfo.setTv_title(info.getShowapi_res_body().getContentlist().get(i).getTitle());
//                            list.add(homePictureAdapterInfo);
//                        }


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
        Fresco.initialize(getActivity());
        query = new BmobQuery("Info");
        lv_pic = (ReflashListView) view.findViewById(R.id.lv_pic);
        lv_pic.setInterface(this);
        list = new ArrayList<>();
        homePictureBaseadapter = new HomePictureBaseadapter(getActivity(), list);
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


//    private String requestUrl(String url) {
//        Request request = new Request.Builder().url(url).build();
//        try {
//            Response response = okHttpClient.newCall(request).execute();
//            str = response.body().string();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return str;
//    }


    public void onReflash() {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                lv_pic.reflashComplete();

            }

        }, 2000);

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
