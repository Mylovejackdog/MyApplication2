package com.example.anzhuo.myapplication.My;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.anzhuo.myapplication.R;

/**
 * Created by anzhuo on 2016/9/13.
 */
public class MyrightActivity extends Activity  {
    public EditText et_pwd;
    public Button   bt_enter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_rightpwd_layout);
        et_pwd= (EditText) findViewById(R.id.et_pwd);
        Intent intent1=getIntent();
        et_pwd.setText(intent1.getExtras().getString("pwd"));
        bt_enter= (Button) findViewById(R.id.bt_enter);
        bt_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyrightActivity.this,Myenteractivity.class);
                startActivity(intent);
                finish();
    }
});
    }
}
