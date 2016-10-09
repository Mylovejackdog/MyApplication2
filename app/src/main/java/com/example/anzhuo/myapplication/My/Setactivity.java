package com.example.anzhuo.myapplication.My;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anzhuo.myapplication.R;
import com.example.anzhuo.myapplication.Utils.MainApplication;
import com.leaking.slideswitch.SlideSwitch;

/**
 * Created by anzhuo on 2016/9/12.
 */
public class Setactivity extends Activity {
    public ImageView iv_back;
    LinearLayout eliminate, examine, contact, user;
    TextView tv_cache;
    com.leaking.slideswitch.SlideSwitch sw_mode, sw_play, sw_auto, sw_push;
    private boolean isNight = false;
    RadioGroup rg_group;
    RadioButton rb_large,rb_middle,rb_small;

  //  File file=new File(Environment.getExternalStorageDirectory().getPath()+File.separator+"");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //夜间模式设置
       if (MainApplication.appConfig.isNighTheme()){
            Setactivity.this.setTheme(R.style.NightTheme);
            isNight=true;
        }else {
            Setactivity.this.setTheme(R.style.DayTheme);
            isNight=false;
        }
        setContentView(R.layout.set_layout);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        eliminate = (LinearLayout) findViewById(R.id.eliminate);
        examine = (LinearLayout) findViewById(R.id.examine);
        contact = (LinearLayout) findViewById(R.id.contact);
        user = (LinearLayout) findViewById(R.id.user);
        sw_mode = (SlideSwitch) findViewById(R.id.sw_mode);
        sw_play = (SlideSwitch) findViewById(R.id.sw_play);
        sw_auto = (SlideSwitch) findViewById(R.id.sw_auto);
        sw_push = (SlideSwitch) findViewById(R.id.sw_push);
        tv_cache= (TextView) findViewById(R.id.tv_cache);
        rg_group= (RadioGroup) findViewById(R.id.rg_group);
        rb_large= (RadioButton) findViewById(R.id.rb_large);
        rb_middle= (RadioButton) findViewById(R.id.rb_middle);
        rb_small= (RadioButton) findViewById(R.id.rb_small);
        //缓存大小
      //  tv_cache.setText(getFormatSize(file));

//返回键
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Setactivity.this, Myactivity.class);
                startActivity(intent);
                finish();
            }
        });
        //清除
        eliminate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Setactivity.this);
                builder.setTitle("清除缓存");
                builder.setMessage("点击确认后，你的收藏信息·历史记录将清除，确定要清除缓存吗？");
                builder.setIcon(R.drawable.icon_1_d);
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
              //          deleteFilesByDirectory(file);
                        Toast.makeText(Setactivity.this, "清除成功！", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        //检查新版本
        examine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Setactivity.this, "目前还没有新版本", Toast.LENGTH_SHORT).show();
            }
        });
        //联系我们
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Setactivity.this, "官方QQ:171835422", Toast.LENGTH_LONG).show();
            }
        });
        //用户协议
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Setactivity.this, "版本测试中，尚未开发相关协议", Toast.LENGTH_SHORT).show();
            }
        });
        //夜间模式
        sw_mode.setSlideListener(new SlideSwitch.SlideListener() {
            @Override
            public void open() {
                Toast.makeText(Setactivity.this, "开启了夜间模式", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void close() {

                Toast.makeText(Setactivity.this, "关闭了夜间模式", Toast.LENGTH_SHORT).show();
            }
        });
        //字体大小的设置
        rg_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Intent jumpIntent=new Intent(Setactivity.this,Myactivity.class);
                switch (checkedId){
                    case R.id.rb_large:
                        jumpIntent.putExtra("textsize", 3);
                        startActivity(jumpIntent);
                        break;
                    case R.id.rb_middle:
                        jumpIntent.putExtra("textsize", 2);
                        startActivity(jumpIntent);
                        break;
                    case R.id.rb_small:
                        jumpIntent.putExtra("textsize", 1);
                        startActivity(jumpIntent);
                        break;
                }
            }
        });

}
    //清除缓存
   /* public String deleteFilesByDirectory(File file){
        String J=getFormatSize(file);
        File[] filelist=file.listFiles();
        for (int i=0;i<filelist.length;i++){
            filelist[i].delete();
        }
        return J;
    }*/
    //缓存大小的计算方法
 /*   public String getFormatSize(File file){
        long size=0;
        File[] filelist=file.listFiles();
        for (int i=0;i<filelist.length;i++){
            size=size+filelist[i].length();
        }
        long a=size;
        if (a==0){
           return a+"B";
        }else  if (a<1024){
            return a+"B";
        }else if (a<1024*1024){
            return a/1024+"KB";
        }else if (a<1024*1024*1024){
            return a/(1204*1024)+"MB";
        }else {
            return a/(1024*1024*1024)+"G";
        }
    }*/
}