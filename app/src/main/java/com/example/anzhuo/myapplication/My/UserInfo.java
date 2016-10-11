package com.example.anzhuo.myapplication.My;

import cn.bmob.v3.BmobUser;

/**
 * Created by anzhuo on 2016/10/11.
 */
public class UserInfo extends BmobUser {
    private Integer age;;
    private String nickname;
    private boolean gender;
    private String phoneNumber;
    private String head;

    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }
}
