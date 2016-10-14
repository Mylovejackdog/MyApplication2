package com.example.anzhuo.myapplication.My;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.anzhuo.myapplication.Main.MainActivity;
import com.example.anzhuo.myapplication.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by anzhuo on 2016/10/11.
 */
public class MyheadphotoActivity extends Activity implements View.OnClickListener {
    private static final int PICTURE_FROM_CAMERA = 0X32;
    private static final int PICTURE_FROM_GALLERY = 0X34;

    private static final String IMAGE_FILE_NAME = "header.jpg";

    private ImageView mImageHeader;
     Bitmap bitmap;
    Uri uri;
    File file;//存储拍摄图片的文件
    String username;
    String phone;
    String pwd;
    String nickname;
    com.example.anzhuo.myapplication.My.UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_headphoto_layout);
        setupViews();

        Bmob.initialize(this, "b5d2051a335bcca76cac2f60ddc09441");

        Intent intent=getIntent();
        username=intent.getExtras().getString("username");
        phone=intent.getExtras().getString("phonenumber");
        pwd=intent.getExtras().getString("password");
        nickname=intent.getExtras().getString("nickname");

    }
    private void setupViews() {
        mImageHeader = (ImageView) findViewById(R.id.image_header);
        final ImageView selectBtn4= (ImageView) findViewById(R.id.iv_back);
        final ImageView selectBtn1 = (ImageView) findViewById(R.id.btn_selectimage);
        final ImageView selectBtn2 = (ImageView) findViewById(R.id.iv_xiangji);
        final Button selsctBtn3= (Button) findViewById(R.id.bt_save);
        selectBtn1.setOnClickListener(this);
        selectBtn2.setOnClickListener(this);
        selsctBtn3.setOnClickListener(this);
        selectBtn4.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                Intent intent1=new Intent(MyheadphotoActivity.this,Myheadactivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.btn_selectimage:
              gotosystempic(v);
                break;
            case R.id.iv_xiangji:
              gototakephoto(v);
                break;
            case R.id.bt_save:
               uploadHead(v);
                break;
        }
    }
private void gotosystempic(View view){
        //设置启动相册的Action
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //设置类型
        intent.setType("image/*");
        //启动相册，这里使用有返回结果的启动
        startActivityForResult(intent, PICTURE_FROM_GALLERY);
    }
    private void gototakephoto(View view){
        //启动相机的Action
        Intent intent=new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        //文件的保存位置
        file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),
                System.currentTimeMillis() + ".jpg");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //设置图片拍摄后保存的位置
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        //启动相机，这里使用有返回结果的启动
        startActivityForResult(intent, PICTURE_FROM_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case PICTURE_FROM_CAMERA:
                    //这里对图片进行了压缩，因为有些手机拍摄的照片过大，无法显示到ImageView中，所以我们将图片近行了压缩然后在进行显示
                    ZipImage.zipImage(Uri.fromFile(file).getPath());
                    //将图片设置到ImageView中，这里使用setImageURI（）方法进行设置。
                    mImageHeader.setImageURI(Uri.fromFile(file));
                    break;
                case PICTURE_FROM_GALLERY:
                    //通过返回的data数据，获取图片的路径信息，但是这个路径是Uri的。
                    uri =data.getData();
                    ContentResolver contentResolver=this.getContentResolver();
                    try {
                        bitmap=BitmapFactory.decodeStream(contentResolver.openInputStream(uri));
                        //把bitmap写成file文件
                        file = new File("/sdcard/myFolder");
                        if (!file.exists())
                            file.mkdir();
                        file = new File("/sdcard/temp.jpg".trim());
                        String fileName = file.getName();
                        String mName = fileName.substring(0, fileName.lastIndexOf("."));
                        String sName = fileName.substring(fileName.lastIndexOf("."));
                        // /sdcard/myFolder/temp_cropped.jpg
                        String newFilePath = "/sdcard/myFolder" + "/" + mName + "_cropped" + sName;
                        file = new File(newFilePath);
                        try {
                            file.createNewFile();
                            FileOutputStream fos = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fos);
                            fos.flush();
                            fos.close();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        //  System.out.println("the bmp toString:"+bitmap);
                        Log.i("LW", "哈哈：" + bitmap);
                        mImageHeader.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    public void uploadHead(View view) {

        final BmobFile bmobFile = new BmobFile(file);
        bmobFile.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Log.i("LW", "123");
                    UserInfo userInfo = new UserInfo();
                    userInfo.setUsername(username);
                    userInfo.setNickname(nickname);
                    userInfo.setPhoneNumber(phone);
                    userInfo.setPassword(pwd);
                    userInfo.setHead(bmobFile);
                    userInfo.signUp(new SaveListener<UserInfo>() {
                        @Override
                        public void done(UserInfo userInfo, BmobException e) {
                            if (e==null){
                                Log.i("LW", "12345");
                                Toast.makeText(MyheadphotoActivity.this,"注册成功："+"账号："+username,Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(MyheadphotoActivity.this,Myenteractivity.class);
                                startActivity(intent);
                                finish();
                            }else {
                                    Toast.makeText(MyheadphotoActivity.this, "注册失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                Intent intenta=new Intent(MyheadphotoActivity.this,MyuserActivity.class);
                                startActivity(intenta);
                                finish();
                            }
                        }
                    });
                }else {
                    Log.i("LW", "1234");
                    Toast.makeText(MyheadphotoActivity.this,"上传失败",Toast.LENGTH_SHORT).show();
                    Intent intentb=new Intent(MyheadphotoActivity.this,MyuserActivity.class);
                    startActivity(intentb);
                    finish();
                }
            }
        });
    }
     /*  userInfo=new com.example.anzhuo.myapplication.My.UserInfo();
        Log.i("LW", bmobFile + "");
        userInfo.setHead(bmobFile);
        userInfo=BmobUser.getCurrentUser(com.example.anzhuo.myapplication.My.UserInfo.class);
     //   String id= (String) BmobUser.getObjectByKey("ObjectId");
        userInfo.update(userInfo.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Log.i("LW", "更新成功");
                    Intent intent = new Intent(MyheadphotoActivity.this, Myactivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });*/
}
