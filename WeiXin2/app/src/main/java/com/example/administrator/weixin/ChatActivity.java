package com.example.administrator.weixin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.weixin.msg.Msg;
import com.example.administrator.weixin.msg.MsgAdapter;
import com.example.administrator.weixin.socket.ClientThread;
import com.example.administrator.weixin.sqlite.service.SqliteOperator;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity implements TextWatcher{
    private RecyclerView recyclerview;
    private EditText et_input;
    private ArrayList<Msg> mMsgList;
    private MsgAdapter mMsgAdapter;
    private ImageView bt_send;
    private TextView nickname;
    private SqliteOperator sqliteOperator;

    private Handler handler;
    private ClientThread clientThread;

    private String sendId;
    private String receiveId;
    private String name;

    private String flag = "-";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        initView();
        initData();
        initAdapter();

        Intent intent = getIntent();
        name = intent.getStringExtra("nickname");
        sendId = intent.getStringExtra("sendId");
        receiveId = intent.getStringExtra("receiveId");
        nickname.setText(name);

        et_input.addTextChangedListener(this);

        sqliteOperator = new SqliteOperator(this);

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 0x123){
                    String content = (String) msg.obj;
                    System.out.println("content============="+content);
                    String args[] = content.split(flag);
                    System.out.println(args.length);
                    String userId = args[0];
                    String text = args[1];
                    if(sendId.equals(userId)){  //如果是发给自己的消息
                        mMsgList.add(new Msg(text, Msg.TYPE_RECEIVE));
                        //刷新
                        mMsgAdapter.notifyDataSetChanged();
                        // 定位到最后一行
                        recyclerview.scrollToPosition(mMsgList.size() - 1);
                    }
                }
            }
        };

        clientThread = new ClientThread(handler);
        new Thread(clientThread).start();
        System.out.println("clientThread开始启动……");

        bt_send.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String content = et_input.getText().toString().trim();
               // 如果用户没有输入,则是一个空串""
               if (!content.isEmpty()) {
                   String sendContent = receiveId+flag+content;
                   try{
                       System.out.println("进入Click方法……");
                       Message msg = new Message();
                       msg.obj = sendContent;
                       msg.what = 0x345;
                       clientThread.revHandler.sendMessage(msg);
                       mMsgList.add(new Msg(content, Msg.TYPE_SEND));
                       // 通知数据适配器刷新界面
                       mMsgAdapter.notifyDataSetChanged();
                       // 定位到最后一行
                       recyclerview.scrollToPosition(mMsgList.size() - 1);
                       // 输入框置空
                       et_input.setText("");
                   }catch (Exception e){
                       e.printStackTrace();
                   }
               }
           }

        });
    }

    private void initAdapter() {
        mMsgAdapter = new MsgAdapter(mMsgList);
        recyclerview.setAdapter(mMsgAdapter);
    }

    /**
     * 初始化数据源
     */
    private void initData() {
        mMsgList = new ArrayList<>();
        mMsgList.add(new Msg("Hello!", Msg.TYPE_RECEIVE));
        mMsgList.add(new Msg("Hello!", Msg.TYPE_SEND));
        mMsgList.add(new Msg("吃饭了吗", Msg.TYPE_RECEIVE));
    }

    /**
     * 初始化控件
     */
    private void initView() {
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        et_input = (EditText) findViewById(R.id.sendText);
        bt_send = (ImageView) findViewById(R.id.sendOrMore);
        nickname = (TextView)findViewById(R.id.nickname);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(layoutManager);

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String content = et_input.getText().toString();
        if(content.length() == 0){
            bt_send.setImageDrawable(getResources().getDrawable(R.drawable.jia));
        }else{
            bt_send.setImageDrawable(getResources().getDrawable(R.drawable.fasong));
        }
    }
}
