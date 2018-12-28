package com.example.administrator.weixin;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.weixin.sqlite.domain.User;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //Fragment
    private Fragment_1 fragment_first;
    private Fragment_2 fragment_second;
    private Fragment_3 fragment_third;

    //底端菜单栏LinearLayout
    private LinearLayout linear_first;
    private LinearLayout linear_second;
    private LinearLayout linear_third;

    //底端菜单栏textView
    private TextView tv_first;
    private TextView tv_second;
    private TextView tv_third;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        //初始化各个控件
        InitView();

        //初始化点击触发事件
        InitEvent();

        //初始化Fragment
        InitFragment(1);

        //将第一个图标设置为选中状态
//        iv_first.setImageResource(R.drawable.find_select);
        tv_first.setTextColor(getResources().getColor(R.color.colorTextViewPress));

        final ImageButton imageButton = (ImageButton)findViewById(R.id.search);
        imageButton.setOnClickListener(this);
    }

    private void InitView(){

        linear_first = (LinearLayout) findViewById(R.id.line1);
        linear_second = (LinearLayout) findViewById(R.id.line2);
        linear_third = (LinearLayout) findViewById(R.id.line3);

//        iv_first = (ImageView) findViewById(R.id.ic_1);
//        iv_second = (ImageView) findViewById(R.id.ic_2);
//        iv_third= (ImageView) findViewById(R.id.ic_3);

        tv_first = (TextView) findViewById(R.id.textview_1);
        tv_second = (TextView) findViewById(R.id.textview_2);
        tv_third = (TextView) findViewById(R.id.textview_3);

    }

    private void InitFragment(int index){
        //v4包下的Fragment，使用getSupportFragmentManager获取
        FragmentManager fragmentManager = getSupportFragmentManager();
        //启动事务
        android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();

        //将所有的Fragment隐藏
        hideAllFragment(transaction);
        switch (index){
            case 1:
                if (fragment_first== null){
                    fragment_first = new Fragment_1();
                    transaction.add(R.id.frame_content,fragment_first);
                }
                else{
                    transaction.show(fragment_first);
                }
                break;

            case 2:
                if (fragment_second== null){
                    fragment_second = new Fragment_2();
                    transaction.add(R.id.frame_content,fragment_second);
                }
                else{
                    transaction.show(fragment_second);
                }
                break;

            case 3:
                if (fragment_third== null){
                    fragment_third = new Fragment_3();
                    transaction.add(R.id.frame_content,fragment_third);
                }
                else{
                    transaction.show(fragment_third);
                }
                break;

        }
        //提交事务
        transaction.commit();
    }
    private void InitEvent(){
        //设置LinearLayout监听
        linear_first.setOnClickListener(this);
        linear_second.setOnClickListener(this);
        linear_third.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        //每次点击之后，将所有的ImageView和TextView设置为未选中
        restartButton();
        //再根据所选，进行跳转页面，并将ImageView和TextView设置为选中
        switch(view.getId()){
            case R.id.line1:
           //     Toast.makeText(MainActivity.this,"first", Toast.LENGTH_SHORT).show();
     //           iv_first.setImageResource(R.drawable.find_select);
                tv_first.setTextColor(getResources().getColor(R.color.colorTextViewPress));
                InitFragment(1);
                break;

            case R.id.line2:
        //        iv_second.setImageResource(R.drawable.wechat_select);
                tv_second.setTextColor(getResources().getColor(R.color.colorTextViewPress));
                InitFragment(2);
                break;

            case R.id.line3:
         //       iv_third.setImageResource(R.drawable.me_select);
                tv_third.setTextColor(getResources().getColor(R.color.colorTextViewPress));
                InitFragment(3);
                break;

            case R.id.search:
                Intent intent = new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intent);
                break;
        }
    }

    //隐藏所有的Fragment
    private void hideAllFragment(android.support.v4.app.FragmentTransaction transaction){
        if (fragment_first!= null){
            transaction.hide(fragment_first);
        }

        if (fragment_second!= null){
            transaction.hide(fragment_second);
        }

        if (fragment_third!= null){
            transaction.hide(fragment_third);
        }

        // transaction.commit();
    }

    //重新设置ImageView和TextView的状态
    private void restartButton(){
        //设置为未点击状态
//        iv_first.setImageResource(R.drawable.find_normal);
//        iv_second.setImageResource(R.drawable.wechat_normal);
//        iv_third.setImageResource(R.drawable.me_normal);

        //设置为灰色
        tv_first.setTextColor(getResources().getColor(R.color.colorTextViewNormal));
        tv_second.setTextColor(getResources().getColor(R.color.colorTextViewNormal));
        tv_third.setTextColor(getResources().getColor(R.color.colorTextViewNormal));
    }
}
