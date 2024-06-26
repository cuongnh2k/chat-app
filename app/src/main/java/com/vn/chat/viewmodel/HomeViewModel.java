package com.vn.chat.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.vn.chat.common.DataStatic;
import com.vn.chat.common.response.ApiResponse;
import com.vn.chat.data.Channel;
import com.vn.chat.data.SearchDTO;
import com.vn.chat.data.Device;
import com.vn.chat.data.File;
import com.vn.chat.data.Message;
import com.vn.chat.data.User;
import com.vn.chat.repository.chat.ChannelRepository;
import com.vn.chat.repository.chat.UserRepository;
import com.vn.chat.repository.common.AuthRepository;
import com.vn.chat.repository.common.FileRepository;

import java.util.Arrays;

public class HomeViewModel extends AndroidViewModel {

    private ChannelRepository channelRepository;
    private UserRepository userRepository;
    private AuthRepository authRepository;
    private FileRepository fileRepository;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        this.channelRepository = new ChannelRepository(getApplication());
        this.userRepository = new UserRepository(getApplication());
        this.authRepository = new AuthRepository(getApplication());
        this.fileRepository = new FileRepository(getApplication());
    }

    public LiveData<ApiResponse<Channel>> detailChannel(String chanelId){
        return this.channelRepository.detailChannel(chanelId);
    }

    public LiveData<ApiResponse<Channel>> getChannel(SearchDTO search){
        return this.channelRepository.getChannel(search);
    }

    public LiveData<ApiResponse<Channel>> postChannel(Channel channel){
        return this.channelRepository.postChannel(channel);
    }

    public LiveData<ApiResponse<Channel>> putChannel(Channel channel){
        return this.channelRepository.putChannel(channel);
    }

    public LiveData<ApiResponse<Channel>> getLastedChannel(SearchDTO searchDTO){
        return this.channelRepository.getLatestChannel(searchDTO);
    }

    public LiveData<ApiResponse<User>> findFriend(SearchDTO commonDTO){
        return this.userRepository.getUser(commonDTO);
    }

    public LiveData<ApiResponse<User>> makeFriend(User user){
        return this.userRepository.makeFriend(user);
    }

    public LiveData<ApiResponse<User>> userDetail(){
        return this.authRepository.userDetail();
    }
    /**
     * @Message
     * */
    public LiveData<ApiResponse<Message>> getMessage(Message message, SearchDTO searchDTO){
        return this.channelRepository.getMessage(message, searchDTO);
    }

    public LiveData<ApiResponse<Message>> postMessage(Message message){
        return this.channelRepository.postMessage(message);
    }
    /**
     * @File
     * */
    public LiveData<ApiResponse<File>> postFile(File file){
        return this.fileRepository.upload(file);
    }
    public LiveData<ApiResponse<File>> getFiles(Channel channel, SearchDTO search){
        return this.channelRepository.getFiles(channel, search);
    }

    /**
     * Member
     * */
    public LiveData<ApiResponse<User>> getMembers(Channel channel){
        return this.channelRepository.getMembers(channel);
    }
    public LiveData<ApiResponse<User>> postMember(Channel channel){
        return this.channelRepository.postMember(channel);
    }
    public LiveData<ApiResponse<User>> getUserAdd(Channel channel){
        return this.channelRepository.getUserAdd(channel);
    }

    /**
     * React
     * */
    public LiveData<ApiResponse<Channel>> postReact(Channel channel){
        return this.channelRepository.postReact(channel);
    }

    public LiveData<ApiResponse<User>> postReactOwner(Channel channel){
        return this.channelRepository.postReactOwner(channel);
    }

    public LiveData<ApiResponse<Channel>> updateOwner(Channel channel){
        return this.channelRepository.updateOwner(channel);
    }

    public LiveData<ApiResponse<Device>> logout(){
        Device d = new Device();
//        d.setDeviceIds(Arrays.asList(DataStatic.AUTHOR.DEVICE_ID));
        return this.authRepository.logout(d);
    }


    public LiveData<ApiResponse<User>> updateUser(User user){
        return this.authRepository.updateUser(user);
    }
}