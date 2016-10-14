package com.example.anzhuo.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.anzhuo.myapplication.DataInfo.RecommendBmobInfo;
import com.example.anzhuo.myapplication.Home.TextCommentActivity;
import com.example.anzhuo.myapplication.R;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by anzhuo on 2016/10/10.
 */
public class RecommendBaseadapter extends BaseAdapter {
    List<RecommendBmobInfo> list;
    Context context;
    final int VIEW_TYPE = 4;
    final int TYPE_1 = 1;
    final int TYPE_2 = 2;
    final int TYPE_3 = 3;
    final int TYPE_4 = 4;
    LayoutInflater inflater;

    public RecommendBaseadapter(Context context, List<RecommendBmobInfo> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    // 每个convert view都会调用此方法，获得当前所需要的view样式
    @Override
    public int getItemViewType(int position) {
        return list.get(position).getType();
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolderText holderText = null;
        ViewHolderPic holderPic = null;
        ViewHolderGif holderGif = null;
        ViewHolderVideo holderVideo = null;
        int type = getItemViewType(i);
        if (view == null) {
            inflater = LayoutInflater.from(context);
            switch (type) {
                case TYPE_1:
                    view = inflater.inflate(R.layout.home_text_item, null);
                    holderText = new ViewHolderText();
                    holderText.iv_head = (ImageView) view.findViewById(R.id.iv_head);
                    holderText.tv_name = (TextView) view.findViewById(R.id.tv_name);
                    holderText.tv_content = (TextView) view.findViewById(R.id.tv_content);
                    holderText.tv_good = (TextView) view.findViewById(R.id.tv_good);
                    holderText.tv_bad = (TextView) view.findViewById(R.id.tv_bad);
                    holderText.tv_comment = (TextView) view.findViewById(R.id.tv_comment);
                    view.setTag(holderText);
                    break;
                case TYPE_2:
                    holderPic = new ViewHolderPic();
                    view = inflater.inflate(R.layout.home_picture_item, null);
                    holderPic.iv_head = (ImageView) view.findViewById(R.id.iv_head);
                    holderPic.tv_name = (TextView) view.findViewById(R.id.tv_name);
                    holderPic.tv_title = (TextView) view.findViewById(R.id.tv_title);
                    holderPic.iv_content = (ImageView) view.findViewById(R.id.iv_content);
                    holderPic.tv_good = (TextView) view.findViewById(R.id.tv_good);
                    holderPic.tv_bad = (TextView) view.findViewById(R.id.tv_bad);
                    holderPic.tv_comment = (TextView) view.findViewById(R.id.tv_comment);
                    view.setTag(holderPic);
                    break;
                case TYPE_3:
                    holderGif = new ViewHolderGif();
                    view = inflater.inflate(R.layout.home_gif_item, null);
                    holderGif.iv_head = (ImageView) view.findViewById(R.id.iv_head);
                    holderGif.tv_name = (TextView) view.findViewById(R.id.tv_name);
                    holderGif.tv_title = (TextView) view.findViewById(R.id.tv_title);
                    holderGif.iv_content = (ImageView) view.findViewById(R.id.iv_content);
                    holderGif.tv_good = (TextView) view.findViewById(R.id.tv_good);
                    holderGif.tv_bad = (TextView) view.findViewById(R.id.tv_bad);
                    holderGif.tv_comment = (TextView) view.findViewById(R.id.tv_comment);
                    view.setTag(holderGif);
                    break;
                case TYPE_4:
                    holderVideo = new ViewHolderVideo();
                    view = inflater.inflate(R.layout.home_video_item, null);
                    holderVideo.iv_head = (ImageView) view.findViewById(R.id.iv_head);
                    holderVideo.tv_name = (TextView) view.findViewById(R.id.tv_name);
                    holderVideo.tv_title = (TextView) view.findViewById(R.id.tv_title);
                    holderVideo.iv_content = (JCVideoPlayerStandard) view.findViewById(R.id.iv_content);
                    holderVideo.tv_good = (TextView) view.findViewById(R.id.tv_good);
                    holderVideo.tv_bad = (TextView) view.findViewById(R.id.tv_bad);
                    holderVideo.tv_comment = (TextView) view.findViewById(R.id.tv_comment);
                    view.setTag(holderVideo);
                    break;
            }

        } else {
            switch (type) {
                case TYPE_1:
                    holderText = (ViewHolderText) view.getTag();
                    break;
                case TYPE_2:
                    holderPic = (ViewHolderPic) view.getTag();
                    break;
                case TYPE_3:
                    holderGif = (ViewHolderGif) view.getTag();
                    break;
                case TYPE_4:
                    holderVideo = (ViewHolderVideo) view.getTag();
                    break;
            }
        }

        RecommendBmobInfo recommendBmobInfo = list.get(i);
        switch (type) {

            case TYPE_1:
                holderText.tv_content.setText(recommendBmobInfo.getContent());
                break;
            case TYPE_2:
                Glide.with(context).load(recommendBmobInfo.getContent()).asBitmap().override(600, 800
                ).fitCenter().diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(R.drawable.load).into(holderPic.iv_content);
                break;
            case TYPE_3:
                Glide.with(context).load(recommendBmobInfo.getContent()).asGif().thumbnail(1).sizeMultiplier(1).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(R.drawable.load).into(holderGif.iv_content);
                break;
            case TYPE_4:
                holderVideo.iv_content.setUp(recommendBmobInfo.getContent(), JCVideoPlayer.SCREEN_LAYOUT_LIST, "老司机出品");

                break;
        }


        return view;
    }




    public class ViewHolderText {
        ImageView iv_head;
        TextView tv_name;
        TextView tv_content;
        TextView tv_good;
        TextView tv_bad;
        TextView tv_comment;
    }

    public class ViewHolderPic {
        ImageView iv_head;
        TextView tv_name;
        TextView tv_title;
        ImageView iv_content;
        TextView tv_good;
        TextView tv_bad;
        TextView tv_comment;
    }

    public class ViewHolderGif {
        ImageView iv_head;
        TextView tv_name;
        TextView tv_title;
        ImageView iv_content;
        TextView tv_good;
        TextView tv_bad;
        TextView tv_comment;
    }

    public class ViewHolderVideo {
        ImageView iv_head;
        TextView tv_name;
        TextView tv_title;
        JCVideoPlayerStandard iv_content;
        TextView tv_good;
        TextView tv_bad;
        TextView tv_comment;
    }


}
