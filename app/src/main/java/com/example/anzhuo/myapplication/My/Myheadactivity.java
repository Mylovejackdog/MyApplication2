package com.example.anzhuo.myapplication.My;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.anzhuo.myapplication.R;
import com.tencent.connect.*;
import com.tencent.connect.UserInfo;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by anzhuo on 2016/9/13.
 */
public class Myheadactivity extends Activity implements View.OnClickListener{
    ImageView iv_back,iv_head;
    EditText signature;
    TextView  tv_phone;
    RadioGroup rg_gender;
    RadioButton rb_man,rb_woman;
    Button bt_back;
    com.example.anzhuo.myapplication.My.UserInfo userInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_head_layout);

        iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_head= (ImageView) findViewById(R.id.iv_head);
        tv_phone= (TextView) findViewById(R.id.tv_phone);
        signature= (EditText) findViewById(R.id.signature);
        rg_gender= (RadioGroup) findViewById(R.id.rg_gender);
        rb_man= (RadioButton) findViewById(R.id.rb_man);
        rb_woman= (RadioButton) findViewById(R.id.rb_woman);
        bt_back= (Button) findViewById(R.id.bt_back);

        iv_back.setOnClickListener(this);
        iv_head.setOnClickListener(this);
        rg_gender.setOnClickListener(this);
        bt_back.setOnClickListener(this);

        userInfo=BmobUser.getCurrentUser(com.example.anzhuo.myapplication.My.UserInfo.class);
        if (userInfo != null) {
            if(userInfo.getHead()!=null) {
                Glide.with(Myheadactivity.this).load(userInfo.getHead().getFileUrl()).asBitmap().into(iv_head);
            }
            else {
                iv_head.setImageResource(R.drawable.avator_default);
            }
            String phone= (String) BmobUser.getObjectByKey("phonenumber");
            tv_phone.setText(phone);
        } else {
            tv_phone.setText("没有注册电话号码....");
            iv_head.setImageResource(R.drawable.avator_default);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                Intent intent=new Intent(Myheadactivity.this,Myactivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.iv_head:
                break;
            case R.id.rg_gender:
                break;
            case R.id.bt_back:
                com.example.anzhuo.myapplication.My.UserInfo.logOut();
                Intent intent2=new Intent(Myheadactivity.this,Myactivity.class);
                startActivity(intent2);
                finish();
                break;
        }
    }

}
