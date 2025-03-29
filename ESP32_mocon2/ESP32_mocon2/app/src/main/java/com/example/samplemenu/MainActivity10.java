package com.example.samplemenu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity10 extends AppCompatActivity {
    private Button btn;
    private RadioGroup rg;
    private String search_engine = "https://www.google.com/search?q=";
    private EditText input_url;
    private RadioButton google;
    private ImageView meta, youtube, wikipedia;
    private ImageView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main10);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btn = findViewById(R.id.eng);
        rg = findViewById(R.id.rb);
        input_url = findViewById(R.id.url_input);
        google = findViewById(R.id.google);
        meta = findViewById(R.id.meta);
        youtube = findViewById(R.id.youtube);
        wikipedia = findViewById(R.id.wikipedia);
        search = findViewById(R.id.search);
        google.setChecked(true);
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

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity10.this, MainActivity.class);
                intent.putExtra("search_engine", search_engine);
                intent.putExtra("text", input_url.getText().toString());
                startActivity(intent);
            }
        });

        meta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity10.this, MainActivity.class);
                intent.putExtra("search_engine", search_engine);
                intent.putExtra("text", input_url.getText().toString());
                intent.putExtra("shortcut","www.meta.ai");
                startActivity(intent);
            }
        });

        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity10.this, MainActivity.class);
                intent.putExtra("search_engine", search_engine);
                intent.putExtra("text", input_url.getText().toString());
                intent.putExtra("shortcut","www.youtube.com");
                startActivity(intent);
            }
        });

        wikipedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity10.this, MainActivity.class);
                intent.putExtra("search_engine", search_engine);
                intent.putExtra("text", input_url.getText().toString());
                intent.putExtra("shortcut","www.wikipedia.org");
                startActivity(intent);
            }
        });

        input_url.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_DONE) {
                    Intent intent = new Intent(MainActivity10.this, MainActivity.class);
                    intent.putExtra("search_engine", search_engine);
                    intent.putExtra("text", input_url.getText().toString());
                    startActivity(intent);
                    return true;
                }
                return false;
            }

        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rg.getVisibility() == View.VISIBLE)
                    rg.setVisibility(View.INVISIBLE);
                else
                    rg.setVisibility(View.VISIBLE);

            }

        });

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.google) {
                    search_engine = "https://www.google.com/search?q=";
                    rg.setVisibility(View.INVISIBLE);

                } else if (checkedId == R.id.brave) {
                    search_engine = "https://search.brave.com/search?q=";
                    rg.setVisibility(View.INVISIBLE);

                } else if (checkedId == R.id.duckduckgo) {
                    search_engine = "https://www.duckduckgo.com/?q=";
                    rg.setVisibility(View.INVISIBLE);

                } else if (checkedId == R.id.bing) {
                    search_engine = "https://www.bing.com/search?q=";
                    rg.setVisibility(View.INVISIBLE);

                }
                Log.d("TAG", "onCheckedChanged:" + search_engine);
            }
        });
    }
    public void startServer() {

        Log.d("haha", "startServere::clientPort ");

    }
}