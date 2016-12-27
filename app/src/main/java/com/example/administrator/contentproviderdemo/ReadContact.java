package com.example.administrator.contentproviderdemo;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChuPeng on 2016/12/26.
 */

public class ReadContact
{
    private String number;
    private String displayName;
    private Context context;
    private Contacts contacts;
    private List<Contacts> contactsList;
    public ReadContact(Context context)
    {
        this.context = context;
        contactsList = new ArrayList<Contacts>();
    }
    //读取通讯录
    public List<Contacts> getContact()
    {
        try
        {
            //使用ContentResolver查找联系人数据
            Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            //遍历查询结果，获取系统中所有的联系人
            while (cursor.moveToNext())
            {
                contacts = new Contacts();
                //获取联系人姓名
                displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                contacts.setName(displayName);
                //获取联系人手机号码
                number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                contacts.setNumber(number);
                contactsList.add(contacts);
            }
            cursor.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return contactsList;
    }
    //查询指定号码联系人
    public String queryContact(String num)
    {
        Cursor cursor = null;
        try
        {
            Uri uri = Uri.parse("content://com.android.contacts/data/phones/filter/" + num);
            ContentResolver contentResolver = context.getContentResolver();
            cursor = contentResolver.query(uri, new String[]{"display_name"}, null, null, null);
            if(cursor.moveToFirst())
            {
                String name = cursor.getString(0);
                cursor.close();
                return name;
            }
            cursor.close();
            return "";
        }
        catch(Exception e)
        {
            cursor.close();
            return "";
        }
    }
    //插入联系人到通讯录
    public boolean insertContact(String name, String number)
    {
        try
        {
            Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
            Uri dataUri = Uri.parse("content://com.android.contacts/data");
            ContentResolver contentResolver = context.getContentResolver();
            ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>();
            ContentProviderOperation op1 = ContentProviderOperation.newInsert(uri)
                    .withValue("account_name", null)
                    .build();
            operations.add(op1);
            //加入姓名
            ContentProviderOperation op2 = ContentProviderOperation.newInsert(dataUri)
                    .withValueBackReference("raw_contact_id", 0)
                    .withValue("mimetype", "vnd.android.cursor.item/name")
                    .withValue("data2", name)
                    .build();
            operations.add(op2);
            //加入电话号码
            ContentProviderOperation op3 = ContentProviderOperation.newInsert(dataUri)
                    .withValueBackReference("raw_contact_id", 0)
                    .withValue("mimetype", "vnd.android.cursor.item/phone_v2")
                    .withValue("data1", number)
                    .withValue("data2", "2")
                    .build();
            operations.add(op3);
            //将上述内容添加到电话号码中
            contentResolver.applyBatch("com.android.contacts", operations);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }

    }
}
