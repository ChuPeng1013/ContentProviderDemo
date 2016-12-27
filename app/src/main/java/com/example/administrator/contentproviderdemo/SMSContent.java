package com.example.administrator.contentproviderdemo;

/**
 * Created by ChuPeng on 2016/12/26.
 */

public class SMSContent
{
    //地址
    private String address;
    //内容
    private String body;
    //时间
    private String date;
    //类型
    private String type;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
