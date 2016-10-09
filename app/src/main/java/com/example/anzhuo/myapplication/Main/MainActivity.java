package com.example.anzhuo.myapplication.Main;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.anzhuo.myapplication.Audit.AuditFragment;
import com.example.anzhuo.myapplication.Home.HomeFragment;
import com.example.anzhuo.myapplication.Message.MessageFragment;
import com.example.anzhuo.myapplication.R;

import cn.bmob.v3.Bmob;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * Created by anzhuo on 2016/9/9.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    RadioGroup rg_main;
    RadioButton rb_home;
    RadioButton rb_audit;
    RadioButton rb_message;
    HomeFragment homeActivity;
    AuditFragment auditActivity;
    MessageFragment messageActivity;
    FragmentManager manager;
    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, "a914836045e7de6a29035e84e62b59e7");
        setContentView(R.layout.oldcar_main_layout);
        rg_main = (RadioGroup) findViewById(R.id.rg_main);
        rb_home = (RadioButton) findViewById(R.id.rb_home);
        rb_audit = (RadioButton) findViewById(R.id.rb_audit);
        rb_message = (RadioButton) findViewById(R.id.rb_message);

        rb_home.setOnClickListener(this);
        rb_audit.setOnClickListener(this);
        rb_message.setOnClickListener(this);
        showFrame(0);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_home:
                showFrame(0);
                break;
            case R.id.rb_audit:
                showFrame(1);
                 break;
            case R.id.rb_message:
                showFrame(2);
                break;
        }
    }

    private void showFrame(int i) {
     manager=getSupportFragmentManager();
        transaction=manager.beginTransaction();
        hideAllFrame(transaction);
        switch (i) {
            case 0:
                if (homeActivity == null) {
                    homeActivity = new HomeFragment();
                    transaction.add(R.id.fl_main, homeActivity);
                } else {
                    transaction.show(homeActivity);
                }
                break;
            case 1:
                if (auditActivity == null) {
                    auditActivity = new AuditFragment();
                    transaction.add(R.id.fl_main, auditActivity);
                } else {
                    transaction.show(auditActivity);
                }
                break;
            case 2:
                if (messageActivity == null) {
                    messageActivity = new MessageFragment();
                    transaction.add(R.id.fl_main, messageActivity);
                } else {
                    transaction.show(messageActivity);
                }
                break;
        }
        transaction.commit();
    }

    private void hideAllFrame(FragmentTransaction transaction) {
        if (homeActivity != null) {
            transaction.hide(homeActivity);
        }
        if (auditActivity != null) {
            transaction.hide(auditActivity);
        }
        if (messageActivity != null) {
            transaction.hide(messageActivity);
        }
    }
    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    public boolean checkNetworkInfo() {
        ConnectivityManager conMan = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo.State mobile = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        NetworkInfo.State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        if (mobile == NetworkInfo.State.CONNECTED || mobile == NetworkInfo.State.CONNECTING)
            return true;
        if (wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING)
            return true;
        return false;
    }
}
