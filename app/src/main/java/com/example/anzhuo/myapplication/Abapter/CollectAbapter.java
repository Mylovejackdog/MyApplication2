package com.example.anzhuo.myapplication.Abapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anzhuo.myapplication.Infor.CollectInfor;
import com.example.anzhuo.myapplication.R;

import java.util.List;

/**
 * Created by anzhuo on 2016/9/23.
 */
public class CollectAbapter extends BaseAdapter {
    List<CollectInfor> mlist;
    Context mcontext;
    CollectInfor collectInfor;
    public CollectAbapter(Context context,List<CollectInfor> list){
        this.mcontext=context;
        this.mlist=list;
    }

    @Override
    public int getCount() {
        return mlist==null?0:mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            convertView= LayoutInflater.from(mcontext).inflate(R.layout.my_collect_listview,null);
            viewHolder=new ViewHolder();
            viewHolder.iv_head= (ImageView) convertView.findViewById(R.id.iv_head);
            viewHolder.iv_content= (ImageView) convertView.findViewById(R.id.iv_content);
            viewHolder.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_content= (TextView) convertView.findViewById(R.id.tv_content);
            convertView.setTag(viewHolder);
        }
        viewHolder= (ViewHolder) convertView.getTag();
        collectInfor=mlist.get(position);
        viewHolder.iv_head.setImageResource(collectInfor.getHead());
        viewHolder.tv_name.setText(collectInfor.getName());
        viewHolder.tv_content.setText(collectInfor.getContent());
        viewHolder.iv_content.setImageResource(collectInfor.getIv_content());
        return convertView;
    }
    class ViewHolder{
        ImageView iv_head;
        TextView  tv_name;
        TextView  tv_content;
        ImageView iv_content;
    }
}
