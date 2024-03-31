package com.vn.chat.repository.common;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.vn.chat.common.DataStatic;
import com.vn.chat.common.response.ApiResponse;
import com.vn.chat.common.utils.RestUtils;
import com.vn.chat.retrofit.common.AuthRequest;
import com.vn.chat.retrofit.RetrofitRequest;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeviceRepository {
    private AuthRequest authRequest;

    public DeviceRepository(Application application){
        this.authRequest = RetrofitRequest.instance(application, DataStatic.COMMON_URL).create(AuthRequest.class);
    }

    public void refresh(Application application){
        this.authRequest = RetrofitRequest.instance(application, DataStatic.COMMON_URL).create(AuthRequest.class);
    }

    public LiveData<ApiResponse<Map<String, Object>>> getDevices(String activated){
        MutableLiveData<ApiResponse<Map<String, Object>>> data = new MutableLiveData<>();
        this.authRequest.getDevice(activated).enqueue(new Callback<ApiResponse<Map<String, Object>>>() {
            @Override
            public void onResponse(Call<ApiResponse<Map<String, Object>>> call, Response<ApiResponse<Map<String, Object>>> response) {
                data.setValue(RestUtils.get(response));
            }

            @Override
            public void onFailure(Call<ApiResponse<Map<String, Object>>> call, Throwable t) {

            }
        });
        return data;
    }
}
