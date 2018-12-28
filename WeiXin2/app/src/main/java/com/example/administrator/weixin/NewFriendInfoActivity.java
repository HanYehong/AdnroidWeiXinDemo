package com.example.administrator.weixin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class NewFriendInfoActivity extends AppCompatActivity {
    private TextView nickname;
    private TextView userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newfriendinfo);

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        init();

        Intent intent = getIntent();

        this.nickname.setText(intent.getStringExtra("nickname"));
        this.userId.setText("微信号："+intent.getStringExtra("userId"));
    }

    private void init() {
        this.nickname = (TextView)findViewById(R.id.search_nickname);
        this.userId = (TextView)findViewById(R.id.search_weixinhao);
    }
}
