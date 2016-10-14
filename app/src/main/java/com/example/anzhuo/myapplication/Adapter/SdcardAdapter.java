package com.example.anzhuo.myapplication.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.anzhuo.myapplication.R;

import java.util.List;

/**
 * Created by anzhuo on 2016/10/13.
 */
public class SdcardAdapter extends BaseAdapter {
    List<String> list;
    Context context;
    public SdcardAdapter(Context context,List<String> list){
        this.context=context;
        this.list=list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.audit_photo_layout,null);
            viewHolder=new ViewHolder();
            viewHolder.iv_sdcard= (ImageView) convertView.findViewById(R.id.iv_sdcard);
            viewHolder.iv_sdcard.setAdjustViewBounds(true);
            convertView.setTag(viewHolder);
        }
        viewHolder= (ViewHolder) convertView.getTag();
        Bitmap bitmap= BitmapFactory.decodeFile(list.get(position));
        viewHolder.iv_sdcard.setImageBitmap(bitmap);
        return convertView;
    }
    class ViewHolder{
        ImageView iv_sdcard;
    }
}
