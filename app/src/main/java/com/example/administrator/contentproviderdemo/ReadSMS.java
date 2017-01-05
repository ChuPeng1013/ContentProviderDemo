package com.example.administrator.contentproviderdemo;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChuPeng on 2016/12/26.
 */

public class ReadSMS
{
    private Context context;
    private StringBuilder sb;
    private List<SMSContent> SMSList;
    public ReadSMS(Context context)
    {
        this.context = context;
        sb = new StringBuilder();
        SMSList = new ArrayList<SMSContent>();

    }
    //获取收件箱的哪些信息
    public List<SMSContent> getSMS()
    {
        String[] projection = new String[]
                {
                        "address",
                        "body",
                        "type",
                        "date"
                };
        try
        {

            Uri uri = Uri.parse("content://sms/");
            Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
            if (cursor.moveToFirst())
            {
                String address;
                String body;
                String date;
                String type;
                int phoneColumn = cursor.getColumnIndex("address");
                int bodyColumn = cursor.getColumnIndex("body");
                int dateColumn = cursor.getColumnIndex("date");
                int typeColumn = cursor.getColumnIndex("type");
                while(cursor.moveToNext())
                {
                    SMSContent content = new SMSContent();
                    address = cursor.getString(phoneColumn);
                    body = cursor.getString(bodyColumn);
                    date = cursor.getString(dateColumn);
                    type = cursor.getString(typeColumn);
                    content.setAddress(address);
                    content.setBody(body);
                    content.setDate(date);
                    content.setType(type);
                    SMSList.add(content);
                }
                cursor.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return SMSList;
    }

    //插入一条信息到收件箱中
    /*
    address:发件人地址(电话号码)
    type:信息类型(1是接收到的，2是已发出)
    date:日期
    body:/短消息内容
     */
    public boolean insertSMS(String address, int type, long date, String body)
    {
        try
        {
            Uri uri = Uri.parse("content://sms/");
            ContentResolver contentResolver = context.getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put("address", address);
            contentValues.put("type", type);
            contentValues.put("date", date);
            contentValues.put("body", body);
            contentResolver.insert(uri, contentValues);
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
}
