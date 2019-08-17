package com.example.chatroom_client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.OutputStream;
import java.net.Socket;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private OutputStream outputStream = null;
    private Socket socket = null;
    private String ip = "192.168.1.150";
    private Button btn_cnt;
    private EditText et_ip;
    private EditText et_name;
    private EditText et_port;
    private TextView myName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_cnt = findViewById(R.id.btn_cnt);
        et_ip = findViewById(R.id.et_ip);
        et_port = findViewById(R.id.et_port);
        et_name = findViewById(R.id.et_name);
        myName = findViewById(R.id.my_name);

        btn_cnt.setOnClickListener(MainActivity.this);
    }

    public void onClick(View view) {
        String name = et_name.getText().toString();
        if ("".equals(name)) {
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(MainActivity.this, ChatRoom.class);
            intent.putExtra("name", et_name.getText().toString());
            intent.putExtra("ip",et_ip.toString());
            intent.putExtra("port",et_port.toString());
            startActivity(intent);
        }


    }


}

