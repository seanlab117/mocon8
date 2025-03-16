package com.example.samplemenu;

import android.os.Handler;
import android.util.Log;

private class AndroidBridge {
    private final Handler handler = new Handler();

    public void setMessage(final String arg) {
        handler.post(new Runnable() {
            public void run() {
                Log.d("HybridApp", "setMessage("+arg+")");
                mTextView.setText(arg);
            }
        });
        }
    }

