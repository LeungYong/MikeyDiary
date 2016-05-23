package com.userking.diarypaper.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.userking.diarypaper.R;
import com.userking.diarypaper.activity.MainActivity;

/**
 * Created by Administrator on 2016/5/23 0023.
 */
public class MyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public class AlarmReceiver extends BroadcastReceiver {

        private NotificationManager manager;

        @Override
        public void onReceive(Context context, Intent intent) {
            manager = (NotificationManager)context.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
            //例如这个id就是你传过来的
            String id = intent.getStringExtra("id");
            //MainActivity是你点击通知时想要跳转的Activity
            Intent playIntent = new Intent(context, MainActivity.class);
            playIntent.putExtra("id", id);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, playIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setContentTitle("title").setContentText("提醒内容").setSmallIcon(R.mipmap.alarm).setDefaults(Notification.DEFAULT_ALL).setContentIntent(pendingIntent).setAutoCancel(true).setSubText("二级text");
            manager.notify(1, builder.build());
        }
    }
}
