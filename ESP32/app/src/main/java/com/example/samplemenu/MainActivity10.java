package com.example.samplemenu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class MainActivity10 extends AppCompatActivity {
    //https://stickode.tistory.com/1352
    //https://pinlib.tistory.com/entry/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C%EB%A1%9C-%EC%95%84%EB%91%90%EC%9D%B4%EC%99%80-%EC%84%9C%EB%B2%84%ED%86%B5%EC%8B%A0-%EC%A0%9C%EC%96%B4%ED%95%98%EA%B8%B0WIFI-D1-R1-ESP8266-%EB%AA%A8%ED%84%B0%EC%A0%9C%EC%96%B4-JSON
    private MyBroadcastReceiver myReceiver = new MyBroadcastReceiver();
    private LocalReceiver localReceiver = new LocalReceiver();
    WebView webView;
    EditText urlInput;
    TextView pageTitle;
    Button loadButton;
    Button backButton;

    Button SendButton;

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main10);

        Log.d("haha", "onCreate 1");
//        IntentFilter filter = new IntentFilter("com.example.samplemen");
//        registerReceiver(myReceiver, filter);

        IntentFilter filter = new IntentFilter("com.example.samplemen");
        LocalBroadcastManager.getInstance(this).registerReceiver(localReceiver, filter);
        Log.d("haha", "onCreate 2");
        // UI 요소 초기화
        webView = findViewById(R.id.webView);
        urlInput = findViewById(R.id.urlInput);
        pageTitle = findViewById(R.id.pageTitle);
        loadButton = findViewById(R.id.loadButton);
        backButton = findViewById(R.id.backButton);

        SendButton = findViewById(R.id.sendButton);
        // WebView 설정
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        urlInput.setText("www.naver.com");

        // WebViewClient 설정 (외부 브라우저로 열리지 않게 설정)
//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                // 페이지가 로드된 후, 타이틀 가져오기
//                pageTitle.setText(view.getTitle());
//            }
//        });

        // URL 로드 버튼 설정
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String url = urlInput.getText().toString();
                String url ="www.naver.com";
                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    url = "http://" + url;
                }
                webView.loadUrl(url); // URL 로드
            }
        });

        // 뒤로가기 버튼 설정
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webView.canGoBack()) {
                    webView.goBack(); // WebView에서 이전 페이지로 이동
                }
            }
        });

        SendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url ="www.naver.com";
                webView.loadUrl(url);
                Log.d("haha", "SendButton.setOnClickListener ");
                Intent intent = new Intent("com.example.samplemenu");
                intent.putExtra("message", "Hello, Broadcast!");
                //sendBroadcast(intent); // 브로드캐스트 전송
                LocalBroadcastManager.getInstance(MainActivity10.this).sendBroadcast(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}