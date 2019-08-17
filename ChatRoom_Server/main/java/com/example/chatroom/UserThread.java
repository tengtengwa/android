package com.example.chatroom;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class UserThread implements Runnable {

    private Socket skt;
    private ArrayList<Socket> sktList;
    private OutputStream os;
    private InputStream is;

    public UserThread(Socket socket, ArrayList<Socket> socketList) {
        skt = socket;
        sktList = socketList;
    }


    @Override
    public void run() {
        byte[] arr = new byte[8*1024];
        try {
            os = skt.getOutputStream();
            is = skt.getInputStream();

            while (true) {
                int len;
                while ((len = is.read(arr)) != -1) {    //服务器接收到数据
                    for (Socket s : sktList) {
                        if (s.equals(skt)) {                        //跳过sktList中自己的socket
                            continue;
                        }
                        OutputStream ops = s.getOutputStream();     //给群聊内每个人创建输出流
                        try {
                            ops.write(arr);
                            break;
                        } catch (IOException e) {
                            MainActivity.socketList.remove(s);
                            sktList.remove(s);
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}


