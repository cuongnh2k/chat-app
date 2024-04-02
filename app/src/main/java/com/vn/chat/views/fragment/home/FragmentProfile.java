package com.vn.chat.views.fragment.home;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.vn.chat.R;
import com.vn.chat.common.DataStatic;
import com.vn.chat.common.utils.ImageUtils;
import com.vn.chat.common.utils.RestUtils;
import com.vn.chat.views.activity.AuthActivity;
import com.vn.chat.views.activity.HomeActivity;

@SuppressLint("ValidFragment")
public class FragmentProfile extends Fragment {

    private static final Integer RES_ID = R.layout.fragment_profile;
    private HomeActivity activity;
    private View view;
    private ImageView ivAvatar;
    private EditText etUsername, etFullName, etEmail;
    private Button btnLogout;

    @SuppressLint("ValidFragment")
    public FragmentProfile(HomeActivity mContext){
        this.activity = mContext;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(RES_ID, container, false);
        this.init();
        this.loadUserInfo();
        this.actionView();
        return this.view;
    }

    private void init(){
        this.ivAvatar = this.view.findViewById(R.id.iv_thumb);
        this.etUsername = this.view.findViewById(R.id.et_username);
        this.etFullName = this.view.findViewById(R.id.et_full_name);
        this.etEmail = this.view.findViewById(R.id.et_mail);
        this.btnLogout = this.view.findViewById(R.id.btn_logout);
    }

    private void loadUserInfo(){
        ImageUtils.loadUrl(activity, this.ivAvatar, DataStatic.AUTHOR.USER_INFO.getAvatarUrl());
        this.etUsername.setText(DataStatic.AUTHOR.USER_INFO.getUser());
        this.etFullName.setText(DataStatic.AUTHOR.USER_INFO.getName());
        this.etEmail.setText(DataStatic.AUTHOR.USER_INFO.getEmail());
    }

    private void actionView(){
        this.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.getHomeViewModel().logout().observe(activity, res -> {
                    if(RestUtils.isSuccess(res)){
                        activity.finish();
                        activity.startActivity(new Intent(activity, AuthActivity.class));
                        Toast.makeText(activity, "Logout successful", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
