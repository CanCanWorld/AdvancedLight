package com.zrq.advancedlight.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.zrq.advancedlight.util.Constants;

public class UserDatabaseHelper extends SQLiteOpenHelper {

    public UserDatabaseHelper(@Nullable Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table user(" +
                "" + Constants.FIELD_ID + " integer primary key autoincrement," +
                "" + Constants.FIELD_USERNAME + " varchar(30)," +
                "" + Constants.FIELD_PASSWORD + " varchar(30)," +
                "" + Constants.FIELD_SEX + " varchar(20))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
