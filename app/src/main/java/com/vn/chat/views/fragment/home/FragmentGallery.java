package com.vn.chat.views.fragment.home;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.vn.chat.R;
import com.vn.chat.data.Channel;
import com.vn.chat.data.File;
import com.vn.chat.views.activity.HomeActivity;
import com.vn.chat.views.adapter.GalleryAdapter;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class FragmentGallery extends Fragment {

    private static final Integer RES_ID = R.layout.fragment_gallery;
    private HomeActivity activity;
    private View view;
    private List<File> files;
    private GalleryAdapter galleryAdapter;
    private TextView tvNoData;
    private GridView gvData;
    private Channel channel;

    @SuppressLint("ValidFragment")
    public FragmentGallery(HomeActivity mContext, Channel channel){
        this.activity = mContext;
        this.channel = channel;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(RES_ID, container, false);
        this.init();
        this.loadGallery();
        return this.view;
    }

    private void init(){
        this.activity.getToolbar().hideBtnConfig();
        this.activity.getToolbar().setNamePage("Gallery");
        this.files = new ArrayList<>();
        this.gvData = this.view.findViewById(R.id.gv_data);
        this.tvNoData = this.view.findViewById(R.id.tv_no_data);
        this.galleryAdapter = new GalleryAdapter(activity, this.files);
        this.gvData.setAdapter(galleryAdapter);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadGallery(){
        files.clear();
        this.gvData.setVisibility(View.GONE);
        this.tvNoData.setVisibility(View.VISIBLE);
        galleryAdapter.notifyDataSetChanged();
        activity.getHomeViewModel().getFiles(new Channel(this.channel.getId())).observe(activity, res -> {
            if(res.getCode().equals(1)){
                if(res.getItems().size() > 0){
                    this.gvData.setVisibility(View.VISIBLE);
                    this.tvNoData.setVisibility(View.GONE);
                    files.addAll(res.getItems());
                    galleryAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.activity.getToolbar().setNamePage(channel.getName());
        this.activity.getToolbar().showBtnConfig();
    }
}
