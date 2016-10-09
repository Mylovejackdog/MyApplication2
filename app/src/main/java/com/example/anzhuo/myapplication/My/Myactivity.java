package com.example.anzhuo.myapplication.My;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.FragmentManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anzhuo.myapplication.Utils.MainApplication;
import com.example.anzhuo.myapplication.Utils.Util;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.tencent.connect.UserInfo;
import com.tencent.tauth.IUiListener;

import com.example.anzhuo.myapplication.R;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by anzhuo on 2016/9/12.
 */
public class Myactivity extends AppCompatActivity {

    private UserInfo mInfo;//QQ登入
    com.facebook.drawee.view.SimpleDraweeView iv_head;
    public ImageView iv_back,iv_set;
    public ImageButton imagebtn;
    public TextView  tv_name;
    public LinearLayout fans,attention;
    public RadioGroup group;
    public RadioButton rb_contribute,rb_collect,rb_comment;
    public FrameLayout vp_main;
    private boolean isNight = false; //夜间模式变量
    boolean click=true;//夜间模式变量

    Collectactivity collectactivity;
    Commentactivity commentactivity;
    Contributeactivity contributeactivity;
    FragmentManager fragmentManager;
    FragmentTransaction transaction;


    Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    JSONObject response= (JSONObject) msg.obj;
                    if (response.has("nickname")){
                        try {
                            //加载QQ昵称
                            tv_name.setText(response.getString("nickname"));
                            Log.i("LW","123");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 1:
                    //加载QQ头像
                    Bitmap bitmap= (Bitmap) msg.obj;
                    iv_head.setImageBitmap(bitmap);
                    iv_head.setVisibility(View.VISIBLE);
                    Log.i("LW", "1265");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //字体大小设置
        int mode=-1;
        try{
            mode=getIntent().getIntExtra("textsize", 1);
        }catch(NullPointerException e){
            e.printStackTrace();
        }catch (Exception e) {
        }
        if(mode==-1||mode==1){
            this.setTheme(R.style.Theme_Small);
        }else if(mode==2){
            this.setTheme(R.style.Theme_Medium);
        }else {
            this.setTheme(R.style.Theme_Large);
        }
        //夜间模式
        if (MainApplication.appConfig.isNighTheme()){
            Myactivity.this.setTheme(R.style.NightTheme);
            isNight=true;
        }else {
            Myactivity.this.setTheme(R.style.DayTheme);
            isNight=false;
        }
        //圆形图片加载初始化
        Fresco.initialize(this);
        setContentView(R.layout.my_layout);
        iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_set= (ImageView) findViewById(R.id.iv_set);
        iv_head= (com.facebook.drawee.view.SimpleDraweeView) findViewById(R.id.iv_head);
        tv_name= (TextView) findViewById(R.id.tv_name);
        imagebtn= (ImageButton) findViewById(R.id.imagebtn);
        fans= (LinearLayout) findViewById(R.id.fans);
        attention= (LinearLayout) findViewById(R.id.attention);
        group= (RadioGroup) findViewById(R.id.group);
        rb_contribute= (RadioButton) findViewById(R.id.rb_contribute);
        rb_collect= (RadioButton) findViewById(R.id.rb_collect);
        rb_comment= (RadioButton) findViewById(R.id.rb_comment);
        vp_main= (FrameLayout) findViewById(R.id.vp_main);
        showFragment(0);
        //QQ第三方登入
            IUiListener listener = new IUiListener() {
                @Override
                public void onError(UiError uiError) {
                }
                @Override
                public void onComplete(final Object response) {
                    Message msg = new Message();
                    msg.obj = response;
                    msg.what = 0;
                    mHandler.sendMessage(msg);
                    new Thread() {
                        @Override
                        public void run() {
                            JSONObject josn = (JSONObject) response;
                            if (josn.has("figureurl")) {
                                Bitmap bitmap = null;
                                try {
                                    bitmap = Util.getbitmap(josn.getString("figureurl_qq_2"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Message msg = new Message();
                                msg.obj = bitmap;
                                msg.what = 1;
                                mHandler.sendMessage(msg);
                            }
                        }
                    }.start();
                }
                @Override
                public void onCancel() {
                }
            };
        if (MainApplication.mQQAuth!=null){
            mInfo=new UserInfo(this,MainApplication.mQQAuth.getQQToken());
            mInfo.getUserInfo(listener);
        }

        //返回键
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //设置按钮
        iv_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Myactivity.this, Setactivity.class);
                startActivity(intent);
                finish();
            }
        });
        iv_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenta=new Intent(Myactivity.this,Myenteractivity.class);
                startActivity(intenta);
                finish();
            }
        });
        tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //FrameLayout的设置
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_contribute:
                        showFragment(0);
                        break;
                    case R.id.rb_collect:
                        showFragment(1);
                        break;
                    case R.id.rb_comment:
                        showFragment(2);
                        break;
                }
            }
        });
    }
    //fragment的连用
    private void showFragment(int i){
            fragmentManager=getFragmentManager();
            transaction=fragmentManager.beginTransaction();
            hideAllFragment(transaction);
        switch (i){
            case 0:
                if (contributeactivity == null) {
                    contributeactivity = new Contributeactivity();
                    transaction.add(R.id.vp_main, contributeactivity);
                } else {
                    transaction.show(contributeactivity);
                }
                break;
            case 1:
                if (collectactivity == null) {
                    collectactivity = new Collectactivity();
                    transaction.add(R.id.vp_main, collectactivity);
                } else {
                    transaction.show(collectactivity);
                }
                break;
            case 2:
                if (commentactivity == null) {
                    commentactivity = new Commentactivity();
                    transaction.add(R.id.vp_main, commentactivity);
                } else {
                    transaction.show(commentactivity);
                }
                break;
        }
        transaction.commit();
    }
    private void hideAllFragment(FragmentTransaction transaction){
        if (contributeactivity!=null){
            transaction.hide(contributeactivity);
        }
        if (collectactivity!=null){
            transaction.hide(collectactivity);
        }
        if (commentactivity!=null){
            transaction.hide(commentactivity);
        }
    }
    //夜间模式
      public void changeTheme(View view){
          Toast.makeText(this,"123",Toast.LENGTH_SHORT).show();
          if (isNight){
              MainApplication.appConfig.setNightTheme(false);
          }else {
              MainApplication.appConfig.setNightTheme(true);
          }
          Intent intent=getIntent();
          overridePendingTransition(0,R.anim.out_anim);
          finish();
          overridePendingTransition(R.anim.in_anim, 0);
          startActivity(intent);
      }

}
