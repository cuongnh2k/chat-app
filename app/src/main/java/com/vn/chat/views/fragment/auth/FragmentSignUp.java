package com.vn.chat.views.fragment.auth;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.vn.chat.R;
import com.vn.chat.common.utils.RestUtils;
import com.vn.chat.common.utils.StringUtils;
import com.vn.chat.common.utils.ViewUtils;
import com.vn.chat.data.User;
import com.vn.chat.views.activity.AuthActivity;

@SuppressLint("ValidFragment")
public class FragmentSignUp extends Fragment {

    private static final Integer RES_ID = R.layout.fragment_sign_up;
    private AuthActivity context;
    private View view;

    private EditText etPassword, etRePassword, etFullName, etPhone, etMail;
    private Button btnSignIn, btnSignUp;

    @SuppressLint("ValidFragment")
    public FragmentSignUp(AuthActivity mContext){
        this.context = mContext;
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
        this.etPassword = this.view.findViewById(R.id.et_password);
        this.etRePassword = this.view.findViewById(R.id.et_re_password);
        this.etFullName = this.view.findViewById(R.id.et_full_name);
        this.etMail = this.view.findViewById(R.id.et_mail);
        this.btnSignIn = this.view.findViewById(R.id.btn_login);
        this.btnSignUp = this.view.findViewById(R.id.btn_register);
    }

    public void actionView(){
        this.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.setFragmentSignIn();
            }
        });

        this.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = etPassword.getText().toString().trim();
                String rePassword = etRePassword.getText().toString().trim();
                String fullName = etFullName.getText().toString().trim();
                String mail = etMail.getText().toString().trim();
//                String password = "Tuyen321!!";
//                String rePassword = "Tuyen321!!";
//                String fullName = "Tuyen";
//                String mail = "test0@gmail.com";
                ViewUtils.hideKeyboard(context);
                if(password.length() == 0 || fullName.length() == 0 || mail.length() == 0){
                    Toast.makeText(context, "Please update information to input", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!StringUtils.validatorEmail(mail)){
                    Toast.makeText(context, "Email format error", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!StringUtils.validatorPassword(password)){
                    Toast.makeText(context, "Password need a minimum of 1 lower case letter [a-z] and\n" +
                            "a minimum of 1 upper case letter [A-Z] and\n" +
                            "a minimum of 1 numeric character [0-9] and\n" +
                            "a minimum of 1 special character: ~`!@#$%^&*()-_+={}[]|\\;:\"<>,./?", Toast.LENGTH_SHORT).show();
                    return;
                }
                context.showProgress("Request...", "Wait!!!");
                if(password.equals(rePassword)){
                    User user = new User("0", "", password, fullName, mail);
                    context.getAuthViewModel().register(user).observe(context, res -> {
                        if(RestUtils.isSuccess(res)) {
                            Toast.makeText(context, "Register user successful", Toast.LENGTH_SHORT).show();
                            context.setFragmentUserConfirm(res.getData().getUserId());
                        }
                        context.hideProgress();
                    });
                }else{
                    Toast.makeText(context, "Password different", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
