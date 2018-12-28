package com.example.administrator.weixin.sqlite.service;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.weixin.sqlite.domain.Friend;
import com.example.administrator.weixin.sqlite.domain.User;

import java.util.ArrayList;
import java.util.List;

public class SqliteOperator {
    private SQLiteDatabase db;
    private SqliteOpenHelper dbHelper;

    public SqliteOperator(Context context) {
        dbHelper = new SqliteOpenHelper(context, "weixin", null, 1);
        db = dbHelper.getWritableDatabase();
    }

    /**
     * 添加好友
     * @param friend
     */
    public void addFriend(Friend friend) {
        db.execSQL("insert into friends values(?,?,?)",
                new Object[] { friend.getUserId(), friend.getFriendId(), friend.getBeizhu() });

    }

    /**
     * 修改好友备注
     * @param friend
     */
    public void updateFriend(Friend friend) {
        db.execSQL("update friends set beizhu=? where user_id=? and friend_id=?",
                new Object[] { friend.getBeizhu(), friend.getUserId() });
    }

    /**
     * 删除某个好友
     * @param userId
     * @param friendId
     */
    public void deleteFriend(String userId, String friendId) {
        db.execSQL("delete from friends where user_id=? and friend_id=?", new String[] { userId, friendId });
    }

    /**
     * 查找一个人的全部好友
     * @param userId
     * @return
     */
    public List<User> queryAllFriends(String userId) {
        ArrayList<User> list = new ArrayList<>();
        Cursor c = db.rawQuery("select * from friends where user_id=?", new String[]{ userId });
        while (c.moveToNext()) {
            User user = findUserByID(c.getString(1));  //friendID
            if(user != null)  list.add(user);
        }
        c.close();
        return list;
    }

    /**
     * 根据ID查找用户
     * @param userId
     * @return
     */
    public User findUserByID(String userId){
        User user = null;
        Cursor c = db.rawQuery("select * from user where user_id= ?", new String[] { userId });
        while (c.moveToNext()) {
            user = new User();
            user.setUserId(c.getString(0));
            user.setPassword(c.getString(1));
            user.setNickname(c.getString(2));
        }
        c.close();
        return user;
    }

    /**
     * 注册用户
     * @param user
     */
    public void addUser(User user){
        db.execSQL("insert into user values(?,?,?)",
                new Object[] { user.getUserId(),user.getPassword(), user.getNickname() });
    }


//    // 检验用户名是否已存在
//    public boolean CheckIsDataAlreadyInDBorNot(String value) {
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        String Query = "Select * from lxrData where name =?";
//        Cursor cursor = db.rawQuery(Query, new String[] { value });
//        if (cursor.getCount() > 0) {
//            cursor.close();
//            return true;
//        }
//        cursor.close();
//        return false;
//    }
//
//    // 判断信息是否已经存在
//    public boolean Dataexist(String name, String number, String introduce) {
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        String Query = "Select * from lxrData where name =? and number=? and introduce=?";
//        Cursor cursor = db.rawQuery(Query, new String[] { name, number, introduce });
//        if (cursor.getCount() > 0) {
//            cursor.close();
//            return true;
//        }
//        cursor.close();
//        return false;
//    }
}
