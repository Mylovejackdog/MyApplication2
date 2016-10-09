package com.example.anzhuo.myapplication.Abapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anzhuo.myapplication.Infor.FansInfor;
import com.example.anzhuo.myapplication.R;

import java.util.List;

/**
 * Created by anzhuo on 2016/9/23.
 */
public class FansAbapter extends BaseAdapter {
    List<FansInfor> mlist;
    Context mcontext;
    FansInfor fansInfor;

    public FansAbapter(Context context,List<FansInfor> list){
        this.mlist=list;
        this.mcontext=context;
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
            convertView= LayoutInflater.from(mcontext).inflate(R.layout.fans_listview_layout,null);
            viewHolder=new ViewHolder();
            viewHolder.iv_head= (ImageView) convertView.findViewById(R.id.iv_head);
            viewHolder.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_motto= (TextView) convertView.findViewById(R.id.tv_motto);
            viewHolder.tv_attention= (TextView) convertView.findViewById(R.id.tv_attention);
            convertView.setTag(viewHolder);
        }
        viewHolder= (ViewHolder) convertView.getTag();
        fansInfor=mlist.get(position);
        viewHolder.iv_head.setImageResource(fansInfor.getHead());
        viewHolder.tv_name.setText(fansInfor.getName());
        viewHolder.tv_motto.setText(fansInfor.getMotto());
        viewHolder.tv_attention.setText(fansInfor.getAttention());
        return convertView;
    }
    class ViewHolder{
        ImageView iv_head;
        TextView  tv_name;
        TextView  tv_motto;
        TextView  tv_attention;
    }
}
