package com.example.anzhuo.myapplication.Home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anzhuo.myapplication.AdapterInfo.CommentAdapterInfo;
import com.example.anzhuo.myapplication.R;

import java.util.List;

/**
 * Created by anzhuo on 2016/10/12.
 */
public class RecommendTextActivity extends Activity {
    ImageView iv_head;
    TextView tv_content;
    TextView tv_name;
    ImageView iv_back;
    ListViewForScrollView pic_comment_lv;
    List<CommentAdapterInfo> list;
    CommentAdapterInfo commentAdapterInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommend_text_activity);
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
        tv_content.setText(Html.fromHtml(intent.getExtras().getString("content")));
    }
}
