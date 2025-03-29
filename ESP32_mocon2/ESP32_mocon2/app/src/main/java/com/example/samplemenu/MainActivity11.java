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
    Button uiroading;
 //   Button backButton;

    Button startButton;
    Button sendButton;
    Context ctx;
    FileOutputStream outputStream;
    int count=0;
    String url;

    String text,text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main11);

        // UI 요소 초기화
        webView = findViewById(R.id.webView);
        urlInput = findViewById(R.id.urlInput);
        save("test1");
        text2=read();
        Log.d("haha", "pref 1::"+text2);

        if(text2.equals("test1")) {
//            urlInput.setText("www.naver.com");
            urlInput.setText("192.168.0.102:1880");
            Log.d("haha", "pref 4::"+text2);

        } else {
//            urlInput.setText("www.google.com");
            urlInput.setText("192.168.0.200:1880");
            Log.d("haha", "pref 5::"+text);

        }

        pageTitle = findViewById(R.id.pageTitle);
        loadButton = findViewById(R.id.loadButton);
        uiroading= findViewById(R.id.uiroading);
//        backButton = findViewById(R.id.backButton);



        // WebView 설정
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setAllowFileAccess(false);
        webSettings.setDomStorageEnabled(true);
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//        webView.setWebViewClient(new MyBrowser());
//        webView.setWebChromeClient(new WebViewClient());
        webView.setWebContentsDebuggingEnabled(false);

        ctx=this;
//        String url="https://www.naver.com";
//        webView.loadUrl(url);
        // WebViewClient 설정 (외부 브라우저로 열리지 않게 설정)
        url = "http://192.168.0.102:1880";

        webView.loadUrl(url);
        Log.d("haha", "pref 2 -1::"+text2);
//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                // String url = "file:///android_asset/index.html";
//
//                text2=read();
//                Log.d("haha", "pref 2::"+text2);
//                if(text2.equals("test1")) {
//                   url = "http://192.168.0.102:1880";
//                    //url = "https://www.naver.com";
//                    webView.loadUrl(url);
//
//                } else
//                {
//                    url = "http://192.168.0.200:1880";
//                    //url = "https://www.google.com";
//                    webView.loadUrl(url);
//
//                }
//                // 페이지가 로드된 후, 타이틀 가져오기
//                pageTitle.setText(view.getTitle());
//            }
//        });

        // URL 로드 버튼 설정
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //sean String url = urlInput.getText().toString();
                Log.d("haha", "pref 3::"+text2);
                text2=read();
                if(text2.equals("test1")) {
                    //url ="https://www.naver.com";
                    url ="http://192.168.0.102:1880";
                    urlInput.setText("192.168.0.102:1880");

                } else {
                    //url ="https://www.google.com";
                    url ="http://192.168.0.200";
                    urlInput.setText("192.168.0.200:1880");

                }
//                if(count==0){
//                    url = "http://192.168.0.200:1880";
//                } else {
//                    String url = "http://192.168.0.200:1880";
//                }
                WebSettings webSettings = webView.getSettings();
                webSettings.setAllowContentAccess(true);
                webSettings.setJavaScriptEnabled(true);
                webSettings.setLoadsImagesAutomatically(true);
                webSettings.setSupportZoom(true);
                webSettings.setBuiltInZoomControls(true);
                webSettings.setAllowFileAccess(false);
                webSettings.setDomStorageEnabled(true);
                webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
                webSettings.setDomStorageEnabled(true);
                webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//        webView.setWebViewClient(new MyBrowser());
//        webView.setWebChromeClient(new WebViewClient());
                webView.setWebContentsDebuggingEnabled(false);




                //webview.loadData(urlString, "text/html;   charset=utf-8", null);
                //String url = "file:///android_asset/index.html";
                //webView
                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    url = "http://" + url;
                }
                //webView.loadUrl("file:///android_asset/index.html"); // URL 로드
                //webView.loadData(url, "text/html;   charset=utf-8", null);
                webView.loadUrl(url);
            }
        });
        uiroading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("haha", "pref 4::"+text2);
                //sean String url = urlInput.getText().toString();
                    text2=read();
                    if(text2.equals("test1")) {
                    //url="www.naver.com";
                    url = "http://192.168.0.102:1880/ui/";
                    urlInput.setText("http://192.168.0.102:1880/ui/");

                }
                else {
                    //url="www.google.com";
                    url = "http://192.168.0.200:1880/ui/";
                    urlInput.setText("http://192.168.0.200:1880/ui/");

                }
                WebSettings webSettings = webView.getSettings();
                webSettings.setJavaScriptEnabled(true);
                webSettings.setAllowContentAccess(true);

                webSettings.setLoadsImagesAutomatically(true);
                webSettings.setSupportZoom(true);
                webSettings.setBuiltInZoomControls(true);
                webSettings.setAllowFileAccess(false);
                webSettings.setDomStorageEnabled(true);
                webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
                webSettings.setDomStorageEnabled(true);
                webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//        webView.setWebViewClient(new MyBrowser());
//        webView.setWebChromeClient(new WebViewClient());
                webView.setWebContentsDebuggingEnabled(false);
                //webview.loadData(urlString, "text/html;   charset=utf-8", null);
                //String url = "file:///android_asset/index.html";
                //webView
                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    url = "http://" + url;
                }
                //webView.loadUrl("file:///android_asset/index.html"); // URL 로드
                //webView.loadData(url, "text/html;   charset=utf-8", null);
                webView.loadUrl(url);
                save("test2");
            }
        });

        // 뒤로가기 버튼 설정
//        backButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (webView.canGoBack()) {
//                    webView.goBack(); // WebView에서 이전 페이지로 이동
//                }
//            }
//        });

    }
    public String  read() {
        SharedPreferences pref = getSharedPreferences("pref", MainActivity11.MODE_PRIVATE);

// 저장된 값들을 불러온다.
        String text = pref.getString("editText", "");
        return text;
    }
    public void save(String str)
    {
        SharedPreferences pref = getSharedPreferences("pref",MainActivity11.MODE_PRIVATE);
        // Editor를 불러옴.
        SharedPreferences.Editor editor = pref.edit();
        Log.d("haha", "pref 7::");
        // 저장할 값들을 입력함.
        editor.putString("editText",str);
        Log.d("haha", "pref 8::");
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