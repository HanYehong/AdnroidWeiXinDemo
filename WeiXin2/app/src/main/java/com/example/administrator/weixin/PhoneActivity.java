package com.example.administrator.weixin;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.system.StructUtsname;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.QuickContactBadge;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class PhoneActivity extends AppCompatActivity {
    private ListView phoneList;
    private Button add;
    private final int tubiaoMargin = 35;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_phone);

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        init();

        /**
         * 得到联系人的姓名和电话
         */
        final List<HashMap<String,String>> peoples = getPeople();

        BaseAdapter adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return peoples.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                LinearLayout linearLayout = getContactsLayout(peoples.get(position).get("name"),
                        peoples.get(position).get("phone"));
                return linearLayout;
            }
        };

        phoneList.setAdapter(adapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addContactView(v);
            }
        });

    }

    /**
     * 获得手机联系人
     * @return list为所有联系人总数，hashmap为每个联系人的属性：name，phone
     */
    private List<HashMap<String,String>> getPeople() {
        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_CONTACTS)
                !=PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) this,
                    new String[]{android.Manifest.permission.READ_CONTACTS},
                    1);
        }

        if (ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_CONTACTS)
                !=PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) this,
                    new String[]{android.Manifest.permission.WRITE_CONTACTS},
                    1);
        }
        List<HashMap<String,String>> list = new ArrayList<>();
        Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null,
                null,null,null);
        while (cursor.moveToNext()){
            HashMap<String,String> hm = new HashMap<>();
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            hm.put("name",name);
            Cursor phone = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"="+contactId,null,null);
            if(phone.moveToNext()){
                String phoneNumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                hm.put("phone",phoneNumber);
            }
            list.add(hm);
            phone.close();
        }
        cursor.close();
        return list;
    }

    private void init() {
        this.phoneList = (ListView) findViewById(R.id.phoneList);
        this.add = (Button)findViewById(R.id.addContact);
    }

    private LinearLayout getContactsLayout(final String name_text, String phone_text){
        //最外层布局
        LinearLayout linearLayout = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(params);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        //联系人
        LinearLayout linearLayout_1 = new LinearLayout(this);
        linearLayout_1.setLayoutParams(params);
        linearLayout_1.setBackgroundColor(getResources().getColor(R.color.fontcolor));
        linearLayout_1.setOrientation(LinearLayout.HORIZONTAL);

        //图标
        ImageView tx = new ImageView(this);
        LinearLayout.LayoutParams params_tx = new LinearLayout.LayoutParams(160,160);
        params_tx.setMargins(tubiaoMargin,tubiaoMargin,tubiaoMargin,tubiaoMargin);
        tx.setLayoutParams(params_tx);
        tx.setImageDrawable(getResources().getDrawable(R.drawable.people));

        //姓名、电话的布局
        LinearLayout linearLayout_2 = new LinearLayout(this);
        LinearLayout.LayoutParams params_2 = new LinearLayout.LayoutParams(650,LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayout_2.setLayoutParams(params_2);
        linearLayout_2.setOrientation(LinearLayout.VERTICAL);

        //姓名
        TextView name = new TextView(this);
        LinearLayout.LayoutParams params_3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params_3.setMargins(30,47,0,10);
        name.setLayoutParams(params_3);
        /**
         * 设置姓名
         */
        name.setText(name_text);
        name.setTextSize(23);
        name.setTextColor(getResources().getColor(R.color.colorTextViewNormal));

        //电话
        TextView phone = new TextView(this);
        LinearLayout.LayoutParams params_4 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params_4.setMargins(30,20,0,0);
        phone.setLayoutParams(params_4);
        /**
         * 设置电话
         */
        phone.setText(phone_text);
        phone.setTextSize(15);
        phone.setTextColor(getResources().getColor(R.color.colorTextViewNormal));

        /**
         * <QuickContactBadge
         *         android:id="@+id/badge"
         *         android:layout_width="wrap_content"
         *         android:layout_height="wrap_content"
         *         android:src="@drawable/ic_launcher_foreground"/>
         */

        //拨打电话图标
        QuickContactBadge call = new QuickContactBadge(this);
        LinearLayout.LayoutParams params_call = new LinearLayout.LayoutParams(80,80);
        params_call.setMargins(0,80,0,0);
        call.setLayoutParams(params_call);
        call.setBackground(getResources().getDrawable(R.drawable.call));
        call.assignContactFromPhone(phone_text,false);

        //分割线
        View line = new View(this);
        LinearLayout.LayoutParams params_line = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,1);
        line.setLayoutParams(params_line);
        line.setBackgroundColor(getResources().getColor(R.color.littleGravy));

        linearLayout_1.addView(tx);
        linearLayout_2.addView(name);
        linearLayout_2.addView(phone);
        linearLayout_1.addView(linearLayout_2);
        linearLayout_1.addView(call);
        linearLayout.addView(linearLayout_1);
        linearLayout.addView(line);

        return linearLayout;
    }

    /**
     * 添加联系人对话框
     */
    public void addContactView(View source){
        final LinearLayout addForm = (LinearLayout)getLayoutInflater().inflate(R.layout.add_contacts, null);
        new AlertDialog.Builder(this)
                .setTitle("添加联系人")
                .setView(addForm)
                .setPositiveButton("添加", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //进行添加
                        EditText editText1 = (EditText)addForm.findViewById(R.id.addName);
                        EditText editText2 = (EditText)addForm.findViewById(R.id.addPhone);
                        if(editText1 == null || editText2 == null){
                            System.out.println("EditText ===== null");
                            return;
                        }
                        String name = editText1.getText().toString();
                        String phone = editText2.getText().toString();
                        ContentValues values = new ContentValues();
                        Uri rawContactUri = getContentResolver().insert(
                                ContactsContract.RawContacts.CONTENT_URI,values);
                        long rawContactId = ContentUris.parseId(rawContactUri);
                        values.clear();
                        values.put(ContactsContract.Data.RAW_CONTACT_ID,rawContactId);
                        values.put(ContactsContract.Data.MIMETYPE,ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
                        values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME,name);
                        getContentResolver().insert(ContactsContract.Data.CONTENT_URI,values);

                        values.clear();
                        values.put(ContactsContract.Data.RAW_CONTACT_ID,rawContactId);
                        values.put(ContactsContract.Data.MIMETYPE,ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                        values.put(ContactsContract.CommonDataKinds.Phone.NUMBER,phone);
                        values.put(ContactsContract.CommonDataKinds.Phone.TYPE,ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
                        getContentResolver().insert(ContactsContract.Data.CONTENT_URI,values);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //取消添加
                    }
                })
                .create()
                .show();
    }

}
