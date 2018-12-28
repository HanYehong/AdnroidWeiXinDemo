package com.example.administrator.weixin.msg;

public class Msg {
    public static final int TYPE_RECEIVE = 1;
    public static final int TYPE_SEND = 2;
    public String content;
    public int type;

    public Msg(String content, int type) {
        this.content = content;
        this.type = type;
    }
}
