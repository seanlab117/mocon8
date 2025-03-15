package com.example.samplemenu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class LocalReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("haha", "LocalReceiver::onReceive 1");
        String message = intent.getStringExtra("data");
        Toast.makeText(context, "Local Broadcast Received: " + message, Toast.LENGTH_SHORT).show();
    }
}
