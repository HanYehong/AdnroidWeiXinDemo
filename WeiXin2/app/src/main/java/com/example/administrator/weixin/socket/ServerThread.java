package com.example.administrator.weixin.socket;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Iterator;

class ServerThread implements Runnable {

    Socket s = null;

    BufferedReader br = null;

    public ServerThread(Socket s) throws IOException {
        this.s = s;
        br = new BufferedReader(new InputStreamReader(
                s.getInputStream(), "utf-8"));
    }

    @Override
    public void run() {
        try{
            String content = null;
            while( (content = readFromClient() ) != null ){
                Iterator<Socket> it = SocketServer.socketList.iterator();
                for(; ((Iterator) it).hasNext(); ){
                    Socket s = (Socket) ((Iterator) it).next();
                    try{
                        OutputStream os = s.getOutputStream();
                        os.write( (content+"\n").getBytes("utf-8") );
                    }catch (SocketException e){
                        e.printStackTrace();
                        ((Iterator) it).remove();
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private String readFromClient() {
        try{
            return br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            SocketServer.socketList.remove(s);
        }
        return null;
    }
}
