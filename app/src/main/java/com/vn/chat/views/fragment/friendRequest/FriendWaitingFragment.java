package com.vn.chat.views.fragment.friendRequest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.vn.chat.R;
import com.vn.chat.common.DataStatic;
import com.vn.chat.common.utils.RestUtils;
import com.vn.chat.common.view.icon.TextViewAwsSo;
import com.vn.chat.data.Channel;
import com.vn.chat.data.SearchDTO;
import com.vn.chat.views.activity.FriendRequestActivity;
import com.vn.chat.views.adapter.ContactAdapter;

import java.util.ArrayList;
import java.util.List;

public class FriendWaitingFragment extends Fragment {

    private static final Integer RES_ID = R.layout.fragment_friend_waiting;
    private FriendRequestActivity activity;
    private View view;
    private TextView tvNoData;
    private ListView lvData;
    private List<Channel> channels;
    private ContactAdapter contactAdapter;

    private EditText etSearch;
    private TextViewAwsSo btnSearch;

    private SearchDTO search = new SearchDTO();
    private boolean isOver = false, isLoad = false;

    public FriendWaitingFragment(FriendRequestActivity activity) {
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(RES_ID, container, false);
        this.init();
        this.actionView();
        return this.view;
    }

    private void init(){
        this.search.setType(DataStatic.TYPE.SENT);

        this.etSearch = view.findViewById(R.id.et_search);
        this.btnSearch = view.findViewById(R.id.btn_search);
        this.tvNoData = this.view.findViewById(R.id.tv_no_data);
        this.lvData = this.view.findViewById(R.id.lv_data);
        this.channels = new ArrayList<>();
        this.contactAdapter = new ContactAdapter(activity, this.channels);
        this.lvData.setAdapter(this.contactAdapter);
    }

    private void actionView(){
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

        this.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isLoad) {
                    search.setSearch(etSearch.getText().toString());
                    search.setPageNumber(0);
                    channels.clear();
                    contactAdapter.notifyDataSetChanged();
                    addMoreData();
                }
            }
        });
    }

    private void addMoreData(){
        isLoad = true;
        if(search.getPageNumber().equals(0)){
            lvData.setVisibility(View.GONE);
            tvNoData.setVisibility(View.VISIBLE);
        }
        activity.getFriendRequestViewModel().getFriendRequest(this.search).observe(activity, res -> {
            if(RestUtils.isSuccess(res)){
                if(res.getItems().size() > 0){
                    lvData.setVisibility(View.VISIBLE);
                    tvNoData.setVisibility(View.GONE);
                    for(Channel channel : res.getItems()){
                        channel.setAccept(false);
                        channel.setCancel(true);
                        channels.add(channel);
                    }
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
}
