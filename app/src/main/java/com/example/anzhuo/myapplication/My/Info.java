package com.example.anzhuo.myapplication.My;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;


/**
 * Created by anzhuo on 2016/9/18.
 */
public class Info extends BmobUser {
    private String user;
    private String pwd;
    private String phone;
    private BmobFile head;
    private String name;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BmobFile getHead() {
        return head;
    }

    public void setHead(BmobFile head) {
        this.head = head;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
