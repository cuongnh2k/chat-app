package com.vn.chat.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.vn.chat.common.DataStatic;
import com.vn.chat.common.response.ApiResponse;
import com.vn.chat.data.Channel;
import com.vn.chat.data.CommonDTO;
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

    private ChannelRepository chanelRepository;
    private UserRepository userRepository;
    private AuthRepository authRepository;
    private FileRepository fileRepository;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        this.chanelRepository = new ChannelRepository(getApplication());
        this.userRepository = new UserRepository(getApplication());
        this.authRepository = new AuthRepository(getApplication());
        this.fileRepository = new FileRepository(getApplication());
    }

    public LiveData<ApiResponse<Channel>> getChannel(String type){
        return this.chanelRepository.getChanel(type);
    }

    public LiveData<ApiResponse<Channel>> postChannel(Channel channel){
        return this.chanelRepository.postChanel(channel);
    }

    public LiveData<ApiResponse<Channel>> getLastedChannel(){
        return this.chanelRepository.getLatestChannel();
    }

    public LiveData<ApiResponse<Channel>> getFriendRequest(){
        return this.chanelRepository.getFriendRequest();
    }
    public LiveData<ApiResponse<User>> findFriend(CommonDTO commonDTO){
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
    public LiveData<ApiResponse<Message>> getMessage(Message message){
        return this.chanelRepository.getMessage(message);
    }

    public LiveData<ApiResponse<Message>> postMessage(Message message){
        return this.chanelRepository.postMessage(message);
    }
    /**
     * @File
     * */
    public LiveData<ApiResponse<File>> postFile(File file){
        return this.fileRepository.upload(file);
    }
    public LiveData<ApiResponse<File>> getFiles(Channel channel){
        return this.chanelRepository.getFiles(channel);
    }

    /**
     * Member
     * */
    public LiveData<ApiResponse<User>> getMembers(Channel channel){
        return this.chanelRepository.getMembers(channel);
    }
    public LiveData<ApiResponse<User>> postMember(Channel channel){
        return this.chanelRepository.postMember(channel);
    }
    public LiveData<ApiResponse<User>> getUserAdd(Channel channel){
        return this.chanelRepository.getUserAdd(channel);
    }

    /**
     * React
     * */
    public LiveData<ApiResponse<User>> postReactOwner(Channel channel){
        return this.chanelRepository.postReactOwner(channel);
    }

    public LiveData<ApiResponse<Device>> logout(){
        Device d = new Device();
        d.setDeviceIds(Arrays.asList(DataStatic.AUTHOR.DEVICE_ID));
        return this.authRepository.logout(d);
    }


    public LiveData<ApiResponse<User>> updateUser(User user){
        return this.authRepository.updateUser(user);
    }
}