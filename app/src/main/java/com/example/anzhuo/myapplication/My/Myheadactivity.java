package com.example.anzhuo.myapplication.My;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.anzhuo.myapplication.R;

/**
 * Created by anzhuo on 2016/9/13.
 */
public class Myheadactivity extends Activity implements View.OnClickListener{
    ImageView iv_back,iv_head;
    EditText et_phone,signature;
    RadioGroup rg_gender;
    RadioButton rb_man,rb_woman;
    Button bt_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_head_layout);

        iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_head= (ImageView) findViewById(R.id.iv_head);
        et_phone= (EditText) findViewById(R.id.et_phone);
        signature= (EditText) findViewById(R.id.signature);
        rg_gender= (RadioGroup) findViewById(R.id.rg_gender);
        rb_man= (RadioButton) findViewById(R.id.rb_man);
        rb_woman= (RadioButton) findViewById(R.id.rb_woman);
        bt_back= (Button) findViewById(R.id.bt_back);

        iv_back.setOnClickListener(this);
        iv_head.setOnClickListener(this);
        rg_gender.setOnClickListener(this);
        bt_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                Intent intent=new Intent(Myheadactivity.this,Myenteractivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.iv_head:
                Intent intent1=new Intent(Myheadactivity.this,MyheadphotoActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.rg_gender:
                break;
            case R.id.bt_back:
                break;
        }
    }

}
