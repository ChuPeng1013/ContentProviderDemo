package com.example.administrator.contentproviderdemo;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private Button readContact;//读取联系人
    private Button queryContact;//查找联系人
    private Button insertContact;//新增联系人
    private Button readSMS;//读取信息
    private Button insertSMS;//新增信息
    private List<Contacts> listContacts;
    private List<SMSContent> listSMSContent;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        readContact = (Button) findViewById(R.id.readContact);
        queryContact = (Button) findViewById(R.id.queryContact);
        insertContact = (Button) findViewById(R.id.insertContact);
        readSMS = (Button) findViewById(R.id.readSMS);
        insertSMS = (Button) findViewById(R.id.insertSMS);

        readContact.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                ReadContact rc = new ReadContact(MainActivity.this);
                listContacts = rc.getContact();
                for (int i = 0; i < listContacts.size(); i++) {
                    Log.d("123", listContacts.get(i).getName());
                    Log.d("123", listContacts.get(i).getNumber());
                    Log.d("123", "-------------------------------------");
                }
            }
        });

        queryContact.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                ReadContact rc = new ReadContact(MainActivity.this);
                String name = rc.queryContact("123456");
                Log.d("123", name);
            }
        });

        insertContact.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                ReadContact rc = new ReadContact(MainActivity.this);
                if (rc.insertContact("abc", "123"))
                {
                    Toast.makeText(MainActivity.this, "插入成功", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "插入失败", Toast.LENGTH_SHORT).show();
                }

            }
        });

        readSMS.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                ReadSMS rs = new ReadSMS(MainActivity.this);
                listSMSContent = rs.getSMS();
                for (int i = 0; i < listSMSContent.size(); i++)
                {
                    Log.d("123", "地址：" + listSMSContent.get(i).getAddress());
                    Log.d("123", "时间：" + listSMSContent.get(i).getDate());
                    Log.d("123", "类型：" + listSMSContent.get(i).getType());
                    Log.d("123", "内容：" + listSMSContent.get(i).getBody());
                    Log.d("123", "------------------------------------------------------------------");
                }
            }
        });

        insertSMS.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                ReadSMS rs = new ReadSMS(MainActivity.this);
                //address, type, date, body
                if (rs.insertSMS("95555", 1, String.valueOf(System.currentTimeMillis()), "尊敬的XXX，您的尾号为986的工行卡，收入人民币50,000,000元"))
                {
                    Toast.makeText(MainActivity.this, "插入成功", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "插入失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
