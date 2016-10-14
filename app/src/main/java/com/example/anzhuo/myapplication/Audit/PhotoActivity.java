package com.example.anzhuo.myapplication.Audit;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.anzhuo.myapplication.R;

/**
 * Created by anzhuo on 2016/10/13.
 */
public class PhotoActivity extends Activity{
    ImageView iv_sdcardpic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sdcardphoto_layout);
        iv_sdcardpic= (ImageView) findViewById(R.id.iv_sdcardpic);
        Intent intent=getIntent();
        String pic=intent.getExtras().getString("ket");
        Bitmap bitmap= BitmapFactory.decodeFile(pic);
        iv_sdcardpic.setImageBitmap(bitmap);
        iv_sdcardpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent();
                finish();
            }
        });
    }


}
