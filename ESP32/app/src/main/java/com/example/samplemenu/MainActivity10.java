package com.example.samplemenu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity10 extends AppCompatActivity {
    //https://stickode.tistory.com/1352
    //https://pinlib.tistory.com/entry/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C%EB%A1%9C-%EC%95%84%EB%91%90%EC%9D%B4%EC%99%80-%EC%84%9C%EB%B2%84%ED%86%B5%EC%8B%A0-%EC%A0%9C%EC%96%B4%ED%95%98%EA%B8%B0WIFI-D1-R1-ESP8266-%EB%AA%A8%ED%84%B0%EC%A0%9C%EC%96%B4-JSON
    //private MyBroadcastReceiver myReceiver = new MyBroadcastReceiver();
    private LocalReceiver localReceiver = new LocalReceiver();
    WebView webView;
    EditText urlInput;
    TextView pageTitle;
    Button loadButton;
    Button backButton;

    Button SendButton;


    TextView textViewColorHex, textViewRGB, textViewRed, textViewGreen, textViewBlue;
    ImageView imageView;
    SeekBar seekBarRed, seekBarGreen, seekBarBlue;
    int redValue = 64;
    int greenValue = 128;
    int blueValue = 0;

    ServerSocket server1=null;
    Socket sock1=null;
    TextView textViewheader;
    Context ctx;
    private void updateColor(){
        int color = Color.rgb(redValue, greenValue, blueValue);
        imageView.setBackgroundColor(color);

        String hex = String.format("#%02X%02X%02X", redValue, greenValue, blueValue);
        textViewColorHex.setText(hex);
        Log.d("haha", "updateColor:hex 1::"+hex);

        Log.d("haha", "updateColor:hex 2::"+hex);
        // Log.d("haha", "obj: "+obj);
        String redGreenBlue = String.format("(%d, %d, %d)", redValue, greenValue, blueValue);
        textViewRGB.setText(redGreenBlue);
    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main10);

        textViewColorHex = findViewById(R.id.textViewColorHex);
        textViewColorHex.setText("");
        textViewRGB = findViewById(R.id.textViewRGB);
        textViewRGB.setText("");
        textViewRed = findViewById(R.id.textViewRed);
        textViewGreen = findViewById(R.id.textViewGreen);
        textViewBlue = findViewById(R.id.textViewBlue);
        imageView = findViewById(R.id.imageView);
        seekBarRed = findViewById(R.id.seekBarRed);
        seekBarGreen = findViewById(R.id.seekBarGreen);
        seekBarBlue = findViewById(R.id.seekBarBlue);
        textViewheader=findViewById(R.id.textView);
        updateColor();
        ctx=this;
        seekBarBlue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                blueValue = progress;
                updateColor();
                textViewBlue.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarGreen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                greenValue = progress;
                updateColor();
                textViewGreen.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarRed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                redValue = progress;
                updateColor();
                textViewRed.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        findViewById(R.id.whiteButton).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                textViewColorHex.setText("");
                textViewRGB.setText("");
                redValue = 255;
                greenValue = 255;
                blueValue = 255;
                updateColor();

                seekBarRed.setProgress(redValue);
                seekBarGreen.setProgress(greenValue);
                seekBarBlue.setProgress(blueValue);

                textViewRed.setText(String.valueOf(redValue));
                textViewGreen.setText(String.valueOf(greenValue));
                textViewBlue.setText(String.valueOf(blueValue));
            }
        });

        findViewById(R.id.blackButton).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                textViewColorHex.setText("");
                textViewRGB.setText("");
                redValue = 0;
                greenValue = 0;
                blueValue = 0;
                updateColor();

                seekBarRed.setProgress(redValue);
                seekBarGreen.setProgress(greenValue);
                seekBarBlue.setProgress(blueValue);

                textViewRed.setText(String.valueOf(redValue));
                textViewGreen.setText(String.valueOf(greenValue));
                textViewBlue.setText(String.valueOf(blueValue));
            }
        });

        findViewById(R.id.blueButton).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                textViewColorHex.setText("");
                textViewRGB.setText("");
                redValue = 0;
                greenValue = 0;
                blueValue = 255;
                updateColor();

                seekBarRed.setProgress(redValue);
                seekBarGreen.setProgress(greenValue);
                seekBarBlue.setProgress(blueValue);

                textViewRed.setText(String.valueOf(redValue));
                textViewGreen.setText(String.valueOf(greenValue));
                textViewBlue.setText(String.valueOf(blueValue));
            }
        });

        findViewById(R.id.resetButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                textViewColorHex.setText("");
                textViewRGB.setText("");
                redValue = 64;
                greenValue = 128;
                blueValue = 0;
                updateColor();

                seekBarRed.setProgress(redValue);
                seekBarGreen.setProgress(greenValue);
                seekBarBlue.setProgress(blueValue);

                textViewRed.setText(String.valueOf(redValue));
                textViewGreen.setText(String.valueOf(greenValue));
                textViewBlue.setText(String.valueOf(blueValue));
            }
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        startServer();
                    }
                }).start();
            }
        });

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
                Log.d("haha", "loadButton.setOnClickListener ");
                //String url = urlInput.getText().toString();
                String url ="www.naver.com";
                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    url = "http://" + url;
                }
               // webView.loadUrl(url); // URL 로드

                //
//                webView.loadUrl("file:///android_asset/index.html");
//                Intent intent = new Intent(ctx, MainActivity10.class);
//                ActivityResultLauncher.launch(intent);
//                Toast.makeText(getApplicationContext(), "position 8", Toast.LENGTH_LONG).show();
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
                //webView.loadUrl(url);
                webView.loadUrl("file:///android_asset/index.html");
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

    public void startServer() {
        Log.d("haha", "startServer:hex ::");

        webView.loadUrl("file:///android_asset/index.html");
        textViewheader.setText("ServerON");
    }
    public void update(String hex){
        Log.d("haha", "update:hex ::");

    }
    private final androidx.activity.result.ActivityResultLauncher<Intent> ActivityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null && data.getData() != null) {
//                        Uri imageUri = data.getData();
//                        imageView.setImageURI(imageUri); // Example: Set image to an ImageView
                        Toast.makeText(getApplicationContext(), "connected", Toast.LENGTH_LONG).show();
                    }
                }
            });

}