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

import com.vn.chat.R;
import com.vn.chat.data.User;
import com.vn.chat.views.activity.AuthActivity;
import com.vn.chat.views.activity.HomeActivity;

@SuppressLint("ValidFragment")
public class FragmentUserConfirm extends Fragment {

    private static final Integer RES_ID = R.layout.fragment_user_confirm;
    private AuthActivity context;
    private View view;
    private String userId;

    private EditText etCode;
    private Button btnSubmit;

    @SuppressLint("ValidFragment")
    public FragmentUserConfirm(AuthActivity mContext, String userId){
        this.context = mContext;
        this.userId = userId;
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
                User user = new User();
                user.setCode(code);
                user.setUserId(userId);
                context.getAuthViewModel().activeUser(user).observe(context, res -> {
                    if(res.getCode().equals(1)) {
                        context.setFragmentSignIn();
                    }
//                    Toast.makeText(context, res.getMessage(), Toast.LENGTH_SHORT).show();
                    context.hideProgress();
                });
            }
        });
    }
}
