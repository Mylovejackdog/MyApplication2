package com.example.anzhuo.myapplication.My;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anzhuo.myapplication.R;
import com.example.anzhuo.myapplication.Utils.MainApplication;
import com.example.anzhuo.myapplication.Utils.Util;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQAuth;
import com.tencent.stat.StatGameUser;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by anzhuo on 2016/9/13.
 */
public class Myenteractivity extends Activity {
    private static final String TAG = Myenteractivity.class.getName();
    public static String mAppid;
 //   private TextView mUserInfo;
 //   private ImageView mUserLogo;
    private Button mNewLoginButton;

    private UserInfo mInfo;
    private Tencent mTencent;
    private final String APP_ID = "1105704796";// 测试时使用，真正发布的时候要换成自己的APP_ID

    public ImageView iv_back;
    public EditText et_user;
    public EditText  et_pwd;
    public Button  bt_enter;
    public TextView  tv_forget;
    public TextView  tv_register;
    public LinearLayout ll_qqenter;

    String user;
    String pwd;
    com.example.anzhuo.myapplication.My.UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "-->onCreate");
        // 固定竖屏
        setContentView(R.layout.my_enter_layout);
        initViews();
      //  Bmob.initialize(this, "b5d2051a335bcca76cac2f60ddc09441");
        iv_back= (ImageView) findViewById(R.id.iv_back);
        et_user= (EditText) findViewById(R.id.et_user);
        et_pwd= (EditText) findViewById(R.id.et_pwd);
        bt_enter= (Button) findViewById(R.id.bt_enter);
        tv_forget= (TextView) findViewById(R.id.tv_forget);
        tv_register= (TextView) findViewById(R.id.tv_register);
        ll_qqenter= (LinearLayout) findViewById(R.id.ll_qqenter);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(Myenteractivity.this,Myactivity.class);
                startActivity(intent1);
                finish();
            }
        });

        bt_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("LW","12");
                userInfo=new com.example.anzhuo.myapplication.My.UserInfo();
                userInfo.setUsername(et_user.getText().toString());
                userInfo.setPassword(et_pwd.getText().toString());
                userInfo.login(new SaveListener<com.example.anzhuo.myapplication.My.UserInfo>() {
                    @Override
                    public void done(com.example.anzhuo.myapplication.My.UserInfo userInfo, BmobException e) {
                        if (e == null) {
                            Toast.makeText(Myenteractivity.this, "登入成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Myenteractivity.this, Myactivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(Myenteractivity.this, "登入失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        tv_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(Myenteractivity.this,MyforgetActivity.class);
                startActivity(intent2);
            }
        });
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Myenteractivity.this, MyuserActivity.class);
                startActivity(intent);
            }
        });

    }

@Override
protected void onStart() {
    Log.d(TAG, "-->onStart");
    final Context context=Myenteractivity.this;
    final Context ctxContext=context.getApplicationContext();
    mAppid=APP_ID;
    MainApplication.mQQAuth= QQAuth.createInstance(mAppid,ctxContext);
    mTencent= Tencent.createInstance(mAppid,Myenteractivity.this);
    super.onStart();
}

    private void initViews() {
        mNewLoginButton= (Button) findViewById(R.id.new_login_btn);
        LinearLayout linearLayout= (LinearLayout) findViewById(R.id.main_container);
        View.OnClickListener listener = new NewClickListener();
        for (int i=0;i<linearLayout.getChildCount();i++){
            View view=linearLayout.getChildAt(i);
            if (view instanceof Button){
                view.setOnClickListener(listener);
            }
        }
    //    mUserInfo= (TextView) findViewById(R.id.user_nickname);
    //    mUserLogo= (ImageView) findViewById(R.id.user_logo);
    }
    /*private void updateLoginButton(){
        if (MainApplication.mQQAuth!=null&&MainApplication.mQQAuth.isSessionValid()){
            mNewLoginButton.setTextColor(Color.RED);
            mNewLoginButton.setText(R.string.qq_logout);
        }else {
            mNewLoginButton.setTextColor(Color.BLUE);
            mNewLoginButton.setText(R.string.qq_login);
        }
    }*/
