package com.example.anzhuo.myapplication.Adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.anzhuo.myapplication.AdapterInfo.CommentAdapterInfo;
import com.example.anzhuo.myapplication.AdapterInfo.HomeGifAdapterInfo;
import com.example.anzhuo.myapplication.R;

import java.util.List;

/**
 * Created by anzhuo on 2016/10/8.
 */
public class TextCommentBaseadapter extends BaseAdapter {
    List<CommentAdapterInfo> list;
    Context context;
    CommentAdapterInfo commentAdapterInfo;

    public TextCommentBaseadapter(Context context, List<CommentAdapterInfo> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.comment_item, null);
            viewHolder.comment_head = (ImageView) view.findViewById(R.id.comment_head);
            viewHolder.commetn_name = (TextView) view.findViewById(R.id.comment_name);
            viewHolder.comment_time = (TextView) view.findViewById(R.id.comment_time);
            viewHolder.comment_content = (TextView) view.findViewById(R.id.comment_content);
            view.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) view.getTag();
        commentAdapterInfo = list.get(i);
        viewHolder.comment_head.setImageResource(commentAdapterInfo.getComment_head());
        viewHolder.commetn_name.setText(commentAdapterInfo.getComment_name());
        viewHolder.comment_time.setText(commentAdapterInfo.getComment_time());
        viewHolder.comment_content.setText(commentAdapterInfo.getComment_content());
        return view;
    }

    class ViewHolder {
        ImageView comment_head;
        TextView commetn_name;
        TextView comment_time;
        TextView comment_content;

    }
}
