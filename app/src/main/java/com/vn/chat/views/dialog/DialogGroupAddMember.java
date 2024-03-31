package com.vn.chat.views.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.vn.chat.R;
import com.vn.chat.common.view.icon.TextViewAwsSo;
import com.vn.chat.data.Channel;
import com.vn.chat.data.CommonDTO;
import com.vn.chat.data.User;
import com.vn.chat.views.activity.HomeActivity;
import com.vn.chat.views.adapter.ContactAdapter;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("NewApi")
public class DialogGroupAddMember {

    private HomeActivity activity;
    private Dialog dialog;
    private EditText etSearch;
    private TextViewAwsSo btnSearch;
    private List<Channel> contacts;
    private ContactAdapter contactAdapter;
    private ListView lvContact;
    private TextView tvNoData;
    private Channel channel;

    public DialogGroupAddMember(HomeActivity context, Channel channel){
        this.activity = context;
        this.dialog = new Dialog(context);
        this.dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.dialog.setContentView(R.layout.dialog_group_add_member);
        this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.getWindow().setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

        Window window = this.dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        window.setAttributes(wlp);
        this.channel = channel;

        this.init();
        this.actionView();
        this.searchUser();
    }

    private void init(){
        this.contacts = new ArrayList<>();
        this.etSearch = dialog.findViewById(R.id.et_search);
        this.lvContact = dialog.findViewById(R.id.lv_data);
        this.btnSearch = dialog.findViewById(R.id.btn_search);
        this.tvNoData = dialog.findViewById(R.id.tv_no_data);
        this.contactAdapter = new ContactAdapter(activity, this.contacts);
        this.contactAdapter.setTmpChannelId(channel.getId());
        this.lvContact.setAdapter(this.contactAdapter);
    }

    private void actionView(){
        this.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchUser();

            }
        });
    }

    private void searchUser(){
        channel.setSearch(etSearch.getText().toString());
        contacts.clear();
        lvContact.setVisibility(View.GONE);
        tvNoData.setVisibility(View.VISIBLE);
        activity.getHomeViewModel().getUserAdd(channel).observe(activity, res -> {
            if(res.getCode().equals(1)) {
                if(res.getItems().size() > 0){
                    for(User user : res.getItems()){
                        contacts.add(new Channel(user.getUserId(), user.getName(), user.getEmail(), true));
                    }
                    contactAdapter.notifyDataSetChanged();
                    lvContact.setVisibility(View.VISIBLE);
                    tvNoData.setVisibility(View.GONE);
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

}
