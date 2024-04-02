package com.vn.chat.views.fragment.auth;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.vn.chat.R;
import com.vn.chat.common.DataStatic;
import com.vn.chat.common.response.ApiResponse;
import com.vn.chat.common.utils.RestUtils;
import com.vn.chat.common.utils.StringUtils;
import com.vn.chat.data.User;
import com.vn.chat.views.activity.AuthActivity;
import com.vn.chat.views.activity.HomeActivity;

import java.util.Map;

@SuppressLint("ValidFragment")
public class FragmentSignIn extends Fragment {

    private static final Integer RES_ID = R.layout.fragment_sign_in;
    private AuthActivity context;
    private View view;

    private EditText etUsername, etPassword;
    private Button btnLogin, btnRegister;

    @SuppressLint("ValidFragment")
    public FragmentSignIn(AuthActivity mContext){
        this.context = mContext;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(RES_ID, container, false);
        this.init();
        this.actionView();
        return this.view;
    }

    public void init(){
        this.etUsername = this.view.findViewById(R.id.et_username);
        this.etPassword = this.view.findViewById(R.id.et_password);
        this.btnLogin = this.view.findViewById(R.id.btn_login);
        this.btnRegister = this.view.findViewById(R.id.btn_register);

    }

    public void actionView(){
        this.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.showProgress("Request", "Wait!!!");
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
//                String username = "tuyen.cntt.k13a@gmail.com";
//                String password = "Tuyen321!!";
                if(username.length() == 0 || password.length() == 0){
                    Toast.makeText(context, "Username and password not empty", Toast.LENGTH_SHORT).show();
                }else{
                    //TODO: Request login auth
                    if(!StringUtils.validatorEmail(username)){
                        Toast.makeText(context, "Email format error", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(!StringUtils.validatorPassword(password)){
                        Toast.makeText(context, "Password format error", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    context.getAuthViewModel().login(new User(username, password)).observe(context, res -> {
                        if(RestUtils.isSuccess(res)) {
                            DataStatic.AUTHOR.ACCESS_TOKEN = res.getData().getAccessToken();
                            DataStatic.AUTHOR.REFRESH_TOKEN = res.getData().getRefreshToken();
                            DataStatic.AUTHOR.DEVICE_ID = res.getData().getDeviceId();
                            context.getAuthViewModel().refresh();
                            LiveData<ApiResponse<Map<String, Object>>> lvData2 = context.getAuthViewModel().getDevices("false");
                            lvData2.observe(context, data2 -> {
                                if (data2.getCode() == -10305) {
                                    String deviceId = (String) data2.getData().get("deviceId");
                                    context.setFragmentDeviceConfirm(deviceId);
                                } else {
                                    Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show();
                                    context.finish();
                                    context.startActivity(new Intent(context, HomeActivity.class));
                                }
                            });
                        }else{
                            switch (res.getKey()){
                                case "USER_PASSWORD_WRONG":
                                    Toast.makeText(context, "Password wrong", Toast.LENGTH_SHORT).show();
                                    break;
                                case "USER_NOT_FOUND":
                                    Toast.makeText(context, "User not found", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(context, "User/Password INVALID", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                        context.hideProgress();
                    });

                }
            }
        });

        this.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.setFragmentSignUp();
            }
        });
    }
}
