package com.example.anzhuo.myapplication.Abapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anzhuo.myapplication.Infor.ContributeInfor;
import com.example.anzhuo.myapplication.R;

import java.util.List;

/**
 * Created by anzhuo on 2016/9/23.
 */
public class ContributeAbapter extends BaseAdapter {
    List<ContributeInfor> mlist;
    Context mcontext;
    ContributeInfor contributeInfor;
    public ContributeAbapter(Context context,List<ContributeInfor> list){
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
            convertView= LayoutInflater.from(mcontext).inflate(R.layout.my_contribute_listview,null);
            viewHolder=new ViewHolder();
            viewHolder.tv_time= (TextView) convertView.findViewById(R.id.tv_time);
            viewHolder.tv_content= (TextView) convertView.findViewById(R.id.tv_content);
            viewHolder.iv_context= (ImageView) convertView.findViewById(R.id.iv_content);

            convertView.setTag(viewHolder);
        }
        viewHolder= (ViewHolder) convertView.getTag();
        contributeInfor=mlist.get(position);
        viewHolder.tv_time.setText(contributeInfor.getTime());
        viewHolder.tv_content.setText(contributeInfor.getContext());
        viewHolder.iv_context.setImageResource(contributeInfor.getIv_context());

        return convertView;
    }
    class ViewHolder{
        TextView tv_time;
        TextView tv_content;
        ImageView iv_context;
    }
}
