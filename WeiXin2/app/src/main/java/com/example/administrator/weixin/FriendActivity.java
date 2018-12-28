package com.example.administrator.weixin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

public class FriendActivity extends AppCompatActivity {
    private LinearLayout search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        init();

        this.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FriendActivity.this,SearchFriendActivity.class);
                startActivity(intent);
            }
        });
    }

    private void init() {
        this.search = (LinearLayout)findViewById(R.id.search);
    }
}
