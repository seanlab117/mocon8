package com.example.samplemenu;

import android.content.Context;
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

import java.io.FileOutputStream;
//https://stickode.tistory.com/1352 ==> webview
//https://oscarstory.tistory.com/70 ==> http okhttp 통신
//https://pilot376.tistory.com/70  ==>webview assets file 읽기  x
//https://readystory.tistory.com/182 ==>webview assets file
//self
//https://m.blog.naver.com/2hyoin/220386667473 ==>storge에 저장하는 법

public class MainActivity11 extends AppCompatActivity {
    WebView webView;
    EditText urlInput;
    TextView pageTitle;
    Button loadButton;
    Button backButton;

    Button startButton;
    Button sendButton;
    Context ctx;
    FileOutputStream outputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main11);

        // UI 요소 초기화
        webView = findViewById(R.id.webView);
        urlInput = findViewById(R.id.urlInput);
        urlInput.setText("192.168.0.101");

        pageTitle = findViewById(R.id.pageTitle);
        loadButton = findViewById(R.id.loadButton);
        backButton = findViewById(R.id.backButton);

        startButton = findViewById(R.id.startButton);
        sendButton = findViewById(R.id.sendButton);

        // WebView 설정
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        ctx=this;
        // WebViewClient 설정 (외부 브라우저로 열리지 않게 설정)
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //String url = "file:///android_asset/index.html";
               // webView.loadUrl(url);
                // 페이지가 로드된 후, 타이틀 가져오기
                pageTitle.setText(view.getTitle());
            }
        });

        // URL 로드 버튼 설정
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //sean String url = urlInput.getText().toString();
               //String url = "https://m.naver.com";
                WebSettings webSettings = webView.getSettings();
                webSettings.setJavaScriptEnabled(true);
                webSettings.setAllowContentAccess(true);
                //webview.loadData(urlString, "text/html;   charset=utf-8", null);
                String url = "file:///android_asset/index.html";
                //webView
                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    url = "http://" + url;
                }
                webView.loadUrl("file:///android_asset/index.html"); // URL 로드
                //webView.loadData(url, "text/html;   charset=utf-8", null);
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
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sean String url = urlInput.getText().toString();
                //String url = "192.168.0.101";
                WebSettings webSettings = webView.getSettings();
                webSettings.setJavaScriptEnabled(true);
                String url = "file:///android_asset/index.html";
                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    url = "http://" + url;
                }
                //webView.loadUrl(url); // URL 로드
                webView.loadUrl("file:///android_asset/index.html"); // URL 로드
                Log.d("haha", "server.start()::webView.getUrl()"+webView.getUrl() );
                String filename = "myfile";
                String string = "Hello world!";
// Internal File
                //FileOutputStream outputStream;

                try {
                    //
                    outputStream = outFileOutput(filename, ctx.MODE_PRIVATE);
                    outputStream.write(string.getBytes());
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
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