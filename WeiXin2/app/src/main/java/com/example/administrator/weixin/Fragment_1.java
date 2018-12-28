package com.example.administrator.weixin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.weixin.data.Data;
import com.example.administrator.weixin.okHttp3.OkManager;
import com.example.administrator.weixin.sqlite.domain.User;

import okhttp3.OkHttpClient;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_1 extends Fragment implements View.OnClickListener{
    private LinearLayout fragment;
    private String nickname[] = {"Donkey","爸爸","妈妈"};
    private String showChatString[] = {"[图片]","吃饭了吗","哈哈"};
    private String chatTimeString[] = {"10:30","09:01","昨天"};
    private int txId[] = {R.drawable.touxiang_1,R.drawable.touxiang_2,R.drawable.touxiang_3};
    private OkManager manager;
    private OkHttpClient clients;
    private final static String Tag = Fragment_1.class.getSimpleName();

    public Fragment_1() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_1, container, false);

        fragment = (LinearLayout)view.findViewById(R.id.chatList);

        for(int i = 0; i < 3; i++){
            LinearLayout linearLayout = new LinearLayout(view.getContext());
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            //设置边距
            linearLayout.setBackgroundColor(getResources().getColor(R.color.fontcolor));
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setLayoutParams(layoutParams);

            ImageView imageView = new ImageView(view.getContext());
            LinearLayout.LayoutParams imageViewParams=new LinearLayout.LayoutParams(
                    140, 140);
            imageViewParams.setMargins(20,10,10,10);
            imageView.setLayoutParams(imageViewParams);
            imageView.setImageDrawable(getResources().getDrawable(txId[i]));

            LinearLayout linearLayout2 = new LinearLayout(view.getContext());
            LinearLayout.LayoutParams layoutParams2=new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            linearLayout2.setOrientation(LinearLayout.VERTICAL);
            linearLayout2.setLayoutParams(layoutParams2);

            TextView textView1 = new TextView(view.getContext());
            LinearLayout.LayoutParams textViewParams1=new LinearLayout.LayoutParams(
                    750, LinearLayout.LayoutParams.WRAP_CONTENT);
            textViewParams1.setMargins(10,20,0,0);
            textView1.setLayoutParams(textViewParams1);
            textView1.setTextSize(15);
            textView1.setText(nickname[i]);

            TextView textView2 = new TextView(view.getContext());
            LinearLayout.LayoutParams textViewParams2=new LinearLayout.LayoutParams(
                    750, LinearLayout.LayoutParams.WRAP_CONTENT);
            textViewParams2.setMargins(10,23,0,0);
            textView2.setLayoutParams(textViewParams2);
            textView2.setTextSize(10);
            textView2.setText(showChatString[i]);

            TextView textView3 = new TextView(view.getContext());
            LinearLayout.LayoutParams textViewParams3=new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            textViewParams3.setMargins(0,20,0,0);
            textView3.setLayoutParams(textViewParams3);
            textView3.setTextSize(12);
            textView3.setText(chatTimeString[i]);



            View view1 = new View(view.getContext());
            LinearLayout.LayoutParams viewParams=new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 1);
            view1.setLayoutParams(viewParams);
            view1.setBackgroundColor(getResources().getColor(R.color.littleGravy));

            linearLayout2.addView(textView1);
            linearLayout2.addView(textView2);
            linearLayout.addView(imageView);
            linearLayout.addView(linearLayout2);
            linearLayout.addView(textView3);
            linearLayout.setId(i);
            fragment.addView(linearLayout);
            fragment.addView(view1);

            linearLayout.setOnClickListener(this);
        }

        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(),ChatActivity.class);
        intent.putExtra("nickname",nickname[v.getId()]);
        intent.putExtra("sendId", Data.user.getUserId());
        intent.putExtra("receiveId","happy123");
        startActivity(intent);

    }

}
