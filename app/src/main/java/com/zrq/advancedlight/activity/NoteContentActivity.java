package com.zrq.advancedlight.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zrq.advancedlight.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NoteContentActivity extends AppCompatActivity {

    private static final String TAG = "NoteContentActivity";
    private Context mContext;
    private Button mBtnGetNote;
    private TextView mTvNoteInfo;
    private EditText mEtInsert;
    private Button mBtnInsert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_content);
        mContext = this;
        initView();
        initData();
        contentListener();
    }

    private void contentListener() {
        ContentResolver resolver = mContext.getContentResolver();
        Uri uri = Uri.parse("content://com.zrq.note.provider");
        resolver.registerContentObserver(uri, true, new ContentObserver(new Handler()) {
            @Override
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);
                Log.d(TAG, "数据发生变化");
                getNote();
            }
        });
    }

    private void initView() {
        mBtnGetNote = findViewById(R.id.btn_get_note);
        mTvNoteInfo = findViewById(R.id.tv_note_info);
        mEtInsert = findViewById(R.id.et_insert_note);
        mBtnInsert = findViewById(R.id.btn_insert_note);
        mTvNoteInfo.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    private void initData() {
        mBtnGetNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNote();
            }
        });
        mBtnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String note = mEtInsert.getText().toString();
                Log.d(TAG, "note: "+note);
                if (!"".equals(note)) {
                    ContentResolver resolver = mContext.getContentResolver();
                    Uri uri = Uri.parse("content://com.zrq.note.provider/notes");
                    ContentValues values = new ContentValues();
                    values.put("content", note);
                    values.put("time", getTime());
                    values.put("mode", 1);
                    Log.d(TAG, "getTime: "+getTime());
                    resolver.insert(uri, values);
                }
            }
        });
    }

    private String getTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    @SuppressLint("Range")
    private void getNote() {
        ContentResolver resolver = mContext.getContentResolver();
        Uri uri = Uri.parse("content://com.zrq.note.provider/notes");
        Cursor cursor = resolver.query(uri, null, null, null, null);
        String[] columnNames = cursor.getColumnNames();
        StringBuilder noteInfo = new StringBuilder();
        for (String columnName : columnNames) {
            noteInfo.append("columnName: " + columnName + "\n");
        }
        while (cursor.moveToNext()) {
            noteInfo.append("===================================\n");
            for (String columnName : columnNames) {
                noteInfo.append(columnName + ": " + cursor.getString(cursor.getColumnIndex(columnName)) + "\n");
            }
        }
        mTvNoteInfo.setText(noteInfo);
    }
}