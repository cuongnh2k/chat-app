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
import com.vn.chat.data.SearchDTO;
import com.vn.chat.data.User;
import com.vn.chat.views.activity.HomeActivity;
import com.vn.chat.views.adapter.ContactAdapter;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("NewApi")
public class DialogAddUser {

    private HomeActivity activity;
    private Dialog dialog;
    private EditText etSearch;
    private TextViewAwsSo btnSearch;
    private List<Channel> contacts;
    private ContactAdapter contactAdapter;
    private ListView lvContact;
    private TextView tvNoData;

    public DialogAddUser(HomeActivity context){
        this.activity = context;
        this.dialog = new Dialog(context);
        this.dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.dialog.setContentView(R.layout.dialog_add_user);
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
        this.contacts = new ArrayList<>();
        this.etSearch = dialog.findViewById(R.id.et_search);
        this.lvContact = dialog.findViewById(R.id.lv_data);
        this.btnSearch = dialog.findViewById(R.id.btn_search);
        this.tvNoData = dialog.findViewById(R.id.tv_no_data);
        this.contactAdapter = new ContactAdapter(activity, this.contacts);
        this.lvContact.setAdapter(this.contactAdapter);
    }

    private void actionView(){
        this.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contacts.clear();
                activity.getHomeViewModel().findFriend(new SearchDTO(etSearch.getText().toString())).observe(activity, res -> {
                    lvContact.setVisibility(View.GONE);
                    tvNoData.setVisibility(View.VISIBLE);
                    if(res.getCode().equals(1)) {
                        if(res.getItems().size() > 0){
                            for(User user : res.getItems()){
                                contacts.add(new Channel(user.getUserId(), user.getName(), user.getEmail(), !("ACCEPT".equals(user.getStatus())), user.getAvatarUrl()));
                            }
                            contactAdapter.notifyDataSetChanged();
                            lvContact.setVisibility(View.VISIBLE);
                            tvNoData.setVisibility(View.GONE);
                        }
                    }
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
