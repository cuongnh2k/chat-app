package com.vn.chat.retrofit.common;

import com.vn.chat.common.response.ApiResponse;
import com.vn.chat.data.Channel;
import com.vn.chat.data.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface FileRequest {

    @Multipart
    @POST("common/file")
    Call<ApiResponse<File>> upload(@Part("accessModifier") RequestBody accessModifier
    , @Part MultipartBody.Part file);
}
