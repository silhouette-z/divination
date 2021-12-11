package com.me.myapplication.activity;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.me.myapplication.R;

public class AutoReceiver extends BroadcastReceiver {
    private static final int NOTIFICATION_FLAG = 1;

    @SuppressLint("NewApi")

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("VIDEO_TIMER")) {
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                    new Intent(context, SplashActivity.class), 0);
            NotificationManager manager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);


            if(Build.VERSION.SDK_INT >= 26)
            {
                //当sdk版本大于26
                String id = "channel_1";
                String description = "143";
                int importance = NotificationManager.IMPORTANCE_LOW;
                NotificationChannel channel = new NotificationChannel(id, description, importance);
//                     channel.enableLights(true);
//                     channel.enableVibration(true);//
                manager.createNotificationChannel(channel);
                Notification notification = new Notification.Builder(context, id)
                        .setCategory(Notification.CATEGORY_MESSAGE)
                        .setSmallIcon(R.drawable.ic_xingzuo)
                        .setContentTitle("您有好久没来看看了~")
                        .setContentText("点击获取今日份运势分析")
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .build();
                manager.notify(1, notification);
            }
            else
            {
                //当sdk版本小于26
                Notification notification = new NotificationCompat.Builder(context)
                        .setContentTitle("This is content title")
                        .setContentText("This is content text")
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .build();
                manager.notify(1,notification);
            }
        }
    }

}
