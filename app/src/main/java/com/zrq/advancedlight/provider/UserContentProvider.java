package com.zrq.advancedlight.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zrq.advancedlight.db.UserDatabaseHelper;
import com.zrq.advancedlight.util.Constants;

public class UserContentProvider extends ContentProvider {
    private UserDatabaseHelper helper;

    private static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int CODE_MATCH = 1;

    static {
        matcher.addURI("com.zrq.advancedlight", null, CODE_MATCH);
    }

    public UserContentProvider() {
    }

    @Override
    public boolean onCreate() {
        helper = new UserDatabaseHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        int match = matcher.match(uri);
        if (match == CODE_MATCH) {
            SQLiteDatabase db = helper.getWritableDatabase();
            return db.query(Constants.TABLE_NAME, projection, selection, selectionArgs, sortOrder, null, null);
        } else {
            throw new IllegalArgumentException("Uri is not match");
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}