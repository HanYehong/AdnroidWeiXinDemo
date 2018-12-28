package com.example.administrator.weixin.sqlite.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteOpenHelper extends SQLiteOpenHelper {
    public static final String CREATE_USERDATA_FRIENDS = "create table friends(user_id varchar(200) primary key,friend_id varchar(200)," +
            "beizhu varchar(200))";
    public static final String CREATE_USERDATA_USER = "create table user(user_id varchar(200) primary key,password varchar(200)," +
            "nickname varchar(200))";
    private Context mContext;

    public SqliteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory cursorFactory, int version) {
        super(context, name, cursorFactory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERDATA_USER);
        db.execSQL(CREATE_USERDATA_FRIENDS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
