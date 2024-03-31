package com.vn.chat.retrofit.chat;

import com.vn.chat.common.response.ApiResponse;
import com.vn.chat.data.Channel;
import com.vn.chat.data.User;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface UserRequest {

    @GET("chat/user")
    Call<ApiResponse<User>> getUser(@Query("search") String search);

    @POST("chat/user/make-friend")
    Call<ApiResponse<User>> makeFriend(@Body User user);
}
