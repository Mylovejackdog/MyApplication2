package com.example.anzhuo.myapplication.My;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.anzhuo.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by anzhuo on 2016/9/13.
 */
public class MyforgetActivity extends Activity implements View.OnClickListener {
    public ImageView iv_back;
    public EditText  et_user;
    public EditText  et_phone;
    public Button    bt_affirm;

    String user;
    String phone;
    String pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_forget_layout);
        iv_back= (ImageView) findViewById(R.id.iv_back);
        et_user= (EditText) findViewById(R.id.et_user);
        et_phone= (EditText) findViewById(R.id.et_phone);
        bt_affirm= (Button) findViewById(R.id.bt_affirm);

        iv_back.setOnClickListener(this);
        et_user.setOnClickListener(this);
        et_phone.setOnClickListener(this);
        bt_affirm.setOnClickListener(this);

        Bmob.initialize(this, "b5d2051a335bcca76cac2f60ddc09441");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                Intent intent=new Intent(MyforgetActivity.this,Myenteractivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.bt_affirm:
                alter();
                break;
        }
    }
    public  void alter(){
        final BmobQuery<Info> user=new BmobQuery<Info>();
        user.addWhereEqualTo("user", et_user.getText().toString());
        BmobQuery<Info> phone=new BmobQuery<Info>();
        phone.addWhereEqualTo("phone", et_phone.getText().toString());
        List<BmobQuery<Info>> query1=new ArrayList<BmobQuery<Info>>();
        query1.add(user);
        query1.add(phone);
        BmobQuery query=new BmobQuery("Info");
        query.and(query1);
       query.findObjectsByTable(new QueryListener<JSONArray>() {
           @Override
           public void done(JSONArray jsonArray, BmobException e) {
               if (e == null) {
                   try {
                       JSONObject object = jsonArray.getJSONObject(0);
                       pwd = object.getString("pwd");
                       Log.i("Lw", pwd);
                       Toast.makeText(MyforgetActivity.this,"查询成功",Toast.LENGTH_SHORT).show();
                       Intent intent=new Intent(MyforgetActivity.this,MyrightActivity.class);
                       intent.putExtra("pwd",pwd);
                       startActivity(intent);
                       finish();
                   } catch (JSONException e1) {
                       e1.printStackTrace();
                   }
               }else {
                   Toast.makeText(MyforgetActivity.this,"查询失败，请重新输入账号或电话",Toast.LENGTH_SHORT).show();
               }
           }
       });

    }
}
