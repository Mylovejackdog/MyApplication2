package com.example.anzhuo.myapplication.Home;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.example.anzhuo.myapplication.R;

/**
 * Created by anzhuo on 2016/10/13.
 */
public class ScreenShowDialog extends Dialog{

    public ScreenShowDialog(Context context) {
        super(context, R.style.ImageloadingDialogStyle);
    }

    public ScreenShowDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_imageloading);
    }
}
