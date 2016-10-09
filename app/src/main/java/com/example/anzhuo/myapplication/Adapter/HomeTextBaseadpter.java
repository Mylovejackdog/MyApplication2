package com.example.anzhuo.myapplication.Adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anzhuo.myapplication.AdapterInfo.HomeTextAdapterInfo;
import com.example.anzhuo.myapplication.R;

import java.util.List;

/**
 * Created by anzhuo on 2016/9/12.
 */
public class HomeTextBaseadpter extends BaseAdapter {
    List<HomeTextAdapterInfo> list;
    Context context;
    HomeTextAdapterInfo homeTextInfo;


    public HomeTextBaseadpter(Context context, List<HomeTextAdapterInfo> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.home_text_item, null);
            viewHolder.iv_head = (ImageView) view.findViewById(R.id.iv_head);
            viewHolder.tv_name = (TextView) view.findViewById(R.id.tv_name);
            viewHolder.tv_content = (TextView) view.findViewById(R.id.tv_content);
            viewHolder.tv_good = (TextView) view.findViewById(R.id.tv_good);
            viewHolder.tv_bad = (TextView) view.findViewById(R.id.tv_bad);
            viewHolder.tv_comment = (TextView) view.findViewById(R.id.tv_comment);
            view.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) view.getTag();
        homeTextInfo = list.get(i);
        viewHolder.iv_head.setImageResource(homeTextInfo.getIv_head());
        viewHolder.tv_name.setText(homeTextInfo.getTv_name());
        viewHolder.tv_content.setText(Html.fromHtml(homeTextInfo.getTv_content()));
        viewHolder.tv_good.setText(homeTextInfo.getTv_good());
        viewHolder.tv_bad.setText(homeTextInfo.getTv_bad());
        viewHolder.tv_comment.setText(homeTextInfo.getTv_comment());
        return view;
    }

    class ViewHolder {
        ImageView iv_head;
        TextView tv_name;
        TextView tv_content;
        TextView tv_good;
        TextView tv_bad;
        TextView tv_comment;
    }


}
