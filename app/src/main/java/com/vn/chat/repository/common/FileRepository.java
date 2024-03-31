package com.vn.chat.repository.common;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.vn.chat.common.DataStatic;
import com.vn.chat.common.response.ApiResponse;
import com.vn.chat.common.utils.RestUtils;
import com.vn.chat.data.File;
import com.vn.chat.retrofit.RetrofitRequest;
import com.vn.chat.retrofit.common.FileRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FileRepository {
    private FileRequest fileRequest;

    public FileRepository(Application application){
        this.fileRequest = RetrofitRequest.instance(application, DataStatic.COMMON_URL).create(FileRequest.class);
    }

    public LiveData<ApiResponse<File>> upload(File file){
        MutableLiveData<ApiResponse<File>> data = new MutableLiveData<>();
        this.fileRequest.upload(file.getBodyAccessModifier(), file.getBodyFile()).enqueue(new Callback<ApiResponse<File>>() {
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
}
