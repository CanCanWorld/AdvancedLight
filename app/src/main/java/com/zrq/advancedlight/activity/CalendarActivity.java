package com.zrq.advancedlight.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.zrq.advancedlight.R;

import java.util.Calendar;
import java.util.TimeZone;

public class CalendarActivity extends AppCompatActivity {

    private static final int PERMISSION_RESULT_CODE = 1;
    private static final String TAG = "CalendarActivity";
    private Context mContext;
    private Button mBtnGetCalendar;
    private TextView mTvCalendar;
    private Button mBtnInsertCalendar;
    private LinearLayout mLLDate;
    private LinearLayout mLLDuration;
    private TextView mTvDate;
    private TextView mTvDuration;
    private EditText mEtInsertCalendar;
    private Calendar calendar;
    private CheckBox mCbRemind;
    private int dateYear;
    private int dateMonth;
    private int dateDay;
    private int startHour;
    private int startMinute;
    private int endHour;
    private int endMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        mContext = this;
        checkCalendarPermission();
        initView();
        initData();
    }

    private void checkCalendarPermission() {
        int readResult = checkSelfPermission(Manifest.permission.READ_CALENDAR);
        int writeResult = checkSelfPermission(Manifest.permission.WRITE_CALENDAR);
        if (readResult == PackageManager.PERMISSION_GRANTED && writeResult == PackageManager.PERMISSION_GRANTED) {
            //有权限
        } else {
            //无权限
            requestPermissions(new String[]{Manifest.permission.READ_CALENDAR,
                    Manifest.permission.WRITE_CALENDAR}, PERMISSION_RESULT_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_RESULT_CODE) {
            if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                //获取权限成功
            } else {
                //获取权限失败
                finish();
            }
        }
    }

    private void initView() {
        mBtnGetCalendar = findViewById(R.id.btn_get_calendar);
        mBtnInsertCalendar = findViewById(R.id.btn_insert_calendar);
        mTvCalendar = findViewById(R.id.tv_calendar_field);
        mTvCalendar.setMovementMethod(ScrollingMovementMethod.getInstance());
        mEtInsertCalendar = findViewById(R.id.et_insert_calendar);
        mLLDate = findViewById(R.id.ll_date);
        mLLDuration = findViewById(R.id.ll_duration);
        mTvDate = findViewById(R.id.tv_date);
        mTvDuration = findViewById(R.id.tv_duration);
        mCbRemind = findViewById(R.id.cb_remind);
        mCbRemind.setChecked(true);
    }

    private void initData() {
        calendar = Calendar.getInstance();
        mBtnGetCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCalendarField();
            }
        });
        mBtnInsertCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertCalendar();
            }
        });
        mLLDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        mContext,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                //monthOfYear 得到的月份会减1所以我们要加1
                                dateYear = year;
                                dateMonth = month;
                                dateDay = dayOfMonth;
                                String date = year + "-" + (month + 1) + "-" + dayOfMonth;
                                mTvDate.setText(date);
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        mLLDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_duration, null);
                TimePicker startTime = view.findViewById(R.id.start_time);
                TimePicker endTime = view.findViewById(R.id.end_time);
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("选择时间");
                builder.setView(view);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startHour = startTime.getHour();
                        startMinute = startTime.getMinute();
                        endHour = endTime.getHour();
                        endMinute = endTime.getMinute();
                        String duration = startHour + ":" + startMinute + " ~ " + endHour + ":" + endMinute;
                        mTvDuration.setText(duration);
                    }
                });
                builder.setNegativeButton("取消", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void insertCalendar() {
        ContentResolver resolver = mContext.getContentResolver();
        //获取id
        int calID = 1;
        //获取开始时间
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(dateYear, dateMonth, dateDay, startHour, startMinute);
        long startMillis = beginTime.getTimeInMillis();
        //获取结束时间
        Calendar endTime = Calendar.getInstance();
        endTime.set(dateYear, dateMonth, dateDay, endHour, endMinute);
        long endMillis = endTime.getTimeInMillis();
        //获取定位
        String timeZone = TimeZone.getDefault().getID();
        Log.d(TAG, "timeZone: " + timeZone);
        //获取题目
        String title = mEtInsertCalendar.getText().toString();
        //获取描述信息
        String desc = "这个是第三方添加";
        //设置values
        ContentValues eventValues = new ContentValues();
        eventValues.put(CalendarContract.Events.CALENDAR_ID, calID);
        eventValues.put(CalendarContract.Events.DTSTART, startMillis);
        eventValues.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone);
        eventValues.put(CalendarContract.Events.DTEND, endMillis);
        eventValues.put(CalendarContract.Events.TITLE, title);
        eventValues.put(CalendarContract.Events.DESCRIPTION, desc);
        Uri insertResult = resolver.insert(CalendarContract.Events.CONTENT_URI, eventValues);
        Log.d(TAG, "insertResult: " + insertResult);
        if (mCbRemind.isChecked()) {
            ContentValues remindValues = new ContentValues();
            remindValues.put(CalendarContract.Reminders.EVENT_ID, insertResult.getLastPathSegment());
            remindValues.put(CalendarContract.Reminders.MINUTES, 15);
            remindValues.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
            Uri remindResult = resolver.insert(CalendarContract.Reminders.CONTENT_URI, remindValues);
            Log.d(TAG, "remindResult: " + remindResult);
        }
    }

    @SuppressLint("Range")
    private void getCalendarField() {
        ContentResolver resolver = mContext.getContentResolver();
        Uri uri = CalendarContract.Calendars.CONTENT_URI;
        Cursor cursor = resolver.query(uri, null, null, null, null);
        String[] columnNames = cursor.getColumnNames();
        StringBuilder sb = new StringBuilder();
        while (cursor.moveToNext()) {
            for (String columnName : columnNames) {
                sb.append(columnName + "-->" + cursor.getString(cursor.getColumnIndex(columnName)) + "\n");
            }
            sb.append("=====================================\n");
        }
        mTvCalendar.setText(sb);
    }
}