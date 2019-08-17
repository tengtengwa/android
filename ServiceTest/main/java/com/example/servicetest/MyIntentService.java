package com.example.servicetest;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class MyIntentService extends IntentService {
    public MyIntentService() {
        super("myintentservice");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("ttw", "Thread in myIntentService id is: " + Thread.currentThread().getId());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("ttw", "onDestroy execute: ");
    }
}
