package com.example.samplemenu;

import static java.lang.System.out;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity6 extends AppCompatActivity {

    TextView textViewColorHex, textViewRGB, textViewRed, textViewGreen, textViewBlue;
    ImageView imageView;
    SeekBar seekBarRed, seekBarGreen, seekBarBlue;
    int redValue = 64;
    int greenValue = 128;
    int blueValue = 0;

    ServerSocket server1=null;
    Socket sock1=null;
    TextView textViewheader;

    private void updateColor(){
        int color = Color.rgb(redValue, greenValue, blueValue);
        imageView.setBackgroundColor(color);

        String hex = String.format("#%02X%02X%02X", redValue, greenValue, blueValue);
        textViewColorHex.setText(hex);
        Log.d("haha", "updateColor:hex 1::"+hex);
        update(hex);
        Log.d("haha", "updateColor:hex 2::"+hex);
       // Log.d("haha", "obj: "+obj);
        String redGreenBlue = String.format("(%d, %d, %d)", redValue, greenValue, blueValue);
        textViewRGB.setText(redGreenBlue);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main6);

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

    }
    public void startServer() {

        try {
            int portNumber = 8080;

            int backlog=10;

            //PF_INET, SOCK_STREAM, 0)
            //ServerSocket server = new ServerSocket(portNumber);
            ServerSocket server = new ServerSocket(portNumber,backlog);//,inetaddres);
//            printServerLog("서버 시작함: " + portNumber);
            server1=server;
            textViewheader.setText("Server is ON");
            Log.d("haha", "server.start()::" +server1);
//            InputStream in_s =
//                    getClass().getClassLoader().getResourceAsStream("posts.json");
             while (true) { // 클라이언트 접속 대기
                Socket sock = server.accept(); //접속 요청 오면 accept 메서드를 통해 소켓 객체 반환
                Log.d("haha", "sock server accept::"+sock.toString());
                InetAddress clientHost = sock.getLocalAddress(); // 클라이언트 연결 정보 확인 가능
                int clientPort = sock.getPort(); // 클라이언트 포트 번호 확인
                Log.d("haha", "sock server create::clientPort ");


//                printServerLog("클라이언트 연결됨: " + clientHost + " : " + clientPort);
                //url='Click <a href=\"/H\">here</a> to turn the LED on pin 5 on.<br>Click <a href=\"/L\">here</a> to turn the LED on pin 5 off.<br>"'
                ObjectInputStream instream = new ObjectInputStream(sock.getInputStream());


                Object obj = instream.readObject(); // 문자열 받아와
//                printServerLog("데이터 받음: " + obj); //

                ObjectOutputStream outstream = new ObjectOutputStream(sock.getOutputStream());

                outstream.writeObject(obj + " from Server."); //from server 라는 문자열 붙여서 클라이언트로 다시 보내
                outstream.flush();
//                printServerLog("데이터 보냄.");

                sock.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }
    public void update(String hex){
        Log.d("haha", "update:hex ::"+hex);
        try {
            if (server1 !=null) {
                Log.d("haha", "sock1 != null::"+hex);
                Socket sock = server1.accept(); //접속 요청 오면 accept 메서드를 통해 소켓 객체 반환
                Log.d("haha", "sock server accept::"+sock.toString());
                InetAddress clientHost = sock.getLocalAddress(); // 클라이언트 연결 정보 확인 가능
                int clientPort = sock.getPort(); // 클라이언트 포트 번호 확인
                Log.d("haha", "sock server create::clientPort ");


//                printServerLog("클라이언트 연결됨: " + clientHost + " : " + clientPort);
                //url='Click <a href=\"/H\">here</a> to turn the LED on pin 5 on.<br>Click <a href=\"/L\">here</a> to turn the LED on pin 5 off.<br>"'
                ObjectInputStream instream = new ObjectInputStream(sock1.getInputStream());


                Object obj = instream.readObject(); // 문자열 받아와
//                printServerLog("데이터 받음: " + obj); //

                ObjectOutputStream outstream = new ObjectOutputStream(sock1.getOutputStream());

                //outstream.writeObject(obj + " from Server."); //from server 라는 문자열 붙여서 클라이언트로 다시 보내
                outstream.writeObject(hex + " from Server.");
                outstream.flush();
//                printServerLog("데이터 보냄.");

                sock1.close();
            } else
            {
                Toast.makeText(MainActivity6.this, "서버를 구동해 주세요: " , Toast.LENGTH_SHORT).show();
            }



        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        stopServer();
    }

    public void stopServer() {
        try {
            if (server1 != null && !server1.isClosed()) {
                server1.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
