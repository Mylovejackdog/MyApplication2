package com.example.anzhuo.myapplication.Audit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anzhuo.myapplication.Infor.ContentInfo;
import com.example.anzhuo.myapplication.Main.MainActivity;
import com.example.anzhuo.myapplication.R;

import java.io.File;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by anzhuo on 2016/10/13.
 */
public class AuditContributeActivity extends Activity {
    ImageView iv_return;
    TextView tv_submit;
    EditText et_content;
    ImageView iv_sdcardphoto;
    ImageView iv_pic;
    ImageView iv_camera;
    TextView tv_num;
    ContentInfo contentInfo = new ContentInfo();
    private static final int REQ_2=2;
    private static final int REQ_1=1;
    String photopath;
    BmobFile bmobFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audit_contribute_layout);
        iv_return= (ImageView) findViewById(R.id.iv_return);
        tv_submit= (TextView) findViewById(R.id.tv_submit);
        et_content= (EditText) findViewById(R.id.et_content);
        iv_sdcardphoto= (ImageView) findViewById(R.id.iv_sdcardphoto);
        iv_pic= (ImageView) findViewById(R.id.iv_pic);
        iv_camera= (ImageView) findViewById(R.id.iv_camera);
        tv_num= (TextView) findViewById(R.id.tv_num);
        Bmob.initialize(this, "91b01d0762aeef4a44f5c599dd65e938");
        et_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(final CharSequence charSequence, int i, int i1, int i2) {
                if (i>=i1&&et_content.getText().toString()!=null&&!et_content.getText().toString().equals("")) {
                    tv_num.setVisibility(View.VISIBLE);
                    tv_num.setText("还能输入" + (300 - charSequence.length()) + "字");
                    if (charSequence.length()>=300){
                        Toast.makeText(AuditContributeActivity.this,"限制输入300个字",Toast.LENGTH_LONG).show();
                    }
                    if(bmobFile==null){
                        tv_submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                contentInfo.setContent(et_content.getText().toString());
                                contentInfo.setType(1);
                                contentInfo.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {
                                        if (e==null){
                                            Toast.makeText(AuditContributeActivity.this,"登陆成功", Toast.LENGTH_LONG).show();
                                            Intent intent=new Intent(AuditContributeActivity.this, MainActivity.class);
                                            startActivity(intent);
                                        }else {
                                            Toast.makeText(AuditContributeActivity.this,"登陆失败", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                        });
                    }else {
                        tv_submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                contentInfo.setContent(et_content.getText().toString());
                                bmobFile = new BmobFile(new File(photopath));
                                contentInfo.setBmobFile(bmobFile);
                                contentInfo.setType(2);
                                bmobFile.upload(new UploadFileListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null) {
                                            contentInfo.save(new SaveListener<String>() {
                                                @Override
                                                public void done(String s, BmobException e) {
                                                    if (e == null) {
                                                        Toast.makeText(AuditContributeActivity.this, "登陆成功", Toast.LENGTH_LONG).show();
                                                        Intent intent = new Intent(AuditContributeActivity.this, MainActivity.class);
                                                        startActivity(intent);
                                                    } else {
                                                        Toast.makeText(AuditContributeActivity.this, "登陆失败", Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });

                                        } else {
                                            Toast.makeText(AuditContributeActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });

                            }
                        });
                    }
                }else {
                    tv_submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(AuditContributeActivity.this,"请再多输入文字",Toast.LENGTH_LONG).show();
                        }
                    });
                    tv_num.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        /*iv_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                finish();
            }
        });*/
        iv_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AuditContributeActivity.this,AuditSdardAtivity.class);
                startActivityForResult(intent,REQ_2);
            }
        });
        iv_sdcardphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AuditContributeActivity.this,PhotoActivity.class);
                intent.putExtra("ket",photopath);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQ_2&&resultCode==RESULT_OK){
            if (data!=null){
                photopath= (String) data.getExtras().get("key");
                Bitmap bitmap= BitmapFactory.decodeFile(photopath);
                iv_sdcardphoto.setImageBitmap(bitmap);
            }
        }
    }

}
