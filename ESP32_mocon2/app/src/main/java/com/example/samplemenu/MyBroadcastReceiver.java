package com.example.samplemenu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("haha", "MyBroadcastReceiver::onReceive ");
        if ("com.example.samplemenu".equals(intent.getAction())) {
            String message = intent.getStringExtra("message");
            Toast.makeText(context, "Received: " + message, Toast.LENGTH_SHORT).show();
        }
    }



}
