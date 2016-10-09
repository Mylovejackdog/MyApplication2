package com.example.anzhuo.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.anzhuo.myapplication.AdapterInfo.HomePictureAdapterInfo;
import com.example.anzhuo.myapplication.AdapterInfo.HomeTextAdapterInfo;
import com.example.anzhuo.myapplication.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;

import java.util.List;

/**
 * Created by anzhuo on 2016/9/12.
 */
public class HomePictureBaseadapter extends BaseAdapter {
    List<HomePictureAdapterInfo> list;
    Context context;
    HomePictureAdapterInfo homePictureAdapterInfo;


    public HomePictureBaseadapter(Context context, List<HomePictureAdapterInfo> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.home_picture_item, null);
            viewHolder.iv_head = (ImageView) view.findViewById(R.id.iv_head);
            viewHolder.tv_name = (TextView) view.findViewById(R.id.tv_name);
            viewHolder.tv_title = (TextView) view.findViewById(R.id.tv_title);
            viewHolder.iv_content = (ImageView) view.findViewById(R.id.iv_content);
            viewHolder.tv_good = (TextView) view.findViewById(R.id.tv_good);
            viewHolder.tv_bad = (TextView) view.findViewById(R.id.tv_bad);
            viewHolder.tv_comment = (TextView) view.findViewById(R.id.tv_comment);
            view.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) view.getTag();
        homePictureAdapterInfo = list.get(i);
        viewHolder.iv_head.setImageResource(homePictureAdapterInfo.getIv_head());
        viewHolder.tv_name.setText(homePictureAdapterInfo.getTv_name());
        viewHolder.tv_title.setText(homePictureAdapterInfo.getTv_title());
        Glide.with(context).load(homePictureAdapterInfo.getIv_content()).asBitmap().override(600,800
        ).fitCenter().diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(R.drawable.load).into(viewHolder.iv_content);
        viewHolder.tv_good.setText(homePictureAdapterInfo.getTv_good());
        viewHolder.tv_bad.setText(homePictureAdapterInfo.getTv_bad());
        viewHolder.tv_comment.setText(homePictureAdapterInfo.getTv_comment());
        return view;
    }

    class ViewHolder {
        ImageView iv_head;
        TextView tv_name;
        TextView tv_title;
        ImageView iv_content;
        TextView tv_good;
        TextView tv_bad;
        TextView tv_comment;
    }
}
