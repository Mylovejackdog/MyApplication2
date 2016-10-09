package com.example.anzhuo.myapplication.Message;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.anzhuo.myapplication.My.Myactivity;
import com.example.anzhuo.myapplication.R;

/**
 * Created by anzhuo on 2016/9/9.
 */
public class MessageFragment extends Fragment {

        public ImageView iv_main;
        public TextView tv_blacklist;
        public LinearLayout contribute;
        public LinearLayout message;
        public LinearLayout fans;
        public ListView lv_content;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.oldcar_message_layout, null);
            return view;
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            iv_main = (ImageView) view.findViewById(R.id.iv_main);
            tv_blacklist = (TextView) view.findViewById(R.id.tv_blacklist);
            contribute = (LinearLayout) view.findViewById(R.id.contribute);
            message = (LinearLayout) view.findViewById(R.id.message);
            fans = (LinearLayout) view.findViewById(R.id.fans);
            lv_content = (ListView) view.findViewById(R.id.lv_content);
            iv_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), Myactivity.class);
                    startActivity(intent);
                }
            });
            tv_blacklist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            contribute.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), Message_interaction_Activity.class);
                    startActivity(intent);
                }
            });
            message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), Message_message_Activity.class);
                    startActivity(intent);
                }
            });
            fans.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), Message_fans_Activity.class);
                    startActivity(intent);
                }
            });
            lv_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
        }

    }


