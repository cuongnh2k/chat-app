package com.vn.chat.views.adapter;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.vn.chat.R;
import com.vn.chat.common.DataStatic;
import com.vn.chat.common.utils.ImageUtils;
import com.vn.chat.common.utils.RestUtils;
import com.vn.chat.common.view.icon.TextViewAwsSo;
import com.vn.chat.data.Channel;
import com.vn.chat.data.User;
import com.vn.chat.views.activity.CommonActivity;
import com.vn.chat.views.activity.FriendRequestActivity;
import com.vn.chat.views.activity.HomeActivity;

import java.util.Arrays;
import java.util.List;

@SuppressLint("NewApi")
public class ContactAdapter extends ArrayAdapter<Channel> {

    private static final Integer RES_ID = R.layout.item_contact;
    private CommonActivity activity;
    private List<Channel> contacts;
    private ViewHolder viewHolder;
    private String tmpChannelId;

    public ContactAdapter(@NonNull CommonActivity activity, @NonNull List<Channel> list) {
        super(activity, RES_ID, list);
        this.activity = activity;
        this.contacts = list;
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
        viewHolder.showData(contacts.get(position));
        viewHolder.actionView(contacts.get(position));
        return convertView;
    }

    public void setTmpChannelId(String tmpChannelId) {
        this.tmpChannelId = tmpChannelId;
    }

    private class ViewHolder {
        private View view;
        private ImageView ivAvatar;
        private TextView tvName, tvMail;
        private TextViewAwsSo btnRequest, btnAccept, btnReject, btnRemove, btnChangeAdmin;
        private CheckBox cbCheck;

        public ViewHolder(View view){
            this.view = view;
            connectView();
        }

        private void connectView(){
            this.ivAvatar = this.view.findViewById(R.id.iv_thumb);
            this.tvName = this.view.findViewById(R.id.tv_name);
            this.tvMail = this.view.findViewById(R.id.tv_mail);
            this.btnRequest = this.view.findViewById(R.id.btn_request);
            this.btnAccept = this.view.findViewById(R.id.btn_accept);
            this.btnReject = this.view.findViewById(R.id.btn_reject);
            this.btnRemove = this.view.findViewById(R.id.btn_remove);
            this.btnChangeAdmin = this.view.findViewById(R.id.btn_change_admin);
            this.cbCheck = this.view.findViewById(R.id.cb_check);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        public void showData(Channel chanel){
            ImageUtils.loadUrl(activity, this.ivAvatar, chanel.getAvatarUrl());
            this.tvName.setText(chanel.getName());
            this.tvMail.setText(chanel.getEmail());

            if(chanel.isRequest()){
                this.btnRequest.setVisibility(View.VISIBLE);
            }else{
                this.btnRequest.setVisibility(View.GONE);
            }

            if(chanel.isAccept()){
                this.btnAccept.setVisibility(View.VISIBLE);
                this.btnReject.setVisibility(View.VISIBLE);
            }else{
                this.btnAccept.setVisibility(View.GONE);
                this.btnReject.setVisibility(View.GONE);
            }

            if(chanel.isCreateGroup()){
                this.cbCheck.setVisibility(View.VISIBLE);
            }else{
                this.cbCheck.setVisibility(View.GONE);
            }

            if(chanel.isCancel()){
                this.btnRemove.setVisibility(View.VISIBLE);
            }else{
                this.btnRemove.setVisibility(View.GONE);
            }

            if(chanel.isChangeAdmin()){
                this.btnChangeAdmin.setVisibility(View.VISIBLE);
            }else{
                this.btnChangeAdmin.setVisibility(View.GONE);
            }
        }

        private void postReactEvent(Channel channel){
            if(activity instanceof FriendRequestActivity){
                ((FriendRequestActivity) activity).getFriendRequestViewModel().postReact(channel).observe(activity, res -> {
                    if(RestUtils.isSuccess(res)){
                        Toast.makeText(activity, "Update successful", Toast.LENGTH_SHORT).show();
                    }
                });
            } else if (activity instanceof HomeActivity) {
                if(((HomeActivity) activity).getFragmentTarget() == ((HomeActivity) activity).getFragmentMessageConfig()){
                    ((HomeActivity) activity).getHomeViewModel().postReactOwner(channel).observe(activity, res -> {
                        if(RestUtils.isSuccess(res)){
                            Toast.makeText(activity, "Update successful", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else if(((HomeActivity) activity).getFragmentTarget() == ((HomeActivity) activity).getFragmentContact()){
                    ((HomeActivity) activity).getHomeViewModel().postReact(channel).observe(activity, res -> {
                        if(RestUtils.isSuccess(res)){
                            Toast.makeText(activity, "Update successful", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            this.btnAccept.setVisibility(View.GONE);
            this.btnReject.setVisibility(View.GONE);
            this.btnRemove.setVisibility(View.GONE);
            this.btnChangeAdmin.setVisibility(View.GONE);
        }

        public void actionView(Channel channel){
            this.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(activity instanceof HomeActivity){
                        if(((HomeActivity) activity).getFragmentTarget().equals(((HomeActivity)activity).getFragmentContact()) ||
                                ((HomeActivity) activity).getFragmentTarget().equals(((HomeActivity)activity).getFragmentGroup())){
                            ((HomeActivity) activity).setFragmentMessage(channel);
                        }
                    }
                }
            });

            this.btnRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(activity instanceof HomeActivity){
                        if(((HomeActivity) activity).getFragmentTarget() == ((HomeActivity) activity).getFragmentContact()){
                            ((HomeActivity) activity).getHomeViewModel().makeFriend(new User(channel.getUserId())).observe(activity, res -> {
                                if(RestUtils.isSuccess(res)){
                                    btnRequest.setVisibility(View.GONE);
                                    Toast.makeText(activity, "Request successful", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else if(((HomeActivity) activity).getFragmentTarget() == ((HomeActivity) activity).getFragmentMessageConfig()){
                            channel.setId(tmpChannelId);
                            channel.setUserIds(Arrays.asList(channel.getUserId()));
                            ((HomeActivity) activity).getHomeViewModel().postMember(channel).observe(activity, res ->{
                                if(RestUtils.isSuccess(res)){
                                    btnRequest.setVisibility(View.GONE);
                                    Toast.makeText(activity, "Request successful", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }
            });

            this.btnAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    channel.setStatus(DataStatic.STATUS.ACCEPT);
                    postReactEvent(channel);
                }
            });

            this.btnReject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    channel.setStatus(DataStatic.STATUS.REJECT);
                    postReactEvent(channel);
                }
            });

            this.btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(channel.getChannelId() != null) channel.setId(channel.getChannelId());
                    if(channel.getId() != null) channel.setChannelId(channel.getId());
                    if(channel.getId() == null) channel.setId(tmpChannelId);

                    channel.setStatus(DataStatic.STATUS.CANCEL);
                    postReactEvent(channel);
                }
            });

            this.cbCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    channel.setCreateGroup(b);
                }
            });

            this.btnChangeAdmin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(activity instanceof HomeActivity){
                        channel.setId(tmpChannelId);
                        ((HomeActivity) activity).getHomeViewModel().updateOwner(channel).observe(activity, res -> {
                            if(RestUtils.isSuccess(res)){
                                Toast.makeText(activity, "Change owner successful", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(activity, "Change owner fail", Toast.LENGTH_SHORT).show();
                            }
                            ((HomeActivity) activity).setFragmentHome();
                        });
                    }
                }
            });
        }
    }
}
