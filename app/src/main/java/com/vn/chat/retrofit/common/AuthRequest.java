package com.vn.chat.retrofit.common;


import com.vn.chat.common.response.ApiResponse;
import com.vn.chat.data.Device;
import com.vn.chat.data.User;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AuthRequest {

    @POST("common/device/sign-in")
    Call<ApiResponse<Device>> login(@Body User user);

    @POST("common/device/sign-out")
    Call<ApiResponse<Device>> logout(@Body Device device);

    @POST("common/user/sign-up")
    Call<ApiResponse<User>> register(@Body User user);

    @PUT("common/user")
    Call<ApiResponse<User>> update(@Body User user);

    @POST("common/device/{deviceId}/active")
    Call<ApiResponse<Device>> activeDevice(@Path("deviceId") String deviceId, @Body Device device);

    @GET("common/user/detail")
    Call<ApiResponse<User>> userDetail();

    @POST("common/user/{userId}/active")
    Call<ApiResponse<User>> activeUser(@Path("userId") String userId, @Body User user);

    @GET("common/device")
    Call<ApiResponse<Map<String, Object>>> getDevice(@Header("Authorization") String auth, @Query("activated") String activated);

}
