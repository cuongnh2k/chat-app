package com.vn.chat.repository.common;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.vn.chat.common.DataStatic;
import com.vn.chat.common.response.ApiResponse;
import com.vn.chat.common.utils.RestUtils;
import com.vn.chat.data.Device;
import com.vn.chat.data.User;
import com.vn.chat.retrofit.common.AuthRequest;
import com.vn.chat.retrofit.RetrofitRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository {
    private AuthRequest authRequest;

    public AuthRepository(Application application){
        this.authRequest = RetrofitRequest.instance(application, DataStatic.COMMON_URL).create(AuthRequest.class);
    }

    public void refresh(Application application){
        this.authRequest = RetrofitRequest.instance(application, DataStatic.COMMON_URL).create(AuthRequest.class);
    }
    
    public LiveData<ApiResponse<Device>> login(User user){
        MutableLiveData<ApiResponse<Device>> data = new MutableLiveData<>();
        try{
            this.authRequest.login(user).enqueue(new Callback<ApiResponse<Device>>() {
                @Override
                public void onResponse(Call<ApiResponse<Device>> call, Response<ApiResponse<Device>> response) {
                    data.setValue(RestUtils.get(response));
                }

                @Override
                public void onFailure(Call<ApiResponse<Device>> call, Throwable t) {
                    Log.e("UserRepository", "onFailure: ", t);
                }

            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }

    public LiveData<ApiResponse<User>> register(User user){
        MutableLiveData<ApiResponse<User>> data = new MutableLiveData<>();
        this.authRequest.register(user).enqueue(new Callback<ApiResponse<User>>() {
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

    public LiveData<ApiResponse<User>> activeUser(User user){
        MutableLiveData<ApiResponse<User>> data = new MutableLiveData<>();
        this.authRequest.activeUser(user.getUserId(), user).enqueue(new Callback<ApiResponse<User>>() {
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

    public LiveData<ApiResponse<User>> userDetail(){
        MutableLiveData<ApiResponse<User>> data = new MutableLiveData<>();
        this.authRequest.userDetail().enqueue(new Callback<ApiResponse<User>>() {
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

    public LiveData<ApiResponse<Device>> activeDevice(Device device){
        MutableLiveData<ApiResponse<Device>> data = new MutableLiveData<>();
        this.authRequest.activeDevice(device.getId(), device).enqueue(new Callback<ApiResponse<Device>>() {
            @Override
            public void onResponse(Call<ApiResponse<Device>> call, Response<ApiResponse<Device>> response) {
                data.setValue(RestUtils.get(response));
            }

            @Override
            public void onFailure(Call<ApiResponse<Device>> call, Throwable t) {
                System.out.println("ERROR: "+new Gson().toJson(call));
            }
        });
        return data;
    }

    public LiveData<ApiResponse<Device>> logout(Device device){
        MutableLiveData<ApiResponse<Device>> data = new MutableLiveData<>();
        this.authRequest.logout(device).enqueue(new Callback<ApiResponse<Device>>() {
            @Override
            public void onResponse(Call<ApiResponse<Device>> call, Response<ApiResponse<Device>> response) {
                data.setValue(RestUtils.get(response));
            }

            @Override
            public void onFailure(Call<ApiResponse<Device>> call, Throwable t) {
                System.out.println("ERROR: "+new Gson().toJson(call));
            }
        });
        return data;
    }

    public LiveData<ApiResponse<User>> updateUser(User user){
        MutableLiveData<ApiResponse<User>> data = new MutableLiveData<>();
        this.authRequest.update(user).enqueue(new Callback<ApiResponse<User>>() {
            @Override
            public void onResponse(Call<ApiResponse<User>> call, Response<ApiResponse<User>> response) {
                data.setValue(RestUtils.get(response));
            }

            @Override
            public void onFailure(Call<ApiResponse<User>> call, Throwable t) {
                System.out.println("ERROR: "+new Gson().toJson(call));
            }
        });
        return data;
    }
}
