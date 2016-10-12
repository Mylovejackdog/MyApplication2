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
import java.net.URI;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by anzhuo on 2016/10/11.
 */
public class MyheadphotoActivity extends Activity implements View.OnClickListener {
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESIZE_REQUEST_CODE = 2;

    private static final String IMAGE_FILE_NAME = "header.jpg";

    private ImageView mImageHeader;
     Bitmap bitmap;
    String img_url;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_headphoto_layout);
        setupViews();
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
     /*           Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
                galleryIntent.setType("image*//*");//图片
                startActivityForResult(galleryIntent, IMAGE_REQUEST_CODE);*/
                break;
            case R.id.iv_xiangji:
              gototakephoto(v);
             /*   if (isSdcardExisting()) {
                    Intent cameraIntent = new Intent(
                            "android.media.action.IMAGE_CAPTURE");//拍照
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, getImageUri());
                    cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
                } else {
                    Toast.makeText(v.getContext(), "请插入sd卡", Toast.LENGTH_LONG)
                            .show();
                }*/
                break;
            case R.id.bt_save:
                 //  upload(img_url);
               uploadHead(v);
                break;
        }
    }
/*   @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        } else {
            switch (requestCode) {
                case IMAGE_REQUEST_CODE:
                    Uri originalUri=data.getData();//获取图片uri
                    resizeImage(originalUri);
                    //下面方法将获取的uri转为String类型哦！
                    String []imgs1={MediaStore.Images.Media.DATA};//将图片URI转换成存储路径
                    Cursor cursor=this.managedQuery(originalUri, imgs1, null, null, null);
                    int index=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    img_url=cursor.getString(index);
                    upload(img_url);
                    break;
                case CAMERA_REQUEST_CODE:
                    if (isSdcardExisting()) {
                        resizeImage(getImageUri());
                        String []imgs={MediaStore.Images.Media.DATA};//将图片URI转换成存储路径
                        Cursor cursor1=this.managedQuery(getImageUri(), imgs, null, null, null);
                        int index1=cursor1.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        cursor1.moveToFirst();
                        img_url=cursor1.getString(index1);
                        upload(img_url);
                    } else {
                        Toast.makeText(MyheadphotoActivity.this, "未找到存储卡，无法存储照片！",
                                Toast.LENGTH_LONG).show();
                    }
                    break;

                case RESIZE_REQUEST_CODE:
                    if (data != null) {
                        showResizeImage(data);
                    }
                    break;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }*/
  /*  private boolean isSdcardExisting() {//判断SD卡是否存在
        final String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }
    public void resizeImage(Uri uri) {//重塑图片大小
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image*//*");
        intent.putExtra("crop", "true");//可以裁剪
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESIZE_REQUEST_CODE);
    }
    private void showResizeImage(Intent data) {//显示图片
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(photo);
            mImageHeader.setImageDrawable(drawable);
        }
    }*/
 /*   private Uri getImageUri() {//获取路径
        return Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                IMAGE_FILE_NAME));
    }*/
/*    private void upload(String imgpath){
        final BmobFile icon=new BmobFile(new File(Environment.getExternalStorageDirectory(),IMAGE_FILE_NAME));
        icon.upload( new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                 if (e==null){
                     UserInfo userInfo=new UserInfo();
                     userInfo.setHead(icon);
                     userInfo.signUp(new SaveListener<UserInfo>() {
                         @Override
                         public void done(UserInfo userInfo, BmobException e) {
                             if (e==null){
                                 Toast.makeText(MyheadphotoActivity.this,"上传成功",Toast.LENGTH_SHORT).show();
                             }else {
                                 Toast.makeText(MyheadphotoActivity.this,"上传失败",Toast.LENGTH_SHORT).show();
                             }
                         }
                     });
                 }
            }
        });
    }*/


    private void gotosystempic(View view){
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT,null);
        intent.setType("image/*");
        intent.putExtra("return_data", true);
        startActivityForResult(intent, 1);
    }
    private void gototakephoto(View view){
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==1&&resultCode==RESULT_OK){
            uri=data.getData();
            ContentResolver contentResolver=this.getContentResolver();
            try {
                bitmap= BitmapFactory.decodeStream(contentResolver.openInputStream(uri));
                System.out.println("the bmp toString:"+bitmap);
                mImageHeader.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else if (requestCode==2&&resultCode==RESULT_OK){
             bitmap= (Bitmap) data.getExtras().get("data");
            mImageHeader.setImageBitmap(bitmap);
        }
    }
   public void uploadHead(View view){
     /*   ByteArrayOutputStream output= new ByteArrayOutputStream();
        BitmapDrawable bitmapDrawable= (BitmapDrawable) mImageHeader.getDrawable();
        final Bitmap finalBm;
        finalBm= scaleTmg(bitmapDrawable.getBitmap(),300);
        finalBm.compress(Bitmap.CompressFormat.JPEG, 60, output);
        byte [] result=output.toByteArray();*/
        final BmobFile file=new BmobFile(new File(Environment.getExternalStorageDirectory(),IMAGE_FILE_NAME));
        file.upload(new UploadFileListener() {
           @Override
           public void done(BmobException e) {
               if (e==null){
                   UserInfo userInfo=new UserInfo();
                   userInfo.setHead(file);
                   userInfo.signUp(new SaveListener<UserInfo>() {
                       @Override
                       public void done(UserInfo userInfo, BmobException e) {
                           if (e==null){
                               Toast.makeText(MyheadphotoActivity.this,"上传成功",Toast.LENGTH_SHORT).show();
                           }else {
                               Toast.makeText(MyheadphotoActivity.this,"上传失败",Toast.LENGTH_SHORT).show();
                           }
                       }
                   });
               }
           }
       });

    }
/*    protected Bitmap scaleTmg(Bitmap bitmap,int newWidth){
        int width=bitmap.getWidth();
        int height=bitmap.getHeight();
        //计算缩放比例
        float scaleWidth=((float)newWidth)/width;
        //取得想要的缩放的martrix参数
        Matrix matrix=new Matrix();
        matrix.postScale(scaleWidth,scaleWidth);
        //得到新图片
        return Bitmap.createBitmap(bitmap,0,0,width,height,matrix,true);

    }*/
}
