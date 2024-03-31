package com.vn.chat.views.fragment.home;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.vn.chat.R;
import com.vn.chat.common.utils.ImageUtils;
import com.vn.chat.common.view.icon.TextViewAwsRe;
import com.vn.chat.data.Channel;
import com.vn.chat.views.activity.HomeActivity;
import com.vn.chat.views.dialog.DialogGroupAddMember;
import com.vn.chat.views.dialog.DialogGroupMembers;
import com.vn.chat.views.dialog.DialogGroupRequestList;

@SuppressLint("ValidFragment")
public class FragmentMessageConfig extends Fragment {

    private static final Integer RES_ID = R.layout.fragment_message_config;
    private HomeActivity activity;
    private LinearLayout btnMembers, btnRequestList, btnAddToGroup, btnMedias;
    private ImageView ivThumb;
    private TextView tvName;
    private TextViewAwsRe btnEdit;
    private View view;
    private Channel channel;

    @SuppressLint("ValidFragment")
    public FragmentMessageConfig(HomeActivity mContext, Channel channel){
        this.activity = mContext;
        this.channel = channel;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(RES_ID, container, false);
        this.init();
        this.loadData();
        this.actionView();
        return this.view;
    }

    private void init(){
        this.btnMembers = this.view.findViewById(R.id.btn_members);
        this.btnRequestList = this.view.findViewById(R.id.btn_request_list);
        this.btnAddToGroup = this.view.findViewById(R.id.btn_add_to_group);
        this.btnMedias = this.view.findViewById(R.id.btn_media_list);
        this.ivThumb = this.view.findViewById(R.id.iv_thumb);
        this.tvName = this.view.findViewById(R.id.tv_name);
        this.btnEdit = this.view.findViewById(R.id.btn_edit);
    }

    private void loadData(){
        tvName.setText(channel.getName());
        ImageUtils.loadUrl(activity, this.ivThumb, channel.getAvatarUrl());
        if(channel.getType().equalsIgnoreCase("FRIEND")){
            btnMembers.setVisibility(View.GONE);
            btnRequestList.setVisibility(View.GONE);
            btnAddToGroup.setVisibility(View.GONE);
            btnEdit.setVisibility(View.GONE);
        }
    }

    private void actionView(){
        this.btnMedias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.setFragmentGallery(channel);
            }
        });

        this.btnMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DialogGroupMembers(activity, channel).show();
            }
        });

        this.btnRequestList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DialogGroupRequestList(activity, channel).show();
            }
        });

        this.btnAddToGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DialogGroupAddMember(activity, channel).show();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
