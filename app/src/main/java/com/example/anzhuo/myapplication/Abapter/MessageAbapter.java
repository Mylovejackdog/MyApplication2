package com.example.anzhuo.myapplication.Abapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anzhuo.myapplication.Infor.MessageInfor;
import com.example.anzhuo.myapplication.R;

import java.util.List;

/**
 * Created by anzhuo on 2016/9/23.
 */
public class MessageAbapter extends BaseAdapter {
    List<MessageInfor> mlist;
    Context mcontext;
    MessageInfor messageInfor;

    public  MessageAbapter(Context context,List<MessageInfor> list){
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
            convertView= LayoutInflater.from(mcontext).inflate(R.layout.message_listview_layout,null);
            viewHolder=new ViewHolder();
            viewHolder.iv_head= (ImageView) convertView.findViewById(R.id.iv_head);
            viewHolder.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_message= (TextView) convertView.findViewById(R.id.tv_message);
            viewHolder.tv_date= (TextView) convertView.findViewById(R.id.tv_date);
            viewHolder.tv_time= (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(viewHolder);
        }
        viewHolder= (ViewHolder) convertView.getTag();
        messageInfor=mlist.get(position);
        viewHolder.iv_head.setImageResource(messageInfor.getHead());
        viewHolder.tv_name.setText(messageInfor.getName());
        viewHolder.tv_message.setText(messageInfor.getMessage());
        viewHolder.tv_date.setText(messageInfor.getDate());
        viewHolder.tv_time.setText(messageInfor.getTime());
        return convertView;
    }
    class ViewHolder{
        ImageView iv_head;
        TextView  tv_name;
        TextView  tv_message;
        TextView  tv_date;
        TextView  tv_time;
    }
}
