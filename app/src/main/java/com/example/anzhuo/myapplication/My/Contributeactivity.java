package com.example.anzhuo.myapplication.My;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.anzhuo.myapplication.Abapter.ContributeAbapter;
import com.example.anzhuo.myapplication.Infor.CollectInfor;
import com.example.anzhuo.myapplication.Infor.ContentInfo;
import com.example.anzhuo.myapplication.Infor.ContributeInfor;
import com.example.anzhuo.myapplication.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by anzhuo on 2016/9/12.
 */
public class Contributeactivity extends Fragment {
    public TextView tv_number;
    public Button   bt_compile;
    public ListView lv_content;

    private static final int MSG=1;
    ContributeInfor contributeInfor;
    ContributeAbapter contributeAbapter;

/*    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG:
                    UserInfo userInfo= BmobUser.getCurrentUser(UserInfo.class);
                    BmobQuery<ContentInfo> query=new BmobQuery<ContentInfo>();
                    query.addWhereEqualTo("author",userInfo);
                    query.order("-updatedAt");
                    query.include("author");
                    query.findObjects(new FindListener<ContentInfo>() {
                        @Override
                        public void done(List<ContentInfo> list, BmobException e) {
                            for (ContentInfo mlist:list){
                                contributeInfor=new ContributeInfor();
                                contributeInfor.setContext(mlist.getContent());

                            }
                        }
                    });
                    break;
            }
        }
    };*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.my_contribute_layout,null);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_number= (TextView) view.findViewById(R.id.tv_number);
        bt_compile= (Button) view.findViewById(R.id.bt_compile);
        lv_content= (ListView) view.findViewById(R.id.lv_content);

        bt_compile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        lv_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });


    }

}
