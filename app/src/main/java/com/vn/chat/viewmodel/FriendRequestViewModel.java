package com.vn.chat.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.vn.chat.common.response.ApiResponse;
import com.vn.chat.data.Channel;
import com.vn.chat.data.CommonDTO;
import com.vn.chat.data.User;
import com.vn.chat.repository.chat.ChannelRepository;
import com.vn.chat.repository.chat.UserRepository;

public class FriendRequestViewModel extends AndroidViewModel {

    private ChannelRepository chanelRepository;

    public FriendRequestViewModel(@NonNull Application application) {
        super(application);
        this.chanelRepository = new ChannelRepository(getApplication());
    }

    public LiveData<ApiResponse<Channel>> getFriendRequest(){
        return this.chanelRepository.getFriendRequest();
    }

    public LiveData<ApiResponse<Channel>> postReact(Channel channel){
        return this.chanelRepository.postReact(channel);
    }

}