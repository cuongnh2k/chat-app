package com.vn.chat.views.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.vn.chat.R;
import com.vn.chat.common.DataStatic;
import com.vn.chat.common.utils.NetworkUtils;
import com.vn.chat.common.utils.SessionUtils;
import com.vn.chat.common.view.icon.TextViewAwsSo;
import com.vn.chat.data.Device;
import com.vn.chat.viewmodel.AuthViewModel;
import com.vn.chat.views.fragment.auth.FragmentDeviceConfirm;
import com.vn.chat.views.fragment.auth.FragmentSignIn;
import com.vn.chat.views.fragment.auth.FragmentSignUp;
import com.vn.chat.views.fragment.auth.FragmentUserConfirm;

import java.util.Map;
import java.util.UUID;

public class AuthActivity extends CommonActivity {

    private AuthViewModel authViewModel;
    private FragmentSignIn fragmentSignIn;
    private FragmentSignUp fragmentSignUp;
    private FragmentDeviceConfirm fragmentDeviceConfirm;
    private FragmentUserConfirm fragmentUserConfirm;
    private TextViewAwsSo twaBtnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        this.init();
        this.actionView();
        if(NetworkUtils.isNetworkAvailable(this)){
            loadSession();
        }else{
            Toast.makeText(this, "No internet", Toast.LENGTH_SHORT).show();
            setFragmentSignIn();
        }
    }

    private void init(){
        this.authViewModel = ViewModelProviders.of(AuthActivity.this).get(AuthViewModel.class);
        this.twaBtnBack = findViewById(R.id.twa_btn_back);
    }

    private void actionView(){
        this.twaBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void loadSession(){
        String strJson = SessionUtils.get(this, DataStatic.SESSION.KEY.AUTH, "{}");
        Device auth = new Gson().fromJson(strJson, Device.class);
        if(auth != null && auth.getAccessToken() != null){
            DataStatic.AUTHOR.ACCESS_TOKEN = auth.getAccessToken();
            DataStatic.AUTHOR.REFRESH_TOKEN = auth.getRefreshToken();
            DataStatic.AUTHOR.DEVICE_ID = auth.getDeviceId();
            startActivity(new Intent(this, HomeActivity.class));
        }else{
            setFragmentSignIn();
        }
    }

    public AuthViewModel getAuthViewModel(){
        return this.authViewModel;
    }

    public void setFragmentSignIn(){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if(fragmentSignIn == null){
            fragmentSignIn = new FragmentSignIn(AuthActivity.this);
        }
        transaction.replace(R.id.layout_content, fragmentSignIn);
        transaction.commitAllowingStateLoss();
    }

    public void setFragmentSignUp(){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if(fragmentSignUp == null){
            fragmentSignUp = new FragmentSignUp(AuthActivity.this);
        }
        transaction.replace(R.id.layout_content, fragmentSignUp).addToBackStack(DataStatic.STACK_APP);
        transaction.commitAllowingStateLoss();
    }

    public void setFragmentDeviceConfirm(String deviceId){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if(fragmentDeviceConfirm == null){
            fragmentDeviceConfirm = new FragmentDeviceConfirm(AuthActivity.this, deviceId);
        }
        transaction.replace(R.id.layout_content, fragmentDeviceConfirm).addToBackStack(DataStatic.STACK_APP);
        transaction.commitAllowingStateLoss();
    }

    public void setFragmentUserConfirm(String userId){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if(fragmentUserConfirm == null){
            fragmentUserConfirm = new FragmentUserConfirm(AuthActivity.this, userId);
        }
        transaction.replace(R.id.layout_content, fragmentUserConfirm).addToBackStack(DataStatic.STACK_APP);
        transaction.commitAllowingStateLoss();
    }
}