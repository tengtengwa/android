package com.example.notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button sendNotice = findViewById(R.id.send_notice);
        sendNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String CHANNEL_ID = "channel_id_1";
                final String CHANNEL_NAME = "channel_name_1";

                Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
                PendingIntent pi = PendingIntent.getActivities(MainActivity.this,
                        0, new Intent[]{intent}, 0);

                NotificationManager mNotificationManager = (NotificationManager)
                        getSystemService(Context.NOTIFICATION_SERVICE);

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    //只在Android 7.0之上需要渠道
                    NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,
                            CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
                    Notification notification = new NotificationCompat.Builder(MainActivity.this)
                            .setPriority(NotificationCompat.PRIORITY_MAX)
                            .build();
                    notificationChannel.enableLights(true);
                    notificationChannel.setLightColor(Color.RED);
                    notificationChannel.enableVibration(false);
//                    notificationChannel .setVibrationPattern(new long[]{1000, 2000, 1000,3000});
                    //如果这里用IMPORTANCE_NOENE就需要在系统的设置里面开启渠道，
                    //通知才能正常弹出
                    if (mNotificationManager != null) {
                        mNotificationManager.createNotificationChannel(notificationChannel);
                    }
                }
                NotificationCompat.Builder builder= new NotificationCompat.Builder(MainActivity.this,CHANNEL_ID);


                builder.setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("通知标题")
                        .setContentText("通知内容")
                        .setLights(Color.RED, 100, 100)
                        .setContentIntent(pi);
//                        .setAutoCancel(true);



                mNotificationManager.notify(1, builder.build());
            }
        });

    }
}
