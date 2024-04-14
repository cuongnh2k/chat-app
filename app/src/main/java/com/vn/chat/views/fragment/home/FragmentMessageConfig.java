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
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.vn.chat.R;
import com.vn.chat.common.DataStatic;
import com.vn.chat.common.utils.ImageUtils;
import com.vn.chat.common.utils.RestUtils;
import com.vn.chat.common.view.icon.TextViewAwsRe;
import com.vn.chat.data.Channel;
import com.vn.chat.views.activity.HomeActivity;
import com.vn.chat.views.dialog.DialogEditGroup;
import com.vn.chat.views.dialog.DialogGroupAddMember;
import com.vn.chat.views.dialog.DialogGroupMembers;
import com.vn.chat.views.dialog.DialogGroupRequestList;

@SuppressLint("ValidFragment")
public class FragmentMessageConfig extends Fragment {

    private static final Integer RES_ID = R.layout.fragment_message_config;
    private HomeActivity activity;
    private LinearLayout btnMembers, btnRequestList, btnAddToGroup, btnMedias, btnOutGroup;
    private ImageView ivThumb;
    private TextView tvName;
    private TextViewAwsRe btnEdit;
    private View view;
    private Channel channel;
    private DialogEditGroup dialogEditGroup;
    private DialogGroupMembers dialogGroupMembers;

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
        this.btnOutGroup = this.view.findViewById(R.id.btn_out_group);
        this.ivThumb = this.view.findViewById(R.id.iv_thumb);
        this.tvName = this.view.findViewById(R.id.tv_name);
        this.btnEdit = this.view.findViewById(R.id.btn_edit);

        activity.getToolbar().getTwaBtnConfig().setVisibility(View.GONE);
        activity.getToolbar().getTwaSearch().setVisibility(View.GONE);

    }

    private void loadData(){
        tvName.setText(channel.getName());
        ImageUtils.loadUrl(activity, this.ivThumb, channel.getAvatarUrl());
        if(channel.getType().equalsIgnoreCase("FRIEND")){
            btnMembers.setVisibility(View.GONE);
            btnRequestList.setVisibility(View.GONE);
            btnAddToGroup.setVisibility(View.GONE);
        }

        if(channel.isAdmin() || !channel.getType().equals("GROUP")){
            this.btnOutGroup.setVisibility(View.GONE);
        }else{
            this.btnOutGroup.setVisibility(View.VISIBLE);
        }

        if(channel.isAdmin() && channel.getType().equals("GROUP")){
            btnEdit.setVisibility(View.VISIBLE);
        }else{
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
                dialogGroupMembers = new DialogGroupMembers(activity, channel);
                dialogGroupMembers.show();
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

        this.btnOutGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Channel c = new Channel();
                c.setChannelId(channel.getId());
                c.setStatus(DataStatic.STATUS.CANCEL);
                c.setUserId(DataStatic.AUTHOR.USER_INFO.getId());
                activity.getHomeViewModel().postReact(c).observe(activity, res -> {
                    if(RestUtils.isSuccess(res)){
                        Toast.makeText(activity, "Out group successful", Toast.LENGTH_SHORT).show();
                        activity.setFragmentHome();
                    }else{
                        Toast.makeText(activity, "Request fail", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        this.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogEditGroup = new DialogEditGroup(activity, channel);
                dialogEditGroup.show();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public DialogEditGroup getDialogEditGroup() {
        return dialogEditGroup;
    }

    public void updateInfo(Channel c){
        channel.setName(c.getName());
        channel.setAvatarUrl(c.getAvatarUrl());
        tvName.setText(channel.getName());
        ImageUtils.loadUrl(activity, this.ivThumb, channel.getAvatarUrl());
    }

    public DialogGroupMembers getDialogGroupMembers() {
        return dialogGroupMembers;
    }
}