/*  private void updateUserInfo(){
        if (MainApplication.mQQAuth!=null&&MainApplication.mQQAuth.isSessionValid()){

        }IUiListener listener=new IUiListener() {
                @Override
                public void onError(UiError uiError) {
                }
                @Override
                public void onComplete(final Object response) {
                    Message msg = new Message();
                    msg.obj = response;
                    msg.what = 0;
                    mHandler.sendMessage(msg);
                    new Thread(){
                        @Override
                        public void run() {
                            JSONObject josn= (JSONObject) response;
                            if (josn.has("figureurl")){
                                Bitmap bitmap=null;
                                try {
                                    bitmap=Util.getbitmap(josn.getString("figureurl_qq_2"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Message msg=new Message();
                                msg.obj=bitmap;
                                msg.what=1;
                                mHandler.sendMessage(msg);
                            }
                        }
                    }.start();
                }
                @Override
                public void onCancel() {
                }
            };
            mInfo=new UserInfo(this, MainApplication.mQQAuth.getQQToken());
            mInfo.getUserInfo(listener);
            mUserInfo.setText("");
            mUserInfo.setVisibility(View.GONE);
            mUserLogo.setVisibility(View.GONE);
        }

    Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==0){
                JSONObject response = (JSONObject) msg.obj;
                if (response.has("nickname")){
                    mUserInfo.setVisibility(View.VISIBLE);
                    try {
                        mUserInfo.setText(response.getString("nickname"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }else if (msg.what==1){
                Bitmap bitmap= (Bitmap) msg.obj;
                mUserLogo.setImageBitmap(bitmap);
                mUserLogo.setVisibility(View.VISIBLE);
            }

        }
    };*/
    private void onClickLogin() {
        if (!MainApplication.mQQAuth.isSessionValid()){
            IUiListener listener=new BaseUiListener(){
                protected void doComplete(JSONObject values) {
                    Toast.makeText(Myenteractivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                 //  updateUserInfo();
                 //  updateLoginButton();
                }
            };
             /**通过这句代码，SDK实现了QQ的登录，这个方法有三个参数，第一个参数是context上下文，第二个参数SCOPO 是一个String类型的字符串，表示一些权限
             官方文档中的说明：应用需要获得哪些API的权限，由“，”分隔。例如：SCOPE = “get_user_info,add_t”；所有权限用“all”
             第三个参数，是一个事件监听器，IUiListener接口的实例，这里用的是该接口的实现类 */
            MainApplication.mQQAuth.login(this, "all", listener);
            mTencent.login(this, "all", listener);
        }else {
            MainApplication.mQQAuth.logout(this);
            Toast.makeText(Myenteractivity.this,"456789",Toast.LENGTH_SHORT).show();
         //   updateUserInfo();
          //  updateLoginButton();
        }
    }
    public static boolean ready(Context context) {
        if (MainApplication.mQQAuth == null) {
            return false;
        }
        boolean ready = MainApplication.mQQAuth.isSessionValid() && MainApplication.mQQAuth.getQQToken().getOpenId() != null;
        if (!ready)
            Toast.makeText(context, "login and get openId first, please!", Toast.LENGTH_SHORT).show();
        return ready;

    }
    /**当自定义的监听器实现IUiListener接口后，必须要实现接口的三个方法，
     * onComplete  onCancel onError
     *分别表示第三方登录成功，取消 ，错误。*/
    private class BaseUiListener implements IUiListener {
        @Override
        public void onComplete(Object response) {
            Util.showResultDialog(Myenteractivity.this, response.toString(), "登录成功");
            Intent intent=new Intent(Myenteractivity.this,Myactivity.class);
            startActivity(intent);
            doComplete((JSONObject) response);
        }
        protected void doComplete(JSONObject values) {

        }
        @Override
        public void onError(UiError e) {
            Util.toastMessage(Myenteractivity.this, "onError: " + e.errorDetail);
            Util.dismissDialog();
        }
        @Override
        public void onCancel() {
            Util.toastMessage(Myenteractivity.this, "onCancel: ");
            Util.dismissDialog();
        }
    }
    class NewClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Context context=v.getContext();
            Class<?> cla=null;
            switch (v.getId()){
                case R.id.new_login_btn:
                    onClickLogin();
                    Toast.makeText(Myenteractivity.this,"正在获取账号",Toast.LENGTH_SHORT).show();
                    return;
            }if (cla!=null){
                Intent intent=new Intent(context,cla);
                Toast.makeText(Myenteractivity.this,"123",Toast.LENGTH_SHORT).show();
                context.startActivity(intent);
            }
        }
    }
}
