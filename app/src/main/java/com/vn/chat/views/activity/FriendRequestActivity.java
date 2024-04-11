package com.vn.chat.views.activity;

import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.vn.chat.R;
import com.vn.chat.common.view.toolbar.MyToolbar;
import com.vn.chat.data.Channel;
import com.vn.chat.viewmodel.FriendRequestViewModel;
import com.vn.chat.views.adapter.ContactAdapter;
import com.vn.chat.views.adapter.FriendTabAdapter;

import java.util.ArrayList;
import java.util.List;

public class FriendRequestActivity extends CommonActivity {

    private TabLayout tabLayout;
    private FriendTabAdapter friendTabAdapter;
    private ViewPager viewPager;

    private FriendRequestViewModel friendRequestViewModel;
    private MyToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_request);

        init();
        actionView();
    }

    private void init(){
        this.toolbar = new MyToolbar(this);
        this.toolbar.getTwaBtnBack().setVisibility(View.VISIBLE);
        this.toolbar.setNamePage("Friend request");

        this.tabLayout = this.findViewById(R.id.tabLayout);
        this.viewPager = this.findViewById(R.id.viewPager);
        this.tabLayout.addTab(tabLayout.newTab().setText("Request"));
        this.tabLayout.addTab(tabLayout.newTab().setText("Waiting"));
        this.tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        this.friendTabAdapter = new FriendTabAdapter(FriendRequestActivity.this, getSupportFragmentManager(), this.tabLayout.getTabCount());
        this.viewPager.setAdapter(this.friendTabAdapter);

        this.friendRequestViewModel = ViewModelProviders.of(FriendRequestActivity.this).get(FriendRequestViewModel.class);
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

        this.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        this.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

}