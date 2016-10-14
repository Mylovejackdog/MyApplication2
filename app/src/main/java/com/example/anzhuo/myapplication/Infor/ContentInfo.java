package com.example.anzhuo.myapplication.Infor;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by anzhuo on 2016/10/13.
 */
public class ContentInfo extends BmobObject {
    private String content;
    private BmobFile bmobFile;
    private Integer type;

    public BmobFile getBmobFile() {
        return bmobFile;
    }

    public void setBmobFile(BmobFile bmobFile) {
        this.bmobFile = bmobFile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
