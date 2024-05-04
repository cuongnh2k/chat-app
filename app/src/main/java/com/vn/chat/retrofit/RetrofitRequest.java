package com.vn.chat.retrofit;

import android.app.Activity;
import android.app.Application;

import com.vn.chat.common.DataStatic;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitRequest {
    private static Retrofit retrofit;

    public static Retrofit instance(Application application, String baseUrl){
//        if (retrofit == null) {
            TokenAuthenticator tokenAuthenticator = new TokenAuthenticator(application);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.authenticator(tokenAuthenticator);
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request().newBuilder()
                            .addHeader("API-Key", DataStatic.API_KEY)
                            .addHeader("Authorization", "Bearer "+DataStatic.AUTHOR.ACCESS_TOKEN)
//                            .addHeader("User-Agent", Objects.requireNonNull(System.getProperty("http.agent")))
                            .build();
                    return chain.proceed(request);
                }
            });

            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
//        }
        return retrofit;
    }
}
