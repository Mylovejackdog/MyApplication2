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

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by anzhuo on 2016/10/8.
 */
public class VideoCommentActivity extends Activity{
    ImageView iv_head;
    ImageView iv_back;
    JCVideoPlayerStandard iv_content;
    TextView tv_name;
    TextView tv_title;
    ListViewForScrollView pic_comment_lv;
    TextCommentBaseadapter textCommentBaseadapter;
    List<CommentAdapterInfo> list;
    CommentAdapterInfo commentAdapterInfo;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videocomment_activity);
        iv_head= (ImageView) findViewById(R.id.comment_iv_head);
        tv_name= (TextView) findViewById(R.id.comment_tv_name);
        tv_title= (TextView) findViewById(R.id.comment_tv_title);
        iv_content=(JCVideoPlayerStandard)findViewById(R.id.iv_content);
        pic_comment_lv= (ListViewForScrollView) findViewById(R.id.pic_comment_lv);
        iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        list=new ArrayList<>();
        Intent intent=getIntent();
        iv_head.setImageResource(intent.getExtras().getInt("head"));
        tv_name.setText(intent.getExtras().getString("name"));
        tv_title.setText(Html.fromHtml(intent.getExtras().getString("title")));
         iv_content.setUp(intent.getExtras().getString("content"), JCVideoPlayer.SCREEN_LAYOUT_LIST,"老司机出品");
//        for (int i=0;i<20;i++){
//            commentAdapterInfo=new CommentAdapterInfo();
//            commentAdapterInfo.setComment_head(R.drawable.olddriver);
//            commentAdapterInfo.setComment_name("老司机");
//            commentAdapterInfo.setComment_time("2016-10-8 16:59");
//            commentAdapterInfo.setComment_content("你好吗");
//            list.add(commentAdapterInfo);
//        }
//       textCommentBaseadapter=new TextCommentBaseadapter(this,list);
//        pic_comment_lv.setAdapter(textCommentBaseadapter);

    }
}
