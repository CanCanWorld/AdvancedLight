package com.zrq.advancedlight.activity.advanced;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.zrq.advancedlight.R;
import com.zrq.advancedlight.adapter.ContactAdapter;
import com.zrq.advancedlight.entity.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity {

    private static final String TAG = "ContactActivity";
    private static final int REQUEST_CODE = 1;
    private Context mContext;
    private RecyclerView mRvContact;
    private List<Contact> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        mContext = this;
        checkContactPermission();
        initView();
        initData();
    }

    private void checkContactPermission() {
        int readResult = checkSelfPermission(Manifest.permission.READ_CONTACTS);
        int writeResult = checkSelfPermission(Manifest.permission.WRITE_CONTACTS);
        if (readResult == PackageManager.PERMISSION_GRANTED && writeResult == PackageManager.PERMISSION_GRANTED) {

        } else {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS}, REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "request contact is success");
            } else {
                Log.d(TAG, "request contact is failure!");
            }
        }
    }

    private void initView() {
        mRvContact = findViewById(R.id.rv_contact);
    }

    private void initData() {
        getContact();
        mRvContact.setLayoutManager(new LinearLayoutManager(mContext));
        ContactAdapter adapter = new ContactAdapter(mContext, contacts);
        mRvContact.setAdapter(adapter);
    }

    @SuppressLint("Range")
    private void getContact() {
        ContentResolver resolver = mContext.getContentResolver();
        Uri uri = Uri.parse("content://com.android.contacts/data/phones");
        Cursor cursor = resolver.query(uri, new String[]{"display_name_alt", "data1"}, null, null, null);
        String[] columnNames = cursor.getColumnNames();
        contacts = new ArrayList<>();
        while (cursor.moveToNext()) {
            Contact contact = new Contact();
            contact.setName(cursor.getString(cursor.getColumnIndex("display_name_alt")));
            contact.setNumber(cursor.getString(cursor.getColumnIndex("data1")));
            contacts.add(contact);
        }
        for (Contact contact : contacts) {
            Log.d(TAG, "getContact: " + contact.toString());
        }
    }
}