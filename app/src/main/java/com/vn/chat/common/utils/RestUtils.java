package com.vn.chat.common.utils;

import com.google.gson.Gson;
import com.vn.chat.common.DataStatic;
import com.vn.chat.common.response.ApiResponse;

import java.io.IOException;

import retrofit2.Response;

public class RestUtils<T> {

    public static <T> ApiResponse get(Response<ApiResponse<T>> response){
        if (response.raw().code() != 200 && response.errorBody() != null){
            try {
                return new Gson().fromJson(response.errorBody().string(), ApiResponse.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (response.body() != null && response.body().getCode() == 1){
            return response.body();
        }
        return null;
    }

    public static <T> boolean isSuccess(ApiResponse<T> response){
        switch (response.getCode()){
            case 1:
                return true;
            case 2:
                return false;
        }
        return false;
    }
}
