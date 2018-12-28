package com.example.administrator.weixin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.administrator.weixin.sqlite.domain.Friend;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_2 extends Fragment {
    private LinearLayout newFriends;
    private View view;
    private LinearLayout phoneList;

    public Fragment_2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_2, container, false);

        init();

        addListener();

        return view;
    }

    private void addListener() {
        this.phoneList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),PhoneActivity.class);
                startActivity(intent);
            }
        });

        this.newFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),FriendActivity.class);
                startActivity(intent);
            }
        });
    }

    public void init(){
        this.newFriends = (LinearLayout)view.findViewById(R.id.newFriends);
        this.phoneList = (LinearLayout)view.findViewById(R.id.phoneList);
    }

}
