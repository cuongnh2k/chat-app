package com.vn.chat.repository.chat;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.vn.chat.common.DataStatic;
import com.vn.chat.common.response.ApiResponse;
import com.vn.chat.common.utils.RestUtils;
import com.vn.chat.data.Channel;
import com.vn.chat.data.CommonDTO;
import com.vn.chat.data.File;
import com.vn.chat.data.Message;
import com.vn.chat.data.User;
import com.vn.chat.retrofit.chat.ChannelRequest;
import com.vn.chat.retrofit.RetrofitRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChannelRepository {
    private ChannelRequest channelRequest;

    public ChannelRepository(Application application){
        this.channelRequest = RetrofitRequest.instance(application, DataStatic.CHAT_URL).create(ChannelRequest.class);
    }

    public LiveData<ApiResponse<Channel>> detailChannel(String chanelId){
        MutableLiveData<ApiResponse<Channel>> data = new MutableLiveData<>();
        this.channelRequest.detailChannel(chanelId).enqueue(new Callback<ApiResponse<Channel>>() {
            @Override
            public void onResponse(Call<ApiResponse<Channel>> call, Response<ApiResponse<Channel>> response) {
                data.setValue(RestUtils.get(response));
            }

            @Override
            public void onFailure(Call<ApiResponse<Channel>> call, Throwable t) {

            }
        });
        return data;
    }

    public LiveData<ApiResponse<Channel>> getChannel(String type){
        MutableLiveData<ApiResponse<Channel>> data = new MutableLiveData<>();
        this.channelRequest.getChannel(type).enqueue(new Callback<ApiResponse<Channel>>() {
            @Override
            public void onResponse(Call<ApiResponse<Channel>> call, Response<ApiResponse<Channel>> response) {
                data.setValue(RestUtils.get(response));
            }

            @Override
            public void onFailure(Call<ApiResponse<Channel>> call, Throwable t) {

            }
        });
        return data;
    }

    public LiveData<ApiResponse<Channel>> postChannel(Channel channel){
        MutableLiveData<ApiResponse<Channel>> data = new MutableLiveData<>();
        this.channelRequest.postChannel(channel).enqueue(new Callback<ApiResponse<Channel>>() {
            @Override
            public void onResponse(Call<ApiResponse<Channel>> call, Response<ApiResponse<Channel>> response) {
                data.setValue(RestUtils.get(response));
            }

            @Override
            public void onFailure(Call<ApiResponse<Channel>> call, Throwable t) {

            }
        });
        return data;
    }

    public LiveData<ApiResponse<Channel>> putChannel(Channel channel){
        MutableLiveData<ApiResponse<Channel>> data = new MutableLiveData<>();
        this.channelRequest.putChannel(channel.getChannelId(), channel).enqueue(new Callback<ApiResponse<Channel>>() {
            @Override
            public void onResponse(Call<ApiResponse<Channel>> call, Response<ApiResponse<Channel>> response) {
                data.setValue(RestUtils.get(response));
            }

            @Override
            public void onFailure(Call<ApiResponse<Channel>> call, Throwable t) {

            }
        });
        return data;
    }

    public LiveData<ApiResponse<Channel>> getLatestChannel(){
        MutableLiveData<ApiResponse<Channel>> data = new MutableLiveData<>();
        this.channelRequest.getLatestChannel().enqueue(new Callback<ApiResponse<Channel>>() {
            @Override
            public void onResponse(Call<ApiResponse<Channel>> call, Response<ApiResponse<Channel>> response) {
                data.setValue(RestUtils.get(response));
            }

            @Override
            public void onFailure(Call<ApiResponse<Channel>> call, Throwable t) {

            }
        });
        return data;
    }

    public LiveData<ApiResponse<Channel>> getFriendRequest(String type){
        MutableLiveData<ApiResponse<Channel>> data = new MutableLiveData<>();
        this.channelRequest.getFriendRequest(type).enqueue(new Callback<ApiResponse<Channel>>() {
            @Override
            public void onResponse(Call<ApiResponse<Channel>> call, Response<ApiResponse<Channel>> response) {
                data.setValue(RestUtils.get(response));
            }

            @Override
            public void onFailure(Call<ApiResponse<Channel>> call, Throwable t) {

            }
        });
        return data;
    }

    public LiveData<ApiResponse<Channel>> postReact(Channel channel){
        MutableLiveData<ApiResponse<Channel>> data = new MutableLiveData<>();
        this.channelRequest.postReact(channel.getChannelId(), channel).enqueue(new Callback<ApiResponse<Channel>>() {
            @Override
            public void onResponse(Call<ApiResponse<Channel>> call, Response<ApiResponse<Channel>> response) {
                data.setValue(RestUtils.get(response));
            }

            @Override
            public void onFailure(Call<ApiResponse<Channel>> call, Throwable t) {

            }
        });
        return data;
    }

    public LiveData<ApiResponse<Message>> getMessage(Message message){
        MutableLiveData<ApiResponse<Message>> data = new MutableLiveData<>();
        this.channelRequest.getMessage(message.getChannelId()).enqueue(new Callback<ApiResponse<Message>>() {
            @Override
            public void onResponse(Call<ApiResponse<Message>> call, Response<ApiResponse<Message>> response) {
                data.setValue(RestUtils.get(response));
            }

            @Override
            public void onFailure(Call<ApiResponse<Message>> call, Throwable t) {

            }
        });
        return data;
    }

    public LiveData<ApiResponse<Message>> postMessage(Message message){
        MutableLiveData<ApiResponse<Message>> data = new MutableLiveData<>();
        this.channelRequest.postMessage(message.getChannelId(), message).enqueue(new Callback<ApiResponse<Message>>() {
            @Override
            public void onResponse(Call<ApiResponse<Message>> call, Response<ApiResponse<Message>> response) {
                data.setValue(RestUtils.get(response));
            }

            @Override
            public void onFailure(Call<ApiResponse<Message>> call, Throwable t) {

            }
        });
        return data;
    }

    public LiveData<ApiResponse<File>> getFiles(Channel channel){
        MutableLiveData<ApiResponse<File>> data = new MutableLiveData<>();
        this.channelRequest.getFiles(channel.getId()).enqueue(new Callback<ApiResponse<File>>() {
            @Override
            public void onResponse(Call<ApiResponse<File>> call, Response<ApiResponse<File>> response) {
                data.setValue(RestUtils.get(response));
            }

            @Override
            public void onFailure(Call<ApiResponse<File>> call, Throwable t) {

            }
        });
        return data;
    }

    public LiveData<ApiResponse<User>> getUserAdd(Channel channel){
        MutableLiveData<ApiResponse<User>> data = new MutableLiveData<>();
        this.channelRequest.getUserAdd(channel.getId(), channel.getSearch()).enqueue(new Callback<ApiResponse<User>>() {
            @Override
            public void onResponse(Call<ApiResponse<User>> call, Response<ApiResponse<User>> response) {
                data.setValue(RestUtils.get(response));
            }

            @Override
            public void onFailure(Call<ApiResponse<User>> call, Throwable t) {

            }
        });
        return data;
    }

    public LiveData<ApiResponse<User>> getMembers(Channel channel){
        MutableLiveData<ApiResponse<User>> data = new MutableLiveData<>();
        this.channelRequest.getMembers(channel.getId(), channel.getStatus()).enqueue(new Callback<ApiResponse<User>>() {
            @Override
            public void onResponse(Call<ApiResponse<User>> call, Response<ApiResponse<User>> response) {
                data.setValue(RestUtils.get(response));
            }

            @Override
            public void onFailure(Call<ApiResponse<User>> call, Throwable t) {

            }
        });
        return data;
    }

    public LiveData<ApiResponse<User>> postMember(Channel channel){
        MutableLiveData<ApiResponse<User>> data = new MutableLiveData<>();
        this.channelRequest.postMember(channel.getId(), channel).enqueue(new Callback<ApiResponse<User>>() {
            @Override
            public void onResponse(Call<ApiResponse<User>> call, Response<ApiResponse<User>> response) {
                data.setValue(RestUtils.get(response));
            }

            @Override
            public void onFailure(Call<ApiResponse<User>> call, Throwable t) {

            }
        });
        return data;
    }

    public LiveData<ApiResponse<User>> postReactOwner(Channel channel){
        MutableLiveData<ApiResponse<User>> data = new MutableLiveData<>();
        this.channelRequest.postReactOwner(channel.getId(), channel).enqueue(new Callback<ApiResponse<User>>() {
            @Override
            public void onResponse(Call<ApiResponse<User>> call, Response<ApiResponse<User>> response) {
                data.setValue(RestUtils.get(response));
            }

            @Override
            public void onFailure(Call<ApiResponse<User>> call, Throwable t) {

            }
        });
        return data;
    }
}
