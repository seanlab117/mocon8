package com.example.samplemenu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
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
//https://kotlinworld.com/364 ==> webview broadcasting
//https://developer.android.com/develop/ui/views/layout/webapps/webview?hl=ko#java ==>webview javascriptxx
//https://indra17.tistory.com/entry/android-webview%EB%A1%9C-javascript-%ED%98%B8%EC%B6%9C-%EB%B0%8F-%EC%9D%B4%EB%B2%A4%ED%8A%B8-%EB%B0%9B%EA%B8%B0
@SuppressLint("SetJavaScriptEnabled")
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
        urlInput.setText("192.168.0.100:1880");

        pageTitle = findViewById(R.id.pageTitle);
        loadButton = findViewById(R.id.loadButton);
        backButton = findViewById(R.id.backButton);

//        startButton = findViewById(R.id.startButton);
//        sendButton = findViewById(R.id.sendButton);

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
//                String url = "file:///android_asset/index.html";
                String url = "192.168.0.100:1880";
                //webView
                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    url = "http://" + url;
                }
                //webView.loadUrl("file:///android_asset/index.html"); // URL 로드
                webView.loadData(url, "text/html;   charset=utf-8", null);
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




    }
    public void read() {
        SharedPreferences pref = getSharedPreferences("pref", MainActivity11.MODE_PRIVATE);

// 저장된 값들을 불러온다.
        String text = pref.getString("editText", "");
    }
    public void save()
    {
        SharedPreferences pref = getSharedPreferences("pref",MainActivity11.MODE_PRIVATE);
        // Editor를 불러옴.
        SharedPreferences.Editor editor = pref.edit();

        // 저장할 값들을 입력함.
        editor.putString("editText","test1");
        editor.putString("editText","test2");

        // 저장함
        editor.commit();
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