package com.example.anzhuo.myapplication.Abapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anzhuo.myapplication.Infor.AttentionInfor;
import com.example.anzhuo.myapplication.R;

import java.util.List;

/**
 * Created by anzhuo on 2016/9/23.
 */
public class AttentionAbapter extends BaseAdapter{
    List<AttentionInfor> mlist;
    Context mcontext;
    AttentionInfor attentionInfor;
    public AttentionAbapter(Context context,List<AttentionInfor> list){
           this.mlist=list;
        this.mcontext=context;
    }
    @Override
    public int getCount() {
        return mlist==null ? 0:mlist.size();
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
            convertView= LayoutInflater.from(mcontext).inflate(R.layout.attention_listview_layout,null);
            viewHolder=new ViewHolder();
            viewHolder.iv_head= (ImageView) convertView.findViewById(R.id.iv_head);
            viewHolder.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_motto= (TextView) convertView.findViewById(R.id.tv_motto);
            viewHolder.tv_attention= (TextView) convertView.findViewById(R.id.tv_attention);

            convertView.setTag(viewHolder);
        }
        viewHolder= (ViewHolder) convertView.getTag();
        attentionInfor=mlist.get(position);
        viewHolder.iv_head.setImageResource(attentionInfor.getHead());
        viewHolder.tv_name.setText(attentionInfor.getName());
        viewHolder.tv_motto.setText(attentionInfor.getMotto());
        viewHolder.tv_attention.setText(attentionInfor.getAttention());

        return convertView;
    }
    class ViewHolder{
        ImageView iv_head;
        TextView tv_name;
        TextView tv_motto;
        TextView tv_attention;
    }
}
