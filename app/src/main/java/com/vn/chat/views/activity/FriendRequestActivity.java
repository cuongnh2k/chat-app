package com.vn.chat.views.activity;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.vn.chat.R;
import com.vn.chat.common.view.toolbar.MyToolbar;
import com.vn.chat.data.Channel;
import com.vn.chat.viewmodel.FriendRequestViewModel;
import com.vn.chat.views.adapter.ContactAdapter;

import java.util.ArrayList;
import java.util.List;

public class FriendRequestActivity extends CommonActivity {

    private FriendRequestViewModel friendRequestViewModel;
    private TextView tvNoData;
    private ListView lvData;
    private List<Channel> channels;
    private ContactAdapter contactAdapter;
    private MyToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_request);

        init();
        actionView();
        loadRequestList();
    }

    private void init(){
        this.toolbar = new MyToolbar(this);
        this.toolbar.getTwaBtnBack().setVisibility(View.VISIBLE);
        this.toolbar.setNamePage("Friend request");
        this.friendRequestViewModel = ViewModelProviders.of(FriendRequestActivity.this).get(FriendRequestViewModel.class);
        this.tvNoData = this.findViewById(R.id.tv_no_data);
        this.lvData = this.findViewById(R.id.lv_data);
        this.channels = new ArrayList<>();
        this.contactAdapter = new ContactAdapter(FriendRequestActivity.this, this.channels);
        this.lvData.setAdapter(this.contactAdapter);
    }

    public FriendRequestViewModel getFriendRequestViewModel() {
        return friendRequestViewModel;
    }

    private void actionView(){
        this.toolbar.getTwaBtnBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void loadRequestList(){
        this.channels.clear();
        this.friendRequestViewModel.getFriendRequest().observe(this, res -> {
            tvNoData.setVisibility(View.VISIBLE);
            lvData.setVisibility(View.GONE);
            if(res.getCode().equals(1)){
                for(Channel channel : res.getItems()){
                    channel.setAccept(true);
                    channels.add(channel);
                }
                contactAdapter.notifyDataSetChanged();
                if(channels.size() > 0){
                   tvNoData.setVisibility(View.GONE);
                   lvData.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}