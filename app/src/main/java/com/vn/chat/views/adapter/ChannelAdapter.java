package com.vn.chat.views.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.vn.chat.R;
import com.vn.chat.common.utils.ImageUtils;
import com.vn.chat.data.Channel;
import com.vn.chat.views.activity.HomeActivity;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("NewApi")
public class ChannelAdapter extends ArrayAdapter<Channel> {

    private static final Integer RES_ID = R.layout.item_channel;
    private HomeActivity activity;
    private List<Channel> channels;
    private ViewHolder viewHolder;

    public ChannelAdapter(@NonNull HomeActivity activity, @NonNull List<Channel> list) {
        super(activity, RES_ID, list);
        this.activity = activity;
        this.channels = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(RES_ID, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.showData(channels.get(position));
        viewHolder.actionView(channels.get(position));
        return convertView;
    }

    @Override
    public int getCount() {
        return channels.size();
    }

    private class ViewHolder {
        private View view;
        private ImageView ivAvatar;
        private TextView tvName, tvContent, tvTime;

        public ViewHolder(View view){
            this.view = view;
            connectView();
        }

        private void connectView(){
            this.ivAvatar = this.view.findViewById(R.id.iv_thumb);
            this.tvName = this.view.findViewById(R.id.tv_name);
            this.tvContent = this.view.findViewById(R.id.tv_content);
            this.tvTime = this.view.findViewById(R.id.tv_time);
//            this.tvCount = this.view.findViewById(R.id.tv_count);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        public void showData(Channel chanel){
            ImageUtils.loadUrl(activity, this.ivAvatar, chanel.getAvatarUrl());
            this.tvName.setText(chanel.getName());
            this.tvContent.setText(chanel.getLatestMessage().getSender().getName()+":"+chanel.getLatestMessage().getContent());
//            this.tvTime.setText(chanel.getLatestMessage().getCreatedDate());
        }

        public void actionView(Channel channel){
            this.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.setFragmentMessage(channel);
                }
            });
        }
    }
}
