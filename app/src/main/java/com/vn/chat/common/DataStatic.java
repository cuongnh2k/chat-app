package com.vn.chat.common;

import android.os.Build;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.vn.chat.data.User;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class DataStatic {

    public static final String STACK_APP = "CHAT_APP";
    public static final String COMMON_URL = "http://103.173.154.123:61453/api/v1/";
    public static final String CHAT_URL = "http://103.173.154.123:54013/api/v1/";
    public static final String BASE_WS = "ws://103.173.154.123:57065/api/v1/websocket/ws?User-Agent=okhttp/3.14.7&Authorization=Bearer ";
//    public static final String BASE_WS = "ws://192.168.1.7:8081";
    public static final String API_KEY = "a1e3e9ff-f9ef-496c-a210-7c0fc476f08d";

    public static class AUTHOR {
        public static String ACCESS_TOKEN = "";
        public static String REFRESH_TOKEN = "";
        public static String DEVICE_ID = "";
        public static User USER_INFO = null;
    }

    public static class ChannelType{
        public static final String GROUP = "GROUP";
        public static final String USER = "USER";
    }

    public static class SESSION {
        public static final String NAME = "SESSION_CHAT_APP";
        public static class KEY{
            public static final String AUTH = "AUTH";
            public static final String USER_ID = "USER_ID";
            public static final String USERNAME = "USERNAME";
            public static final String PASSWORD = "PASSWORD";
        }
    }

    public static class CODE{
        public static final Integer CHOOSE_IMAGE = 102;
    }

    public static class STATUS {
        public static final String NEW = "NEW";
        public static final String ACCEPT = "ACCEPT";
        public static final String REJECT = "REJECT";
        public static final String CANCEL = "CANCEL";
    }

    public static Map<String, Object> JWT_Decoded(String JWTEncoded) {
        try {
            String[] split = JWTEncoded.split("\\.");
            return new Gson().fromJson(getJson(split[1]), Map.class);
        } catch (UnsupportedEncodingException e) {
            //Error
        }
        return null;
    }

    private static String getJson(String strEncoded) throws UnsupportedEncodingException{
        byte[] decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE);
        return new String(decodedBytes, "UTF-8");
    }

}
