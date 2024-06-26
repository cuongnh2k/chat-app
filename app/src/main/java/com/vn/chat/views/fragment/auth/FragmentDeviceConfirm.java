package com.vn.chat.views.fragment.auth;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ProgressDialog;
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
import com.vn.chat.common.utils.SessionUtils;
import com.vn.chat.data.Device;
import com.vn.chat.data.User;
import com.vn.chat.views.activity.AuthActivity;
import com.vn.chat.views.activity.HomeActivity;

@SuppressLint("ValidFragment")
public class FragmentDeviceConfirm extends Fragment {

    private static final Integer RES_ID = R.layout.fragment_device_confirm;
    private AuthActivity context;
    private View view;
    private EditText etCode;
    private Button btnSubmit;
    private String deviceId;
    private Device device;

    @SuppressLint("ValidFragment")
    public FragmentDeviceConfirm(AuthActivity mContext, Device d, String deviceId){
        this.context = mContext;
        this.deviceId = deviceId;
        this.device = d;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(RES_ID, container, false);
        //init view
        this.init();
        this.actionView();
        return this.view;
    }

    public void init(){
        this.etCode = this.view.findViewById(R.id.et_code);
        this.btnSubmit = this.view.findViewById(R.id.btn_submit);
    }

    public void actionView(){
        this.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.showProgress("Request", "Wait");
                String code = etCode.getText().toString().trim();
                device.setCode(code);
                device.setId(deviceId);
                context.getAuthViewModel().activeDevice(device).observe(context, res -> {
                    if(RestUtils.isSuccess(res)) {
                        DataStatic.AUTHOR.ACCESS_TOKEN = device.getAccessToken();
                        DataStatic.AUTHOR.REFRESH_TOKEN = device.getRefreshToken();
                        DataStatic.AUTHOR.DEVICE_ID = device.getDeviceId();
                        SessionUtils.set(context, DataStatic.SESSION.KEY.AUTH, device);
                        context.getAuthViewModel().refresh();
                        context.startActivity(new Intent(context, HomeActivity.class));
                    }else {
                        Toast.makeText(context, "Code verify wrong", Toast.LENGTH_SHORT).show();
                    }
                    context.hideProgress();
                });
            }
        });
    }
}
