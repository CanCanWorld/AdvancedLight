package com.zrq.advancedlight.activity.advanced;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

import com.zrq.advancedlight.R;

public class NotificationActivity extends AppCompatActivity {

    private Context mContext;
    private Button mBtnNf;
    //创建通知管理器
    private NotificationManager notificationManager;
    //通知渠道的id
    private String channelId = "music";
    private RemoteViews mRemoteViews;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        mContext = this;
        initView();
        initData();
    }

    private void initView() {
        mBtnNf = findViewById(R.id.notification);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initData() {
        //获取通知管理器对象
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //Android8.0以上的适配
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //创建通知渠道实例（这三个参数是必须要有的，分别是渠道ID，渠道名称，重要等级）
            NotificationChannel channel = new NotificationChannel(channelId, "微信消息", NotificationManager.IMPORTANCE_DEFAULT);
            //创建通知渠道的通知管理器
//            NotificationManager manager = (NotificationManager) getSystemService(NotificationManager.class);
            //为NotificationManager设置通知渠道
            notificationManager.createNotificationChannel(channel);
        }
        mRemoteViews = new RemoteViews(getPackageName(), R.layout.notification_fold);
        Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.baidu.com"));
        //PendIntent为延迟的Intent，用于设置Notification的跳转，需要传入一个Intent对象
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, mIntent, 0);
        mBtnNf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建notification对象，设置相应属性
                //创建使用AndroidX库的NotificationCompat类的静态内部类Builder
                //使用需要两个参数分别是Context和创建的渠道ID相匹配
                Notification notification = new NotificationCompat.Builder(mContext, channelId)
                        .setAutoCancel(true)    //设置自动关闭
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setContentIntent(pendingIntent)    //设置跳转
                        .setContentTitle("收到聊天消息")
                        .setContentText("不早了，快快睡觉")
                        .setWhen(System.currentTimeMillis())
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.bg5))
                        .setSmallIcon(R.mipmap.bg5)
                        .setFullScreenIntent(pendingIntent, true)
                        .build();
                //调用管理器的notify方法
                notification.bigContentView = mRemoteViews;
                notificationManager.notify(1, notification);
            }
        });
    }
}