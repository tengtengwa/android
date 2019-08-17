package com.example.broadcastbestpractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends BaseActivity {
//    private IntentFilter intentFilter;
//    private BaseActivity.ForceOfflineReceiver localReceiver;
//    private LocalBroadcastManager localBroadcastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        Button forceOffline = findViewById(R.id.force_offline);
        forceOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.example.broadcastbestpractice.FORCE_OFFLINE");
                sendBroadcast(intent);
//                localBroadcastManager.sendBroadcast(intent);

            }
        });
//        intentFilter = new IntentFilter();
//        intentFilter.addAction("com.example.broadcasttest.FORCE_OFFLINE");
//        localReceiver = new BaseActivity.ForceOfflineReceiver();
//        localBroadcastManager.registerReceiver(localReceiver, intentFilter);
    }
}
