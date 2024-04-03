package com.vn.chat.views.fragment.home;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vn.chat.R;
import com.vn.chat.common.DataStatic;
import com.vn.chat.common.utils.IntentUtils;
import com.vn.chat.common.utils.PermissionUtils;
import com.vn.chat.common.utils.RestUtils;
import com.vn.chat.common.view.icon.TextViewAwsSo;
import com.vn.chat.data.Channel;
import com.vn.chat.data.File;
import com.vn.chat.data.Message;
import com.vn.chat.views.activity.HomeActivity;
import com.vn.chat.views.adapter.MessageAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class FragmentMessage extends Fragment {

    private static final Integer RES_ID = R.layout.fragment_message;
    private HomeActivity activity;
    private RecyclerView rvData;
    private MessageAdapter messageAdapter;
    private EditText etContent;
    private ImageView ivSend, ivAttachFile;
    private TextView tvFileInfo, tvTargetInfo;
    private TextViewAwsSo twaBtnRemoveFile, twaBtnRemoveTarget;
    private View view;
    private List<Message> messages;
    private Channel channel;
    private Message message = new Message();

    @SuppressLint("ValidFragment")
    public FragmentMessage(HomeActivity mContext, Channel channel){
        this.activity = mContext;
        this.channel = channel;
    }

    public Channel getChannel() {
        return channel;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(RES_ID, container, false);
        this.init();
        this.loadMessage();
        this.actionView();
        return this.view;
    }

    private void init(){
        activity.getToolbar().getTwaBtnConfig().setVisibility(View.VISIBLE);
        activity.getToolbar().getTwaBtnBack().setVisibility(View.VISIBLE);
        activity.getToolbar().setNamePage(channel.getName());
        this.messages = new ArrayList<>();
        this.tvFileInfo = view.findViewById(R.id.tv_file_info);
        this.tvTargetInfo = view.findViewById(R.id.tv_target_info);
        this.twaBtnRemoveFile = view.findViewById(R.id.twa_remove_file);
        this.twaBtnRemoveTarget = view.findViewById(R.id.twa_remove_target);
        this.etContent = view.findViewById(R.id.et_content);
        this.ivSend = view.findViewById(R.id.iv_send);
        this.ivAttachFile = view.findViewById(R.id.iv_attach);
        this.rvData = view.findViewById(R.id.rv_data);
        this.messageAdapter = new MessageAdapter(activity, messages);
        this.rvData.setAdapter(this.messageAdapter);
        this.rvData.setLayoutManager(new LinearLayoutManager(activity));
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadMessage(){
        messages.clear();
        messageAdapter.notifyDataSetChanged();
        activity.getHomeViewModel().getMessage(new Message(this.channel.getId())).observe(activity, res -> {
            if(res.getCode().equals(1)){
                if(res.getItems().size() > 0){
                    for(Message message : res.getItems()){
                        messages.add(0, message);
                    }
                    messageAdapter.notifyDataSetChanged();
                    rvData.scrollToPosition(messages.size() - 1);
                }
            }
        });
    }

    public void pushMessage(Message m){
        messages.add(messages.size(), m);
        messageAdapter.notifyDataSetChanged();
        rvData.scrollToPosition(messages.size() - 1);
    }

    private void actionView(){
        ivSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = etContent.getText().toString().trim();
                if(content.length() > 0 || message.getFiles().size() > 0){
                    message.setContent(content);
                    message.setChannelId(channel.getId());
                    message.getSender().setId(DataStatic.AUTHOR.USER_INFO.getId());
                    message.getSender().setUserId(DataStatic.AUTHOR.USER_INFO.getId());

                    activity.getHomeViewModel().postMessage(message).observe(activity, res -> {

                    });
                    etContent.setText("");
                    setFileInfo(null);
                }
            }
        });

        ivAttachFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(PermissionUtils.readExternalStorage(activity)){
                    IntentUtils.chooseImage(activity, IntentUtils.FRAGMENT.MESSAGE);
                }
            }
        });

        twaBtnRemoveFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFileInfo(null);
            }
        });

        twaBtnRemoveTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        activity.getToolbar().getTwaBtnConfig().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.setFragmentMessageConfig(channel);
            }
        });
    }

    public void setFileInfo(File file){
        if(file == null){
            twaBtnRemoveFile.setVisibility(View.GONE);
            tvFileInfo.setVisibility(View.GONE);
            tvFileInfo.setText("");
            message.setFiles(null);
        }else{
            twaBtnRemoveFile.setVisibility(View.VISIBLE);
            tvFileInfo.setVisibility(View.VISIBLE);
            tvFileInfo.setText(file.getName());
            message.addFile(file);
        }
    }

    public void setTargetInfo(Message message){
        if(message == null){
            twaBtnRemoveTarget.setVisibility(View.GONE);
            tvTargetInfo.setVisibility(View.GONE);
            tvTargetInfo.setText("");
            this.message.setParentId(null);
        }else{
            twaBtnRemoveTarget.setVisibility(View.VISIBLE);
            tvTargetInfo.setVisibility(View.VISIBLE);
            tvTargetInfo.setText(message.getContent());
            this.message.setParentId(message.getId());
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        activity.visibleMenu(View.VISIBLE);
        activity.getToolbar().getTwaBtnBack().setVisibility(View.GONE);
        activity.getToolbar().getTwaBtnConfig().setVisibility(View.GONE);
        activity.getToolbar().setNamePage(getResources().getString(R.string.app_name));
    }
}
