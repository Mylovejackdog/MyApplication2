package com.example.anzhuo.myapplication.My;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anzhuo.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by anzhuo on 2016/9/13.
 */
public class MyuserActivity extends Activity implements View.OnClickListener {
    public ImageView iv_back;
    public EditText  et_user;
    public EditText  et_phone;
    public EditText  et_pwd;
    public EditText  et_infopwd;
    public TextView  tv_code;
    public EditText  et_input;
    public Button    bt_enter;
    private int START = 0;
    String phone;
    String user;
    String input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_user_layout);
        Bmob.initialize(this, "b5d2051a335bcca76cac2f60ddc09441");
        iv_back= (ImageView) findViewById(R.id.iv_back);
        et_user= (EditText) findViewById(R.id.et_user);
        et_phone= (EditText) findViewById(R.id.et_phone);
        et_pwd= (EditText) findViewById(R.id.et_pwd);
        et_infopwd= (EditText) findViewById(R.id.et_infopwd);
        et_input= (EditText) findViewById(R.id.et_input);
        tv_code= (TextView) findViewById(R.id.tv_code);
        bt_enter= (Button) findViewById(R.id.bt_enter);


        iv_back.setOnClickListener(this);
        et_user.setOnClickListener(this);
        et_phone.setOnClickListener(this);
        et_pwd.setOnClickListener(this);
        et_infopwd.setOnClickListener(this);
        et_input.setOnClickListener(this);
        tv_code.setOnClickListener(this);
        bt_enter.setOnClickListener(this);




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                Intent intent=new Intent(MyuserActivity.this,Myenteractivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_code:
                tv_code.setText(random());
                break;
            case R.id.bt_enter:
                input=et_input.getText().toString();
                BmobQuery query=new BmobQuery("Info");
                query.findObjectsByTable(new QueryListener<JSONArray>() {
                    @Override
                    public void done(JSONArray jsonArray, BmobException e) {
                        if (e == null) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                        JSONObject object = jsonArray.getJSONObject(i);
                                        phone = object.getString("phone");
                                        user = object.getString("user");
                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                }
                            }
                              if (et_user.getText().toString().equals(user)||et_phone.getText().toString().equals(phone)) {
                                    Log.i("info", "1237895645"+ "");
                                    Toast.makeText(MyuserActivity.this, "该账号已存在,请重新输入账号", Toast.LENGTH_SHORT).show();
                                    START = 1;
                                }else {
                                  if (!et_user.getText().toString().equals(user)&&!et_phone.getText().toString().equals(phone)){
                                    Info info = new Info();
                                    info.setUser(et_user.getText().toString());
                                    info.setPwd(et_pwd.getText().toString());
                                    info.setPhone(et_phone.getText().toString());
                                    info.save(new SaveListener<String>() {
                                        @Override
                                        public void done(String s, BmobException e) {
                                            if (e == null) {
                                                if (et_pwd.getText().toString().equals(et_infopwd.getText().toString()) && input.equals(tv_code.getText())) {
                                                    Log.i("info", "12335445" + "");
                                                    Toast.makeText(MyuserActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                } else {
                                                    Log.i("info", "123456" + "");
                                                    Toast.makeText(MyuserActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                Log.i("info", "123" + "");
                                                Toast.makeText(MyuserActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                              }

                        }
                    }
        });


                break;
    }
    }
    //随机验证码
    public static String random() {
        int count = 6;//验证码长度
        char start = '0';//从0开始
        char end = '9';//9结束
        Random rnd = new Random();
        char[] result = new char[count];
        int len = end - start + 1;
        while (count-- > 0) {
            result[count] = (char) (rnd.nextInt(len) + start);
        }
        return new String(result);
    }
}
