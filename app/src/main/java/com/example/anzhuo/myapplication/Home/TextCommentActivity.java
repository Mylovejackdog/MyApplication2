package com.example.anzhuo.myapplication.Home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anzhuo.myapplication.DataInfo.Info;
import com.example.anzhuo.myapplication.R;

import cn.bmob.v3.b.I;

/**
 * Created by anzhuo on 2016/10/8.
 */
public class TextCommentActivity extends Activity{
    ImageView iv_head;
    TextView tv_content;
    TextView tv_name;
    ImageView iv_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.textcomment_activity);
        iv_head= (ImageView) findViewById(R.id.comment_iv_head);
        tv_content= (TextView) findViewById(R.id.comment_tv_content);
        tv_name= (TextView) findViewById(R.id.comment_tv_name);
        iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent intent=getIntent();
        iv_head.setImageResource(intent.getExtras().getInt("head"));
        tv_content.setText(Html.fromHtml(intent.getExtras().getString("content")));
        tv_name.setText(intent.getExtras().getString("name"));
    }
}
