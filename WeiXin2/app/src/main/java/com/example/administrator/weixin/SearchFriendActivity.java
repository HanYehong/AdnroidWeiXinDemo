package com.example.administrator.weixin;

import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.weixin.sqlite.domain.User;
import com.example.administrator.weixin.sqlite.service.SqliteOperator;

public class SearchFriendActivity extends AppCompatActivity implements TextWatcher {
    private EditText searchFriend;
    private TextView searchName;
    private LinearLayout goneLayout;
    private RelativeLayout goneNoUser;
    private SqliteOperator sqliteOperator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchfriend);

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        init();

        addlistener();
    }

    private void addlistener() {

        this.goneLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进行数据库查询操作
                String name = searchFriend.getText().toString();
                System.out.println("name======="+name);
                User user = sqliteOperator.findUserByID(name);
                if(user == null){
                    //跳转到用户不存在界面
                    System.out.println("用户不存在");
                    goneNoUser.setVisibility(View.VISIBLE);
                    goneLayout.setVisibility(View.GONE);
                }else{
                    //跳转到用户资料界面
                    System.out.println("用户存在");
                    Intent intent = new Intent(SearchFriendActivity.this,NewFriendInfoActivity.class);
                    intent.putExtra("nickname",user.getNickname());
                    intent.putExtra("userId",user.getUserId());
                    startActivity(intent);
                }
            }
        });

        this.searchFriend.addTextChangedListener(this);

    }

    private void init() {
        this.searchFriend = (EditText)findViewById(R.id.search_friend);
        this.searchName = (TextView)findViewById(R.id.search_name);
        this.goneLayout = (LinearLayout)findViewById(R.id.gone_layout);
        this.goneNoUser = (RelativeLayout)findViewById(R.id.gone_nouser);
        this.sqliteOperator = new SqliteOperator(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
//        System.out.println("被改变");
        String name = searchFriend.getText().toString();
//        System.out.println(name);
        if(name.length()!=0){
            searchName.setText(name);
            if(goneNoUser.getVisibility() == View.GONE)
                goneLayout.setVisibility(View.VISIBLE);
            else
                goneLayout.setVisibility(View.GONE);
        }else{
            searchName.setText("");
            goneLayout.setVisibility(View.GONE);
            goneNoUser.setVisibility(View.GONE);
        }
    }
}
