package com.example.anzhuo.myapplication.Home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.anzhuo.myapplication.DataInfo.RecommendBmobInfo;
import com.example.anzhuo.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anzhuo on 2016/10/13.
 */
public class ImageShowerActivity  extends Activity{
    ImageView big_pic;
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageshower_layout);
         big_pic= (ImageView) findViewById(R.id.big_pic);
        final Intent intent=getIntent();

      //  Glide.with(this).load("http://www.zbjuran.com/uploads/allimg/160809/7-160P9152QQ61.gif").asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(big_pic);
        final ScreenShowDialog dialog = new ScreenShowDialog(this);
        dialog.show();
        // 两秒后关闭后dialog
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Glide.with(ImageShowerActivity.this).load(intent.getExtras().getString("contentsrc")) .asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(big_pic);
                dialog.dismiss();
            }
        }, 1000 * 2);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        finish();
        return true;
    }
}
