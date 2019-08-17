package com.example.chatroom;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private EditText et_port;
    private Button start;
    private Button end;
    private ServerSocket serverSocket = null;
    private InputStream inputStream;
    private String server_port;
    public static ArrayList<Socket> socketList = new ArrayList<>();
    StringBuilder sb = new StringBuilder();
    private startServer startServer;

    class startServer extends Thread {
        public void run() {
            try {
                serverSocket = new ServerSocket(6666,50, InetAddress.getByName ("192.168.1.150"));
                while (true) {
                    Socket socket = serverSocket.accept();
                    socketList.add(socket);
                    new Thread(new UserThread(socket, socketList)).start();

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_port = findViewById(R.id.et_port);
        start = findViewById(R.id.btn_start);
        end = findViewById(R.id.btn_end);
        et_port.setText("6666");
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                server_port = et_port.getText().toString();
                try {
                    Integer.parseInt(server_port);
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "端口不存在", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                startServer = new startServer();
                startServer.start();
                Toast.makeText(MainActivity.this, "服务器启动成功", Toast.LENGTH_SHORT).show();
                start.setEnabled(false);
                end.setEnabled(true);
            }
        });

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startServer.interrupt();
                socketList.clear();
                try {
                    serverSocket.close();
                    Toast.makeText(MainActivity.this, "服务器关闭成功", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(MainActivity.this, "服务器关闭失败", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                start.setEnabled(true);
                end.setEnabled(false);
            }
        });
    }


}







