package com.example.anzhuo.myapplication.Audit;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.anzhuo.myapplication.Adapter.HomeAdapter;
import com.example.anzhuo.myapplication.Infor.ContentInfo;
import com.example.anzhuo.myapplication.Infor.FragmentInfo;
import com.example.anzhuo.myapplication.My.Myactivity;
import com.example.anzhuo.myapplication.My.Myenteractivity;
import com.example.anzhuo.myapplication.My.UserInfo;
import com.example.anzhuo.myapplication.R;
import com.facebook.drawee.backends.pipeline.Fresco;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by anzhuo on 2016/9/9.
 */
public class AuditFragment extends Fragment {
    ImageView iv_my;
    ImageView iv_publish;
    ViewPager vp_pager;
    HomeAdapter homeAdapter;
    List<Fragment> mlist=new ArrayList<>();
    FragmentInfo fragmentInfo;
    MyFragment myFragment;
    private static final int MSG = 1;

    BmobQuery <ContentInfo>bmobQuery;

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG:

//                 bmobQuery.findObjects(new FindListener<ContentInfo>() {
//                      @Override
//                      public void done(List<ContentInfo> list, BmobException e) {
//
//                          for(ContentInfo newlist:list){
//                              fragmentInfo=new FragmentInfo();
//                              fragmentInfo.setConment(newlist.getContent());
//                              fragmentInfo.setPhoto(Uri.parse());
//                              fragmentInfo.setPraise(R.drawable.praises);
//                           String name  = newlist.getBmobFile().getFileUrl();
//                              myFragment=new MyFragment(fragmentInfo);
//                                    mlist.add(myFragment) ;
//
//                          }
//                          homeAdapter.notifyDataSetChanged();
//                      }
//                  });
                    BmobQuery bmobQuery=new BmobQuery("ContentInfo");
                    bmobQuery.findObjectsByTable(new QueryListener<JSONArray>() {
                        @Override
                       public void done(JSONArray jsonArray, BmobException e) {
                           for (int i=0;i<jsonArray.length();i++){
                               try {
                                   JSONObject jsonObject=jsonArray.getJSONObject(i);
                                    String content=jsonObject.getString("content");
                                    JSONObject object=jsonObject.getJSONObject("bmobFile");
                                    String photo=object.getString("url");
                                    fragmentInfo=new FragmentInfo();
                                    fragmentInfo.setConment(content);
                                    fragmentInfo.setPhoto(photo);
                                    fragmentInfo.setPraise(R.drawable.praises);
                                    myFragment=new MyFragment(fragmentInfo);
                                    mlist.add(myFragment);
                                    homeAdapter.notifyDataSetChanged();
                                } catch (JSONException e1) {
                                   e1.printStackTrace();
                               }
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
       View view=inflater.inflate(R.layout.oldcar_audit_layout,null);
        return  view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iv_my= (ImageView) view.findViewById(R.id.iv_my);
        iv_publish= (ImageView) view.findViewById(R.id.iv_publish);
        vp_pager= (ViewPager) view.findViewById(R.id.vp_pager);
        Fresco.initialize(getActivity());
        Bmob.initialize(getActivity(),"a914836045e7de6a29035e84e62b59e7");
        send();
        homeAdapter=new HomeAdapter(getFragmentManager(),mlist);

        vp_pager.setAdapter(homeAdapter);
        iv_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             UserInfo   userInfo= BmobUser.getCurrentUser(UserInfo.class);
                if (userInfo!=null){
                    Intent intent=new Intent(getActivity(),AuditContributeActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent=new Intent(getActivity(),Myenteractivity.class);
                    startActivity(intent);
                }

            }
        });
        iv_my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent=new Intent(getActivity(), Myactivity.class);
                    startActivity(intent);
            }
        });
        
    }
    public void send(){
        new Thread() {
            @Override
            public void run() {
                handler.sendEmptyMessage(MSG);
            }
        }.start();
    }
}
