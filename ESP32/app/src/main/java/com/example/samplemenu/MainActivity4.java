package com.example.samplemenu;

import static android.system.OsConstants.SOCK_STREAM;

//import static com.example.samplemenu.TinyWebServer.serverSocket;
import static java.lang.System.out;

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
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class MainActivity4 extends AppCompatActivity {

    EditText editText;
    TextView textView, textView2;
    Handler handler = new Handler();
    Socket sock1=null;
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
            if (sock1 != null) {
                int portNumber = 8080;
                Socket sock = new Socket("0.0.0.0", portNumber);
                Log.d("haha", "client.start()1::" + sock1.getRemoteSocketAddress());//sock.getRemoteSocketAddress()
                printClientLog("소켓 연결함1");

                ObjectOutputStream outStream = new ObjectOutputStream(sock1.getOutputStream());

                outStream.writeObject(data);
                //outStream.writeObject("hello world");
                Log.d("send", data);
                outStream.flush();
                printClientLog("데이터 전송함");

                ObjectInputStream insTream = new ObjectInputStream(sock1.getInputStream());
                printClientLog("서버로부터 받음 : " + insTream.readObject());


                sock1.close();
            } else{
                int portNumber = 8080;
                Socket sock = new Socket("0.0.0.0", portNumber);
                Log.d("haha", "client.start()2::" + sock.getRemoteSocketAddress());
                printClientLog("소켓 연결함2");
                Log.d("haha", "소켓 연결함2::");
                printClientLog("소켓 연결함2"+data);

                ObjectOutputStream outStream = new ObjectOutputStream(sock.getOutputStream());

                outStream.writeObject(data);
                //outStream.writeObject("hello world");
                Log.d("haha","test:data"+data);
                outStream.flush();
                printClientLog("데이터 전송함"+data);

                ObjectInputStream insTream = new ObjectInputStream(sock.getInputStream());
                printClientLog("서버로부터 받음 : " + insTream.readObject());


                sock.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }



    public void startServer() {

        try {
            int portNumber = 8080;
            int backlog=50;

            // sean ServerSocket server = new ServerSocket(portNumber,backlog);//,inetaddres);
            ServerSocket server = new ServerSocket(portNumber,backlog,InetAddress.getByName("0.0.0.0"));
            printServerLog("서버 외부 접속 시작함: " + portNumber);
            server1=server;
            Log.d("haha", "server.start()::" +server1);

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
                //printServerLog("데이터 보냄.");
                printServerLog("데이터 보냄."+obj);

                sock.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopServer();
    }

    public void startServer2() {
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
            try {
                if (server1 != null && !server1.isClosed()) {
                    server1.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
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


