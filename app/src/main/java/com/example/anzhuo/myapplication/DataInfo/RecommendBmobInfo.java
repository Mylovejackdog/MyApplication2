package com.example.anzhuo.myapplication.DataInfo;

import cn.bmob.v3.BmobObject;

/**
 * Created by anzhuo on 2016/10/10.
 */
public class RecommendBmobInfo extends BmobObject {

    private String id;
    private String title;
    private String img;
    private String creat_time;
    private String pic;
    private String content;
    private Integer good_count;
    private Integer bad_count;
    private Integer comment_count;
    private Integer type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCreat_time() {
        return creat_time;
    }

    public void setCreat_time(String creat_time) {
        this.creat_time = creat_time;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Integer getGood_count() {
        return good_count;
    }

    public void setGood_count(Integer good_count) {
        this.good_count = good_count;
    }

    public Integer getBad_count() {
        return bad_count;
    }

    public void setBad_count(Integer bad_count) {
        this.bad_count = bad_count;
    }

    public Integer getComment_count() {
        return comment_count;
    }

    public void setComment_count(Integer comment_count) {
        this.comment_count = comment_count;
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
