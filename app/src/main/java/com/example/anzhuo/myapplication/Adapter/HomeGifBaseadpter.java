package com.example.anzhuo.myapplication.Adapter;

import android.content.Context;
import android.net.Uri;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.anzhuo.myapplication.AdapterInfo.HomeGifAdapterInfo;
import com.example.anzhuo.myapplication.AdapterInfo.HomeTextAdapterInfo;
import com.example.anzhuo.myapplication.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;

import java.util.List;

/**
 * Created by anzhuo on 2016/9/12.
 */
public class HomeGifBaseadpter extends BaseAdapter {
    List<HomeGifAdapterInfo> list;
    Context context;
    HomeGifAdapterInfo homeGifAdapterInfo;


    public HomeGifBaseadpter(Context context, List<HomeGifAdapterInfo> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.home_gif_item, null);
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
        homeGifAdapterInfo = list.get(i);
        viewHolder.iv_head.setImageResource(homeGifAdapterInfo.getIv_head());
        viewHolder.tv_name.setText(homeGifAdapterInfo.getTv_name());
        viewHolder.tv_title.setText(Html.fromHtml(homeGifAdapterInfo.getTv_title()));
        //   viewHolder.iv_content.setImageURI(homeGifAdapterInfo.getIv_content());
        Glide.with(context).load(homeGifAdapterInfo.getIv_content()).asGif().thumbnail(1).sizeMultiplier(1).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(R.drawable.load).into(viewHolder.iv_content);
        viewHolder.tv_good.setText(homeGifAdapterInfo.getTv_good());
        viewHolder.tv_bad.setText(homeGifAdapterInfo.getTv_bad());
        viewHolder.tv_comment.setText(homeGifAdapterInfo.getTv_comment());
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
