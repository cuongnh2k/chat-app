package com.vn.chat.repository.chat;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.vn.chat.common.DataStatic;
import com.vn.chat.common.response.ApiResponse;
import com.vn.chat.common.utils.RestUtils;
import com.vn.chat.data.Channel;
import com.vn.chat.data.CommonDTO;
import com.vn.chat.data.User;
import com.vn.chat.retrofit.RetrofitRequest;
import com.vn.chat.retrofit.chat.ChannelRequest;
import com.vn.chat.retrofit.chat.UserRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private UserRequest userRequest;

    public UserRepository(Application application){
        this.userRequest = RetrofitRequest.instance(application, DataStatic.CHAT_URL).create(UserRequest.class);
    }

    public LiveData<ApiResponse<User>> getUser(CommonDTO commonDTO){
        MutableLiveData<ApiResponse<User>> data = new MutableLiveData<>();
        this.userRequest.getUser(commonDTO.getSearch()).enqueue(new Callback<ApiResponse<User>>() {
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

    public LiveData<ApiResponse<User>> makeFriend(User user){
        MutableLiveData<ApiResponse<User>> data = new MutableLiveData<>();
        this.userRequest.makeFriend(user).enqueue(new Callback<ApiResponse<User>>() {
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
