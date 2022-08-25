package com.zrq.advancedlight.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.zrq.advancedlight.R;
import com.zrq.advancedlight.adapter.UserAdapter;
import com.zrq.advancedlight.dao.IUserDao;
import com.zrq.advancedlight.dao.UserDaoImpl;
import com.zrq.advancedlight.entity.User;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private Context mContext;
    private EditText mEtUsername;
    private EditText mEtPassword;
    private Button mBtnLogin;
    private RadioGroup mRgSex;
    private RecyclerView mRvListUser;
    private String sex = "未知";
    private List<User> mData;
    private UserAdapter adapter;
    private IUserDao userDao;
    private View rootView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;
        initView();
        initData();
    }

    private void initView() {
        rootView = getWindow().getDecorView();

        mEtUsername = findViewById(R.id.username);
        mEtPassword = findViewById(R.id.password);
        mBtnLogin = findViewById(R.id.btn_login);
        mRgSex = findViewById(R.id.rg_sex);
        mRvListUser = findViewById(R.id.rv_list_user);

    }

    private void initData() {
        userDao = new UserDaoImpl(mContext);
        mData = userDao.listAllUsers();
        mRvListUser.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new UserAdapter(mContext, mData);
        mRvListUser.setAdapter(adapter);

        adapter.setOnClickListener(new UserAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                Log.d(TAG, "position: " + position);
                Log.d(TAG, "mData.get(position).getId(): " + mData.get(position).getId());
                new AlertDialog.Builder(mContext)
                        .setTitle("确定要删除吗？")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                userDao.deleteUserById(mData.get(position).getId());
                                User user = adapter.removeData(position);
                                Snackbar.make(rootView, "删除成功", Snackbar.LENGTH_LONG)
                                        .setAction("撤回", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                userDao.addUser(user);
                                                adapter.addData(position, user);
                                            }
                                        })
                                        .show();
                            }
                        })
                        .show();
            }
        });

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mEtUsername.getText().toString();
                String password = mEtPassword.getText().toString();
                register(username, password);
            }
        });

        mRgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == 1) {
                    sex = "男";
                } else if (checkedId == 2) {
                    sex = "女";
                }
            }
        });
    }

    private void register(String username, String password) {
        if ("".equals(username)) {
            mEtUsername.setError("用户名不能为空");
        } else if ("".equals(password)) {
            mEtPassword.setError("密码不能为空");
        } else if (username.length() > 20) {
            mEtUsername.setError("用户名太长");
        } else if (password.length() > 20) {
            mEtPassword.setError("密码太长");
        } else {
            User user = new User(username, password, sex);
            userDao.addUser(user);
            adapter.addData(mData.size(),user);
            Toast.makeText(mContext, "注册成功", Toast.LENGTH_SHORT).show();
        }
    }


}