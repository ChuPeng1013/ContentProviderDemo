package com.example.administrator.contentproviderdemo;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by ChuPeng on 2017/1/4.
 */

public class MyContentObserver extends ContentObserver
{

    /**
     * Creates a content observer.
     *
     * @param handler The handler to run {@link #onChange} on, or null if none.
     */
    private Context context;
    public MyContentObserver(Handler handler, Context context)
    {
        super(handler);
        this.context = context;
    }

    /**
     * 当监听的Uri发生改变时，就会回调此方法
     *
     * @param selfChange 一般情况下为false，意义不是很大
     */
    public void onChange(boolean selfChange)
    {
        //查询发件箱中的短信
        Uri uri = Uri.parse("content://sms/outbox");
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        if(cursor != null)
        {
            //对查询结果进行遍历
            while(cursor.moveToNext())
            {
                StringBuilder sb = new StringBuilder();
                //发送地址
                sb.append("address=").append(cursor.getString(cursor.getColumnIndex("address"))).append(";  ");
                //短信内容
                sb.append("body=").append(cursor.getString(cursor.getColumnIndex("body"))).append(";  ");
                //是否查看
                sb.append("read=").append(cursor.getString(cursor.getColumnIndex("read"))).append(";  ");
                //发送时间
                sb.append("time=").append(cursor.getString(cursor.getColumnIndex("date"))).append("\n");
                Log.d("sms", sb.toString());
            }
            //在使用完以后需要关闭
            cursor.close();
        }
        else
        {
            Toast.makeText(context, "查询失败", Toast.LENGTH_SHORT).show();
        }
    }
}