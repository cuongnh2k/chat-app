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
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.vn.chat.R;
import com.vn.chat.common.utils.ImageUtils;
import com.vn.chat.common.utils.IntentUtils;
import com.vn.chat.common.view.icon.TextViewAwsSo;
import com.vn.chat.data.Channel;
import com.vn.chat.data.CommonDTO;
import com.vn.chat.data.File;
import com.vn.chat.data.User;
import com.vn.chat.views.activity.HomeActivity;
import com.vn.chat.views.adapter.ContactAdapter;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("NewApi")
public class DialogCreateGroup {

    private HomeActivity activity;
    private Dialog dialog;
    private List<Channel> contacts;
    private ContactAdapter contactAdapter;
    private ListView lvContact;
    private ImageView ivAvatar;
    private EditText etName;
    private Button btnSave;
    private String urlDefault = "https://s3.ap-southeast-1.amazonaws.com/space.cuongnh2k.s3.bucket.public/a84b0f5e-40a0-4c73-9caa-985a35d09d43/c23e105e-52a8-4683-adf3-845757c6c2a4.jpg";

    public DialogCreateGroup(HomeActivity context, List<Channel> contacts){
        this.activity = context;
        this.dialog = new Dialog(context);
        this.dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.dialog.setContentView(R.layout.dialog_create_group);
        this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.getWindow().setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

        Window window = this.dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        window.setAttributes(wlp);

        this.contacts = new ArrayList<>();
        this.contacts.addAll(contacts);
        for(Channel contact : this.contacts){
            contact.setCreateGroup(true);
        }

        this.init();
        this.actionView();
    }

    private void init(){
        this.ivAvatar = dialog.findViewById(R.id.iv_avatar);
        this.etName = dialog.findViewById(R.id.et_name);
        this.btnSave = dialog.findViewById(R.id.btn_save);
        this.lvContact = dialog.findViewById(R.id.lv_data);
        this.contactAdapter = new ContactAdapter(activity, this.contacts);
        this.lvContact.setAdapter(this.contactAdapter);
    }

    private void actionView(){
        this.ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentUtils.chooseImage(activity, IntentUtils.FRAGMENT.CONTACT);
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

                List<String> userIds = new ArrayList<>();
                for(Channel contact : contacts){
                    if(contact.isCreateGroup()){
                        userIds.add(contact.getUserId());
                    }
                }
                if(userIds.size() > 0){
                    Channel channel = new Channel();
                    channel.setName(nameGroup);
                    channel.setAvatarUrl(urlDefault);
                    channel.setUserIds(userIds);
                    activity.getHomeViewModel().postChannel(channel).observe(activity, res -> {
                        if(res.getCode().equals(1)){
                            Toast.makeText(activity, "Create group successful", Toast.LENGTH_SHORT).show();
                            hide();
                        }else{
                            Toast.makeText(activity, "Create group fail", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
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
        urlDefault = file.getUrl();
    }
}
