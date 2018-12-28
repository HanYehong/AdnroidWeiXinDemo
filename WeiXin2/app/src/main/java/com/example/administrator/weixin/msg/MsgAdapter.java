package com.example.administrator.weixin.msg;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.weixin.R;

import java.util.ArrayList;

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {
    private ArrayList<Msg> mMsgList;

    public MsgAdapter(ArrayList<Msg> mMsgList) {
        this.mMsgList = mMsgList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.recyclerview_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Msg msg = mMsgList.get(position);
        if (msg.type == Msg.TYPE_RECEIVE) {
            holder.tv_receive.setVisibility(View.VISIBLE);
            holder.tv_send.setVisibility(View.GONE);
            holder.tv_receive.setText(msg.content);
        } else {
            holder.tv_send.setVisibility(View.VISIBLE);
            holder.tv_receive.setVisibility(View.GONE);
            holder.tv_send.setText(msg.content);
        }
    }

    @Override
    public int getItemCount() {
        return mMsgList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_receive;
        private TextView tv_send;

        ViewHolder(View itemView) {
            super(itemView);
            tv_receive = (TextView) itemView.findViewById(R.id.tv_receive);
            tv_send = (TextView) itemView.findViewById(R.id.tv_send);
        }
    }
}
