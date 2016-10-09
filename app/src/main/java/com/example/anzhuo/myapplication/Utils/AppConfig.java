package com.example.anzhuo.myapplication.Utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by anzhuo on 2016/9/29.
 */
public class AppConfig {
    private SharedPreferences innerConfig;
    private static final String KEY_NIGHT_MODE_SWITCH="night_theme";

    public AppConfig(Context context){
        innerConfig=context.getSharedPreferences("app_config",Application.MODE_PRIVATE);
    }
    public boolean isNighTheme(){
        return  innerConfig.getBoolean(KEY_NIGHT_MODE_SWITCH ,false);
    }
    public void setNightTheme(boolean on){
        SharedPreferences.Editor editor=innerConfig.edit();
        editor.putBoolean(KEY_NIGHT_MODE_SWITCH ,on);
        editor.commit();
    }
    public void  clear(){
        SharedPreferences.Editor editor=innerConfig.edit();
        editor.clear();
        editor.commit();
    }

}
