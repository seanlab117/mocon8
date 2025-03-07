package com.example.samplemenu;

import static android.system.OsConstants.SOCK_STREAM;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class MainActivity4 extends AppCompatActivity {

    EditText editText;
    TextView textView, textView2;
    Handler handler = new Handler();
    Socket sock1;
    ServerSocket server1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String data = editText.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        send(data);
                    }
                }).start();
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
        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        stopServer();
                        openBTSCAN();

                    }
                }).start();
            }
        });



    }



    public void printClientLog(final String data) {
        Log.d("MainActivity4", data);

        handler.post(new Runnable() {
            @Override
            public void run() {
                textView.append(data + "\n");
            }
        });
    }

    public void printServerLog(final String data) {
        Log.d("MainActivity4", data);
        Log.d("haha", "MainActivity4"+data);
        //Log.d("haha","MainActivity", data);

        handler.post(new Runnable() {
            @Override
            public void run() {
                textView2.append(data + "\n");
            }
        });
    }

    public void send(String data) {
        try {
            int portNumber = 3001;
            Socket sock = new Socket("localhost", portNumber);
            Log.d("haha", "client.start()::"+sock.getRemoteSocketAddress() );//sock.getRemoteSocketAddress()
            printClientLog("소켓 연결함");

            ObjectOutputStream outStream = new ObjectOutputStream(sock.getOutputStream());

            outStream.writeObject(data);
            //outStream.writeObject("hello world");
            Log.d("send", data);
            outStream.flush();
            printClientLog("데이터 전송함");

            ObjectInputStream insTream = new ObjectInputStream(sock.getInputStream());
            printClientLog("서버로부터 받음 : " + insTream.readObject());


            sock.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void startServer() {
        try {
            int portNumber = 3001;

            int backlog=10;

            //PF_INET, SOCK_STREAM, 0)
            //ServerSocket server = new ServerSocket(portNumber);
            ServerSocket server = new ServerSocket(portNumber,backlog);//,inetaddres);
            printServerLog("서버 시작함: " + portNumber);
            server1=server;
            Log.d("haha", "server.start()::" +server1);
//            InputStream in_s =
//                    getClass().getClassLoader().getResourceAsStream("posts.json");
            while (true) { // 클라이언트 접속 대기
                Socket sock = server.accept(); //접속 요청 오면 accept 메서드를 통해 소켓 객체 반환
                Log.d("haha", "sock server accept::"+sock.toString());
                InetAddress clientHost = sock.getLocalAddress(); // 클라이언트 연결 정보 확인 가능
                int clientPort = sock.getPort(); // 클라이언트 포트 번호 확인
                Log.d("haha", "sock server create::clientPort ");


                printServerLog("클라이언트 연결됨: " + clientHost + " : " + clientPort);
                //url='Click <a href=\"/H\">here</a> to turn the LED on pin 5 on.<br>Click <a href=\"/L\">here</a> to turn the LED on pin 5 off.<br>"'
                ObjectInputStream instream = new ObjectInputStream(sock.getInputStream());


                Object obj = instream.readObject(); // 문자열 받아와
                printServerLog("데이터 받음: " + obj); //

                ObjectOutputStream outstream = new ObjectOutputStream(sock.getOutputStream());

                outstream.writeObject(obj + " from Server."); //from server 라는 문자열 붙여서 클라이언트로 다시 보내
                outstream.flush();
                printServerLog("데이터 보냄.");

                sock.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }


    public void stopServer() {


    }

    void restartServer() {
          openBTSCAN();

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
    private void openBTSCAN() {
        Intent intent = new Intent(this, MainActivity.class);
        ActivityResultLauncher.launch(intent);
        //imagePickerLauncher.launch(intent);
    }
    private void openBTSCAN2( int position) {

        switch (position) {
            case 0: {
                Intent intent = new Intent(this, MainActivity.class);
                ActivityResultLauncher.launch(intent);
            }
            break;
            case 1: {
                Intent intent = new Intent(this, MainActivity2.class);
                ActivityResultLauncher.launch(intent);
                Toast.makeText(getApplicationContext(), "position 1", Toast.LENGTH_LONG).show();
            }
            break;
            case 2: {
                Intent intent = new Intent(this, MainActivity2.class);
                ActivityResultLauncher.launch(intent);
                Toast.makeText(getApplicationContext(), "position 2", Toast.LENGTH_LONG).show();
            }
            break;
            case 3: {
                Intent intent = new Intent(this, MainActivity2.class);
                ActivityResultLauncher.launch(intent);
                Toast.makeText(getApplicationContext(), "position 3", Toast.LENGTH_LONG).show();
            }
            break;
            case 4: {
//               Intent intent = new Intent(this, MainActivity2.class);
//                ActivityResultLauncher.launch(intent);
                Toast.makeText(getApplicationContext(), "position 4", Toast.LENGTH_LONG).show();
            }
            break;
            case 5: {
                Intent intent = new Intent(this, MainActivity4.class);
                ActivityResultLauncher.launch(intent);
                Toast.makeText(getApplicationContext(), "position 5", Toast.LENGTH_LONG).show();
            }
            break;
            case 6: {
                Intent intent = new Intent(this, MainActivity4.class);
                ActivityResultLauncher.launch(intent);
                Toast.makeText(getApplicationContext(), "position 6", Toast.LENGTH_LONG).show();
            }
            break;
            default: {
                Intent intent = new Intent(this, MainActivity4.class);
                ActivityResultLauncher.launch(intent);
                Toast.makeText(getApplicationContext(), "position default", Toast.LENGTH_LONG).show();
            }
            break;
        }
    }

}


