package com.vn.chat.views.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.vn.chat.R;
import com.vn.chat.common.utils.RestUtils;
import com.vn.chat.data.Channel;
import com.vn.chat.data.User;
import com.vn.chat.views.activity.HomeActivity;
import com.vn.chat.views.adapter.ContactAdapter;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("NewApi")
public class DialogGroupRequestList {

    private HomeActivity activity;
    private Dialog dialog;
    private List<Channel> requests;
    private ContactAdapter contactAdapter;
    private ListView lvMember;
    private TextView tvNoData;
    private Channel channel;

    public DialogGroupRequestList(HomeActivity context, Channel channel){
        this.channel = channel;
        this.activity = context;
        this.dialog = new Dialog(context);
        this.dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.dialog.setContentView(R.layout.dialog_group_request_list);
        this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.getWindow().setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

        Window window = this.dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        window.setAttributes(wlp);

        this.init();
        this.loadRequestList();
        this.actionView();
    }

    private void init(){
        this.requests = new ArrayList<>();
        this.lvMember = dialog.findViewById(R.id.lv_data);
        this.tvNoData = dialog.findViewById(R.id.tv_no_data);
        this.contactAdapter = new ContactAdapter(activity, this.requests);
        this.contactAdapter.setTmpChannelId(channel.getId());
        this.lvMember.setAdapter(this.contactAdapter);
    }

    private void loadRequestList(){
        this.requests.clear();
        this.tvNoData.setVisibility(View.VISIBLE);
        this.lvMember.setVisibility(View.GONE);
        this.contactAdapter.notifyDataSetChanged();
        channel.setStatus("NEW");
        activity.getHomeViewModel().getMembers(channel).observe(activity, res -> {
            if(RestUtils.isSuccess(res)){
                if(res.getItems().size() > 0){
                    for (User user : res.getItems()){
                        requests.add(new Channel(user.getUserId(), user.getName(), user.getEmail(), false, true, user.getAvatarUrl()));
                    }
                    this.tvNoData.setVisibility(View.GONE);
                    this.lvMember.setVisibility(View.VISIBLE);
                    this.contactAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void actionView(){

    }

    public void show(){
        this.dialog.show();
    }

    public void hide(){
        this.dialog.dismiss();
    }

}
