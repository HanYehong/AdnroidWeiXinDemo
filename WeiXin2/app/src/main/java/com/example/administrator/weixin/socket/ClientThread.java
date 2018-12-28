package com.example.administrator.weixin.socket;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class ClientThread implements Runnable {

    private Socket s;

    private Handler handler;

    private String host = "192.168.43.38";

    private int port = 30000;

    public Handler revHandler;

    BufferedReader br = null;

    OutputStream os = null;

    public ClientThread(Handler handler){
        this.handler = handler;
    }

    @Override
    public void run() {
        System.out.println("进入run方法……");
        try{
            s = new Socket(host,port);
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            os = s.getOutputStream();
            new Thread(){
                @Override
                public void run() {
                    String content = null;
                    try{
                        while ( (content = br.readLine() ) != null ){
                            Message msg = new Message();
                            msg.what = 0x123;
                            msg.obj = content;
                            handler.sendMessage(msg);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
            System.out.println("创建第一个线程结束……");
            Looper.prepare();
            System.out.println("创建hander对象开始……");
            revHandler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    if(msg.what == 0x345){
                        try{
                            os.write( ((msg.obj.toString())+"\r\n").getBytes("utf-8") );
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            System.out.println("创建hander对象结束……");
            Looper.loop();
        } catch (SocketTimeoutException e){
            System.out.print("网络连接超时！");
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
