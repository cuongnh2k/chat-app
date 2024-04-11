package com.vn.chat.views.adapter;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.vn.chat.views.activity.FriendRequestActivity;
import com.vn.chat.views.fragment.friendRequest.FriendRequestFragment;
import com.vn.chat.views.fragment.friendRequest.FriendWaitingFragment;

public class FriendTabAdapter extends FragmentPagerAdapter {

    private FriendRequestActivity activity;
    private int total;

    public FriendTabAdapter(FriendRequestActivity activity, FragmentManager fm, int total){
        super(fm);
        this.activity = activity;
        this.total = total;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new FriendRequestFragment(activity);
            case 1:
                return new FriendWaitingFragment(activity);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return total;
    }
}