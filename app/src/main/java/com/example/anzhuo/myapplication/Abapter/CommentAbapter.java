package com.example.anzhuo.myapplication.Abapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anzhuo.myapplication.Infor.CommentInfor;
import com.example.anzhuo.myapplication.R;

import java.util.List;

/**
 * Created by anzhuo on 2016/9/23.
 */
public class CommentAbapter extends BaseAdapter {
    List<CommentInfor> mlist;
    Context mcontext;
    CommentInfor commentInfor;

    public  CommentAbapter(Context context,List<CommentInfor> list){
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
            convertView= LayoutInflater.from(mcontext).inflate(R.layout.my_comment_listview,null);
            viewHolder=new ViewHolder();
            viewHolder.iv_head= (ImageView) convertView.findViewById(R.id.iv_head);
            viewHolder.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_date= (TextView) convertView.findViewById(R.id.tv_date);
            viewHolder.tv_time= (TextView) convertView.findViewById(R.id.tv_time);
            viewHolder.tv_content= (TextView) convertView.findViewById(R.id.tv_content);
            convertView.setTag(viewHolder);
        }
         viewHolder= (ViewHolder) convertView.getTag();
        commentInfor=mlist.get(position);
        viewHolder.iv_head.setImageResource(commentInfor.getHead());
        viewHolder.tv_name.setText(commentInfor.getName());
        viewHolder.tv_date.setText(commentInfor.getDate());
        viewHolder.tv_time.setText(commentInfor.getTime());
        viewHolder.tv_content.setText(commentInfor.getContent());

        return convertView;
    }
    class ViewHolder{
        ImageView iv_head;
        TextView  tv_name;
        TextView  tv_date;
        TextView  tv_time;
        TextView  tv_content;
    }
}
