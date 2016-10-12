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
    public EditText  et_nickname;
    public EditText  et_pwd;
    public EditText  et_infopwd;
    public TextView  tv_code;
    public EditText  et_input;
    public Button    bt_enter;
    private int START = 0;
    String phone;
    String user;
    String input;
    UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_user_layout);
      //  Bmob.initialize(this, "b5d2051a335bcca76cac2f60ddc09441");
        userInfo=new UserInfo();
        iv_back= (ImageView) findViewById(R.id.iv_back);
        et_user= (EditText) findViewById(R.id.et_user);
        et_nickname= (EditText) findViewById(R.id.et_nickname);
        et_pwd= (EditText) findViewById(R.id.et_pwd);
        et_infopwd= (EditText) findViewById(R.id.et_infopwd);
        et_input= (EditText) findViewById(R.id.et_input);
        tv_code= (TextView) findViewById(R.id.tv_code);
        bt_enter= (Button) findViewById(R.id.bt_enter);

        iv_back.setOnClickListener(this);
        et_user.setOnClickListener(this);
        et_nickname.setOnClickListener(this);
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
                input=random();
                tv_code.setText(input);
                break;
            case R.id.bt_enter:
                if (et_user.getText().toString().equals("")||et_nickname.getText().toString().equals("")){
                    Log.i("LW", "123456");
                    Toast.makeText(MyuserActivity.this,"请输入账号或昵称！",Toast.LENGTH_SHORT).show();
                }else {
                    if (et_input.getText().toString().equals(input)){
                        if (et_user!=null&&et_infopwd.getText().toString().equals(et_pwd.getText().toString())){
                            Log.i("LW", "12345");
                            userInfo.setPassword(et_pwd.getText().toString());
                            userInfo.setUsername(et_user.getText().toString());
                            userInfo.setNickname(et_nickname.getText().toString());
                            userInfo.signUp(new SaveListener<UserInfo>() {
                                @Override
                                public void done(UserInfo userInfo, BmobException e) {
                                    if (e == null) {
                                        Log.i("LW", "1234");
                                        Intent intent = new Intent(MyuserActivity.this, Myenteractivity.class);
                                        startActivity(intent);
                                        Toast.makeText(MyuserActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        if (e.getMessage().toString().equals("user'" + et_user.getText().toString() + "'already taken")) {
                                            Log.i("LW", "123");
                                            Toast.makeText(MyuserActivity.this, "注册失败：该用户已存在", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Log.i("LW", "12");
                                            Toast.makeText(MyuserActivity.this, "注册失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            });
                        }else {
                            Toast.makeText(MyuserActivity.this,"请输入相同的密码！",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(MyuserActivity.this,"验证码不正确，请重新输入！",Toast.LENGTH_SHORT).show();
                    }
                }
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
