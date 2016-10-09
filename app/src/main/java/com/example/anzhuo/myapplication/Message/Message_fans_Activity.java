package com.example.anzhuo.myapplication.Message;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.anzhuo.myapplication.My.Contributeactivity;
import com.example.anzhuo.myapplication.R;

/**
 * Created by anzhuo on 2016/9/21.
 */
public class Message_fans_Activity extends Activity {
    public ImageView iv_back;
    public RadioGroup group;
    public RadioButton rb_fans;
    public RadioButton rb_attention;
    public FrameLayout frame;
    Message_fans_fans_Activity message_fans_fans_activity;
    Message_fans_attention_Activity message_fans_attention_activity;
    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_fans);
        iv_back= (ImageView) findViewById(R.id.iv_back);
        group= (RadioGroup) findViewById(R.id.group);
        rb_fans= (RadioButton) findViewById(R.id.rb_fans);
        rb_attention= (RadioButton) findViewById(R.id.rb_attention);
        frame= (FrameLayout) findViewById(R.id.frame);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_fans:
                        showFragment(0);
                        break;
                    case R.id.rb_attention:
                        showFragment(1);
                        break;
                }
            }
        });

    }
    private void showFragment(int i){
        fragmentManager=getFragmentManager();
        transaction=fragmentManager.beginTransaction();
        hideAllFragment(transaction);
        switch (i){
            case 0:
                if (message_fans_fans_activity == null) {
                    message_fans_fans_activity = new Message_fans_fans_Activity();
                    transaction.add(R.id.frame, message_fans_fans_activity);
                } else {
                    transaction.show(message_fans_fans_activity);
                }
                break;
            case 1:
                if (message_fans_attention_activity==null){
                    message_fans_attention_activity=new Message_fans_attention_Activity();
                    transaction.add(R.id.frame,message_fans_attention_activity);
                }else {
                    transaction.show(message_fans_attention_activity);
                }
                break;
        }
        transaction.commit();
    }
    private void  hideAllFragment(FragmentTransaction transaction){
        if (message_fans_fans_activity!=null){
            transaction.hide(message_fans_fans_activity);
        }
        if (message_fans_attention_activity!=null){
            transaction.hide(message_fans_attention_activity);
        }

    }
}
