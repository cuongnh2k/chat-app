package com.vn.chat.views.fragment.friendRequest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.vn.chat.R;
import com.vn.chat.common.DataStatic;
import com.vn.chat.data.Channel;
import com.vn.chat.views.activity.FriendRequestActivity;
import com.vn.chat.views.adapter.ContactAdapter;

import java.util.ArrayList;
import java.util.List;

public class FriendWaitingFragment extends Fragment {

    private static final Integer RES_ID = R.layout.fragment_friend_request;
    private FriendRequestActivity activity;
    private View view;
    private TextView tvNoData;
    private ListView lvData;
    private List<Channel> channels;
    private ContactAdapter contactAdapter;

    public FriendWaitingFragment(FriendRequestActivity activity) {
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(RES_ID, container, false);
        this.init();
        this.loadRequestList();
        return this.view;
    }

    private void init(){
        this.tvNoData = this.view.findViewById(R.id.tv_no_data);
        this.lvData = this.view.findViewById(R.id.lv_data);
        this.channels = new ArrayList<>();
        this.contactAdapter = new ContactAdapter(activity, this.channels);
        this.lvData.setAdapter(this.contactAdapter);
    }

    private void loadRequestList(){
        this.channels.clear();
        activity.getFriendRequestViewModel().getFriendRequest(DataStatic.TYPE.SENT).observe(activity, res -> {
            tvNoData.setVisibility(View.VISIBLE);
            lvData.setVisibility(View.GONE);
            if(res.getCode().equals(1)){
                for(Channel channel : res.getItems()){
                    channel.setAccept(false);
                    channel.setCancel(true);
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
