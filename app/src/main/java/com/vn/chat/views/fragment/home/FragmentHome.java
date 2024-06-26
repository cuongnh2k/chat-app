package com.vn.chat.views.fragment.home;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.vn.chat.R;
import com.vn.chat.common.utils.RestUtils;
import com.vn.chat.data.Channel;
import com.vn.chat.data.SearchDTO;
import com.vn.chat.views.activity.HomeActivity;
import com.vn.chat.views.adapter.ChannelAdapter;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class FragmentHome extends Fragment {

    private static final Integer RES_ID = R.layout.fragment_home;
    private HomeActivity context;
    private ListView lvData;
    private ChannelAdapter channelAdapter;
    private EditText etSearch;
    private TextView tvNoData;
    private View view;
    private static List<Channel> channels = new ArrayList<>();

    private SearchDTO search = new SearchDTO();
    private boolean isOver = false, isLoad = false;

    @SuppressLint("ValidFragment")
    public FragmentHome(HomeActivity mContext){
        this.context = mContext;
        this.channels = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(RES_ID, container, false);
        this.init();
        this.actionView();
        return this.view;
    }

    private void init(){
        this.etSearch = view.findViewById(R.id.et_search);
        this.tvNoData = view.findViewById(R.id.tv_no_data);
        this.lvData = view.findViewById(R.id.lv_data);
        this.channelAdapter = new ChannelAdapter(context, this.channels);
        this.lvData.setAdapter(this.channelAdapter);
    }

    private void addMoreData(){
        Log.d("FragmentHome", "addMoreData: "+search.getPageNumber());
        isLoad = true;
        if(search.getPageNumber().equals(0)){
            lvData.setVisibility(View.GONE);
            tvNoData.setVisibility(View.VISIBLE);
        }
        context.getHomeViewModel().getLastedChannel(search).observe(context, res -> {
            if(RestUtils.isSuccess(res)) {
                if(res.getItems().size() > 0){
                    lvData.setVisibility(View.VISIBLE);
                    tvNoData.setVisibility(View.GONE);
                    channels.addAll(res.getItems());
                    channelAdapter.notifyDataSetChanged();
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

    public void updateMessage(Channel channel){
        for(int i = 0; i < channels.size(); i++){
            Channel c = channels.get(i);
            if(c.getId().equalsIgnoreCase(channel.getId())){
                channels.remove(i);
                break;
            }
        }
        channels.add(0, channel);
        channelAdapter.notifyDataSetChanged();
    }

    private void actionView(){
        this.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("FragmentHome", "beforeTextChanged: "+charSequence.toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("FragmentHome", "onTextChanged: "+charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("FragmentHome", "afterTextChanged: "+editable.toString());
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

    @Override
    public void onResume() {
        super.onResume();
        context.setFragmentTarget(context.getFragmentHome());
    }
}
