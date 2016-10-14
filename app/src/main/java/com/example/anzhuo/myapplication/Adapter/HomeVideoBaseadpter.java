package com.example.anzhuo.myapplication.Adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.anzhuo.myapplication.AdapterInfo.HomeGifAdapterInfo;
import com.example.anzhuo.myapplication.AdapterInfo.HomeVideoAdapterInfo;
import com.example.anzhuo.myapplication.R;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by anzhuo on 2016/9/12.
 */
public class HomeVideoBaseadpter extends BaseAdapter {
    List<HomeVideoAdapterInfo> list;
    Context context;
    HomeVideoAdapterInfo homeVideoAdapterInfo;


    public HomeVideoBaseadpter(Context context, List<HomeVideoAdapterInfo> list) {
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

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.home_video_item, null);
            viewHolder.iv_head = (ImageView) view.findViewById(R.id.iv_head);
            viewHolder.tv_name = (TextView) view.findViewById(R.id.tv_name);
            viewHolder.tv_title = (TextView) view.findViewById(R.id.tv_title);
            viewHolder.iv_content = (JCVideoPlayerStandard) view.findViewById(R.id.iv_content);
            viewHolder.tv_good = (TextView) view.findViewById(R.id.tv_good);
            viewHolder.tv_bad = (TextView) view.findViewById(R.id.tv_bad);
            viewHolder.tv_comment = (TextView) view.findViewById(R.id.tv_comment);
            view.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) view.getTag();
        homeVideoAdapterInfo = list.get(i);
        viewHolder.iv_head.setImageResource(homeVideoAdapterInfo.getIv_head());
        viewHolder.tv_name.setText(homeVideoAdapterInfo.getTv_name());
        viewHolder.tv_title.setText(homeVideoAdapterInfo.getTv_title());
        viewHolder.iv_content.setUp(homeVideoAdapterInfo.getIv_content(),JCVideoPlayer.SCREEN_LAYOUT_LIST,"老司机出品");
        viewHolder.tv_good.setText(homeVideoAdapterInfo.getTv_good());
        viewHolder.tv_bad.setText(homeVideoAdapterInfo.getTv_bad());
        viewHolder.tv_comment.setText(homeVideoAdapterInfo.getTv_comment());
        return view;
    }



    class ViewHolder {
        ImageView iv_head;
        TextView tv_name;
        TextView tv_title;
        JCVideoPlayerStandard iv_content;
        TextView tv_good;
        TextView tv_bad;
        TextView tv_comment;
    }


}
