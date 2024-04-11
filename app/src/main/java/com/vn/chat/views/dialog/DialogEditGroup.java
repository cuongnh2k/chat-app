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
import com.vn.chat.data.Channel;
import com.vn.chat.data.File;
import com.vn.chat.views.activity.HomeActivity;
import com.vn.chat.views.adapter.ContactAdapter;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("NewApi")
public class DialogEditGroup {

    private HomeActivity activity;
    private Dialog dialog;
    private ImageView ivAvatar;
    private EditText etName;
    private Button btnSave;
    private Channel channel;

    public DialogEditGroup(HomeActivity context, Channel channel){
        this.activity = context;
        this.dialog = new Dialog(context);
        this.dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.dialog.setContentView(R.layout.dialog_edit_group);
        this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.getWindow().setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

        this.channel = channel;

        Window window = this.dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        window.setAttributes(wlp);

        this.init();
        this.actionView();
    }

    private void init(){
        this.ivAvatar = dialog.findViewById(R.id.iv_avatar);
        this.etName = dialog.findViewById(R.id.et_name);
        this.btnSave = dialog.findViewById(R.id.btn_save);

        this.etName.setText(channel.getName());
        ImageUtils.loadUrl(activity, this.ivAvatar, channel.getAvatarUrl());
    }

    private void actionView(){
        this.ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentUtils.chooseImage(activity, IntentUtils.FRAGMENT.MESSAGE_CONFIG);
            }
        });

        this.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameGroup = etName.getText().toString().trim();
                if(nameGroup.length() == 0){
                    Toast.makeText(activity, "Please add group name", Toast.LENGTH_SHORT).show();
                    return;
                }
                channel.setName(nameGroup);
                activity.showProgress("Update", "Waiting minutes");
                activity.getHomeViewModel().putChannel(channel).observe(activity, res -> {
                    if(RestUtils.isSuccess(res)){
                        Toast.makeText(activity, "Update group successful", Toast.LENGTH_SHORT).show();
                        hide();
                    }else{
                        Toast.makeText(activity, "Update group fail", Toast.LENGTH_SHORT).show();
                    }
                    activity.hideProgress();
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

    public void setImageAvatar(File file){
        ImageUtils.loadUrl(activity, this.ivAvatar, file.getUrl());
        channel.setAvatarUrl(file.getUrl());
    }
}
