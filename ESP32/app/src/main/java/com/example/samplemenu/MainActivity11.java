package com.example.samplemenu;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity11 extends AppCompatActivity {
//https://oscarstory.tistory.com/70
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main11);
        starthttp();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void starthttp(){
        
        String url ="http://www.naver.com/index.html";
        OkHttpClient client = new OkHttpClient();
        Log.d("haha", "starthttp.start() 1::");
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                Log.d("haha", "server.start()::"+e);

            }

            public void onResponse(Call call, Response response) throws IOException {

                Log.d("haha", "onResponse.start()::"+response);

                Log.d("haha", ""+response.body().string());
            }
        });
    }


}