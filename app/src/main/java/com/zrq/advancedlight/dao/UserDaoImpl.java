package com.zrq.advancedlight.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zrq.advancedlight.db.UserDatabaseHelper;
import com.zrq.advancedlight.entity.User;
import com.zrq.advancedlight.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements IUserDao {

    private final UserDatabaseHelper mHelper;

    public UserDaoImpl(Context context) {
        mHelper = new UserDatabaseHelper(context);
    }

    @Override
    public long addUser(User user) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = userToValues(user);
        long result = db.insert(Constants.TABLE_NAME, null, values);
        db.close();
        return result;
    }


    @Override
    public int deleteUserById(int id) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        int result = db.delete(Constants.TABLE_NAME, Constants.FIELD_ID + "=" + id, null);
        db.close();
        return result;
    }

    @Override
    public int updateUserById(User user) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = userToValues(user);
        int result = db.update(Constants.TABLE_NAME, values, Constants.FIELD_ID, new String[]{String.valueOf(user.getId())});
        db.close();
        return result;
    }

    @Override
    public User getUserById(int id) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql = "select * from " + Constants.TABLE_NAME + " where id = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(id)});
        User user = null;
        if (cursor.moveToNext()) {
            user = cursorToUser(cursor);
        }
        db.close();
        return user;
    }

    @Override
    public List<User> listAllUsers() {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        Cursor cursor = db.query(Constants.TABLE_NAME, null, null, null, null, null, null);
        List<User> users = new ArrayList<>();
        while (cursor.moveToNext()) {
            users.add(cursorToUser(cursor));
        }
        return users;
    }

    /**
     * 将User对象转换成ContentValues对象
     */
    private ContentValues userToValues(User user) {
        ContentValues values = new ContentValues();
        values.put(Constants.FIELD_USERNAME, user.getUsername());
        values.put(Constants.FIELD_PASSWORD, user.getPassword());
        values.put(Constants.FIELD_SEX, user.getSex());
        return values;
    }

    /**
     * 根据Cursor获取其中的字段拼接成User对象
     */
    @SuppressLint("Range")
    private User cursorToUser(Cursor cursor) {
        User user = new User();
        user.setId(cursor.getInt(cursor.getColumnIndex(Constants.FIELD_ID)));
        user.setUsername(cursor.getString(cursor.getColumnIndex(Constants.FIELD_USERNAME)));
        user.setPassword(cursor.getString(cursor.getColumnIndex(Constants.FIELD_PASSWORD)));
        user.setSex(cursor.getString(cursor.getColumnIndex(Constants.FIELD_SEX)));
        return user;
    }
}
