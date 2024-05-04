package com.vn.chat.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.vn.chat.common.response.ApiResponse;
import com.vn.chat.data.Channel;
import com.vn.chat.data.SearchDTO;
import com.vn.chat.repository.chat.ChannelRepository;

public class FriendRequestViewModel extends AndroidViewModel {

    private ChannelRepository channelRepository;

    public FriendRequestViewModel(@NonNull Application application) {
        super(application);
        this.channelRepository = new ChannelRepository(getApplication());
    }

    public LiveData<ApiResponse<Channel>> getFriendRequest(SearchDTO search){
        return this.channelRepository.getFriendRequest(search);
    }

    public LiveData<ApiResponse<Channel>> postReact(Channel channel){
        return this.channelRepository.postReact(channel);
    }

}