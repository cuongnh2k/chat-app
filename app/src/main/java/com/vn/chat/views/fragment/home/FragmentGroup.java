package com.vn.chat.views.fragment.home;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.vn.chat.R;
import com.vn.chat.common.utils.RestUtils;
import com.vn.chat.common.view.icon.TextViewAwsSo;
import com.vn.chat.data.Channel;
import com.vn.chat.data.SearchDTO;
import com.vn.chat.views.activity.HomeActivity;
import com.vn.chat.views.adapter.ContactAdapter;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class FragmentGroup extends Fragment {

    private static final Integer RES_ID = R.layout.fragment_group;
    private HomeActivity context;
    private View view;
    private ContactAdapter contactAdapter;
    private ListView lvData;
    private TextView tvNoData;
    private static List<Channel> channels;
    private SwipeRefreshLayout srl;

    private EditText etSearch;
    private TextViewAwsSo btnSearch;

    private SearchDTO search = new SearchDTO();
    private boolean isOver = false, isLoad = false;

    @SuppressLint("ValidFragment")
    public FragmentGroup(HomeActivity mContext){
        this.context = mContext;
        channels = new ArrayList<>();
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
        this.search.setType("GROUP");
        this.srl = view.findViewById(R.id.srl_layout);
        this.etSearch = view.findViewById(R.id.et_search);
        this.btnSearch = view.findViewById(R.id.btn_search);
        this.lvData = view.findViewById(R.id.lv_data);
        this.tvNoData = view.findViewById(R.id.tv_no_data);
        this.contactAdapter = new ContactAdapter(context, channels);
        this.lvData.setAdapter(this.contactAdapter);
    }

    private void addMoreData(){
        isLoad = true;
        context.getHomeViewModel().getChannel(this.search).observe(context, res -> {
            if(RestUtils.isSuccess(res)) {
                if(res.getItems().size() > 0){
                    channels.addAll(res.getItems());
                    lvData.setVisibility(View.VISIBLE);
                    tvNoData.setVisibility(View.GONE);
                    contactAdapter.notifyDataSetChanged();
                }else{
                    isOver = true;
                    if(search.getPageNumber().equals(0)){
                        lvData.setVisibility(View.GONE);
                        tvNoData.setVisibility(View.VISIBLE);
                    }
                }
            }else{
                isOver = true;
            }
            search.addPage();
            srl.setRefreshing(false);
            isLoad = false;
        });
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
                        if (!isLoad && !srl.isRefreshing()) {
                            System.out.println("ADD MORE");
                            addMoreData();
                        }
                    }
                }
            }
        });

        this.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isLoad){
                    search.setSearch(etSearch.getText().toString());
                    search.setPageNumber(0);
                    channels.clear();
                    contactAdapter.notifyDataSetChanged();
                    addMoreData();
                }
            }
        });

        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reloadData();
            }
        });
    }

    public void reloadData(){
        search.setPageNumber(0);
        channels.clear();
        contactAdapter.notifyDataSetChanged();
        addMoreData();
    }

    @Override
    public void onResume() {
        super.onResume();
        context.setFragmentTarget(context.getFragmentGroup());
    }
}
