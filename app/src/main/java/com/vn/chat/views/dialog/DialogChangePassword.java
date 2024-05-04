package com.vn.chat.views.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.vn.chat.R;
import com.vn.chat.common.utils.ImageUtils;
import com.vn.chat.common.utils.IntentUtils;
import com.vn.chat.common.utils.RestUtils;
import com.vn.chat.common.utils.StringUtils;
import com.vn.chat.data.Channel;
import com.vn.chat.data.File;
import com.vn.chat.data.User;
import com.vn.chat.views.activity.HomeActivity;
import com.vn.chat.views.adapter.ContactAdapter;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("NewApi")
public class DialogChangePassword {

    private HomeActivity activity;
    private Dialog dialog;
    private EditText etOldPass, etNewPass, etConfirmPass;
    private Button btnSave;

    public DialogChangePassword(HomeActivity context){
        this.activity = context;
        this.dialog = new Dialog(context);
        this.dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.dialog.setContentView(R.layout.dialog_change_password);
        this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.getWindow().setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

        Window window = this.dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        window.setAttributes(wlp);

        this.init();
        this.actionView();
    }

    private void init(){
        this.etOldPass = dialog.findViewById(R.id.et_old_password);
        this.etNewPass = dialog.findViewById(R.id.et_new_password);
        this.etConfirmPass = dialog.findViewById(R.id.et_confirm_password);
        this.btnSave = dialog.findViewById(R.id.btn_save);
    }

    private void actionView(){

        this.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPass = etOldPass.getText().toString();
                String newPass = etNewPass.getText().toString();
                String confirmPass = etConfirmPass.getText().toString();
                if(!StringUtils.validatorPassword(oldPass)){
                    Toast.makeText(activity, "Old password format error", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!StringUtils.validatorPassword(newPass)){
                    Toast.makeText(activity, "New password format error", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!StringUtils.validatorPassword(confirmPass)){
                    Toast.makeText(activity, "Confirm password format error", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!newPass.equals(confirmPass)){
                    Toast.makeText(activity, "New password and confirm password different", Toast.LENGTH_SHORT).show();
                    return;
                }
                User user = new User();
                user.setPassword(newPass);
                user.setOldPassword(oldPass);
                activity.getHomeViewModel().updateUser(user).observe(activity, res -> {
                    if(RestUtils.isSuccess(res)){
                        Toast.makeText(activity, "Change password successful", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(activity, "Change password fail", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                });
            }
        });
    }

    public void show(){
        this.dialog.show();
    }

    public void hide(){
        this.dialog.dismiss();
    }
}
