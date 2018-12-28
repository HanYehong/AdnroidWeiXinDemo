package com.example.administrator.weixin.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class SocketServer {

    private static int port = 30000;

    /**
     * 保存所有客户端的Socket
     */
    public static ArrayList<Socket> socketList = new ArrayList<>();

    public static void startServer() throws IOException {
        ServerSocket ss = new ServerSocket(port);
        while(true){
            Socket s = ss.accept();
            socketList.add(s);
            new Thread(new ServerThread(s)).start();
        }
    }

    public static void main(String args[]) throws IOException{
        startServer();
    }
}
