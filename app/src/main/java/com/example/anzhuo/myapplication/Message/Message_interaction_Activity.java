package com.example.anzhuo.myapplication.Message;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.anzhuo.myapplication.R;

import java.util.AbstractCollection;

/**
 * Created by anzhuo on 2016/9/21.
 */
public class Message_interaction_Activity extends Activity {
    public ImageView iv_back;
    public TextView tv_empty;
    public ListView lv_message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_interaction);
        iv_back= (ImageView) findViewById(R.id.iv_back);
        tv_empty= (TextView) findViewById(R.id.tv_empty);
        lv_message= (ListView) findViewById(R.id.lv_message);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
