package com.vn.chat.views.fragment.home;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.vn.chat.R;
import com.vn.chat.common.utils.RestUtils;
import com.vn.chat.data.Channel;
import com.vn.chat.data.SearchDTO;
import com.vn.chat.views.activity.FriendRequestActivity;
import com.vn.chat.views.activity.HomeActivity;
import com.vn.chat.views.adapter.ContactAdapter;
import com.vn.chat.views.dialog.DialogCreateGroup;
import com.vn.chat.views.dialog.DialogAddUser;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class FragmentContact extends Fragment {

    private static final Integer RES_ID = R.layout.fragment_contact;
    private HomeActivity context;
    private View view;
    private LinearLayout btnCreateGroup, btnRequestList, btnRequest;
    private ContactAdapter contactAdapter;
    private ListView lvData;
    private TextView tvNoData;
    private List<Channel> channels;
    private DialogAddUser dialogSearchUser = null;
    private DialogCreateGroup dialogCreateGroup = null;

    private SearchDTO search = new SearchDTO();
    private boolean isOver = false, isLoad = false;

    @SuppressLint("ValidFragment")
    public FragmentContact(HomeActivity mContext){
        this.context = mContext;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(RES_ID, container, false);
        this.init();
        this.actionView();
        return this.view;
    }

    public void init(){
        this.channels = new ArrayList<>();
        this.search.setType("FRIEND");

        this.btnCreateGroup = view.findViewById(R.id.btn_create_group);
        this.btnRequestList = view.findViewById(R.id.btn_request_list);
        this.btnRequest = view.findViewById(R.id.btn_request);
        this.lvData = view.findViewById(R.id.lv_data);
        this.tvNoData = view.findViewById(R.id.tv_no_data);
        this.contactAdapter = new ContactAdapter(context, channels);
        this.lvData.setAdapter(this.contactAdapter);
    }

    private void addMoreData(){
        isLoad = true;
        if(search.getPageNumber().equals(0)){
            lvData.setVisibility(View.GONE);
            tvNoData.setVisibility(View.VISIBLE);
        }
        context.getHomeViewModel().getChannel(search).observe(context, res -> {
            if(RestUtils.isSuccess(res)) {
                if(res.getItems().size() > 0){
                    channels.addAll(res.getItems());
                    lvData.setVisibility(View.VISIBLE);
                    tvNoData.setVisibility(View.GONE);
                    contactAdapter.notifyDataSetChanged();
                }else{
                    isOver = true;
                }
            }else{
                isOver = true;
            }
            search.addPage();
            isLoad = false;
        });
    }

    public void actionView(){
        this.btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogSearchUser = new DialogAddUser(context);
                dialogSearchUser.show();
            }
        });

        this.btnRequestList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, FriendRequestActivity.class));
            }
        });

        this.btnCreateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogCreateGroup = new DialogCreateGroup(context, channels);
                dialogCreateGroup.show();
            }
        });

        this.lvData.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                final int lastItem = firstVisibleItem + visibleItemCount;
                if(lastItem == totalItemCount){
                    // here you have reached end of list, load more data
                    if (!isOver) {
                        if (!isLoad) {
                            addMoreData();
                        }
                    }
                }
            }
        });
    }

    public DialogCreateGroup getDialogCreateGroup() {
        return dialogCreateGroup;
    }
}
