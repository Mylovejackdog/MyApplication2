package com.example.anzhuo.myapplication.Audit;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anzhuo.myapplication.Adapter.SdcardAdapter;
import com.example.anzhuo.myapplication.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by anzhuo on 2016/10/13.
 */
public class AuditSdardAtivity extends Activity {
    TextView tv_return;
    TextView tv_camera;
    GridView gv_photo;
    List<String> imagePath = new ArrayList<>();
    private static String[] imageFormatSet = new String[]{"jpg", "png", "gif"};
    SdcardAdapter sdcardAdapter;
    private static final int REQ_1=1;
    String filepath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audit_sdcard_layout);
        tv_return= (TextView) findViewById(R.id.tv_return);
        tv_camera= (TextView) findViewById(R.id.tv_camera);
        gv_photo= (GridView) findViewById(R.id.gv_photo);
        String sdpath = Environment.getExternalStorageDirectory() + "/";//获得SD卡的路径
        getFiles(sdpath);//调用getFiles()方法获取SD卡上的全部图片
        if (imagePath.size() < 1) {//如果不存在文件图片
            return;
        }
        sdcardAdapter=new SdcardAdapter(this,imagePath);
        gv_photo.setAdapter(sdcardAdapter);
        sdcardAdapter.notifyDataSetChanged();
        gv_photo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent=new Intent();
                String filepath=imagePath.get(i);
                intent.putExtra("key",filepath);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        tv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                filepath=Environment.getExternalStorageDirectory().getPath();
                Date date=new Date(System.currentTimeMillis());
                SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
                String time=format.format(date);
                filepath=filepath+"/"+"p"+time+".png";
                Uri uri=Uri.fromFile(new File(filepath));
                intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
                startActivityForResult(intent,REQ_1);
            }
        });
        tv_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AuditSdardAtivity.this,AuditContributeActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK&&requestCode==REQ_1){
            imagePath.add(filepath);
            sdcardAdapter.notifyDataSetChanged();
        }
    }
    private static boolean isImageFile(String path) {
        for (String format : imageFormatSet) {//遍历数组
            if (path.contains(format)) {//判断是否为合法的图片文件
                return true;
            }
        }
        return false;
    }
    private void getFiles(String url) {
        File files = new File(url);//创建文件对象
        File[] file = files.listFiles();
        try {
            for (File f : file) {//通过for循环遍历获取到的文件数组
                if(f.isDirectory()){//如果是目录，也就是文件夹
                    getFiles(f.getAbsolutePath());//递归调用
                }else {
                    if (isImageFile(f.getPath())) {//如果是图片文件
                        imagePath.add(f.getPath());//将文件的路径添加到List集合中

                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();//输出异常信息
        }
    }
}
