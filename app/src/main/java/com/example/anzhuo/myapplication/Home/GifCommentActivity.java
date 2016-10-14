package com.example.anzhuo.myapplication.Home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.anzhuo.myapplication.Adapter.TextCommentBaseadapter;
import com.example.anzhuo.myapplication.AdapterInfo.CommentAdapterInfo;
import com.example.anzhuo.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anzhuo on 2016/10/8.
 */
public class GifCommentActivity extends Activity{
    ImageView iv_head;
    ImageView iv_content;
    ImageView iv_back;
    TextView tv_name;
    TextView tv_title;
    ListViewForScrollView pic_comment_lv;
    TextCommentBaseadapter textCommentBaseadapter;
    List<CommentAdapterInfo> list;
    CommentAdapterInfo commentAdapterInfo;
    Intent intent;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gifcomment_activity);
        iv_head= (ImageView) findViewById(R.id.comment_iv_head);
        tv_name= (TextView) findViewById(R.id.comment_tv_name);
        tv_title= (TextView) findViewById(R.id.comment_tv_title);
        iv_content=(ImageView)findViewById(R.id.comment_iv_content);
        pic_comment_lv= (ListViewForScrollView) findViewById(R.id.pic_comment_lv);
        iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        list=new ArrayList<>();
         intent=getIntent();
        iv_head.setImageResource(intent.getExtras().getInt("head"));
        tv_name.setText(intent.getExtras().getString("name"));
        tv_title.setText(Html.fromHtml(intent.getExtras().getString("title")));
        Glide.with(this).load(intent.getExtras().getString("content")).asGif().override(600,800
        ).fitCenter().diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(R.drawable.load).into(iv_content);

        iv_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(GifCommentActivity.this, GifShowerActivity.class);
                intent1.putExtra("contentsrc",intent.getExtras().getString("content"));
                startActivity(intent1);
            }
        });
    }

}
