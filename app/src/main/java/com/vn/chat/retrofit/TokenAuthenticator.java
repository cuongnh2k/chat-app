package com.vn.chat.retrofit;

import android.app.Application;
import android.content.Intent;

import com.google.gson.Gson;
import com.vn.chat.common.DataStatic;
import com.vn.chat.common.response.ApiResponse;
import com.vn.chat.common.utils.SessionUtils;
import com.vn.chat.data.Device;
import com.vn.chat.views.activity.AuthActivity;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class TokenAuthenticator implements Authenticator {
    Application application;

    public TokenAuthenticator(Application application){
        this.application = application;
    }

    @Override
    public Request authenticate(Route route, Response response) throws IOException {

        boolean refreshResult = refreshToken();
        if (refreshResult) {
            //token moi cua ban day
            String accessToken = "Bearer "+DataStatic.AUTHOR.ACCESS_TOKEN;
            return response.request().newBuilder().header("Authorization", accessToken).build();
        } else {
//            Intent intent = new Intent(application, AuthActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            application.startActivity(intent);
            return null;
        }
    }

    public boolean refreshToken() throws IOException {
        URL refreshUrl = new URL("http://103.173.154.123:61453/api/v1/common/device/refresh-token");
        HttpURLConnection urlConnection = (HttpURLConnection) refreshUrl.openConnection();
        urlConnection.setDoInput(true);
        urlConnection.setRequestMethod("POST");
        urlConnection.setRequestProperty("User-Agent", "okhttp/3.14.7");
        urlConnection.setRequestProperty("Authorization", "Bearer "+DataStatic.AUTHOR.REFRESH_TOKEN);
        urlConnection.setUseCaches(false);
        String urlParameters = "";

        urlConnection.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = urlConnection.getResponseCode();

        if (responseCode == 200) {
            BufferedReader in =
                    new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // this gson part is optional , you can read response directly from Json too
            Gson gson = new Gson();
            ApiResponse res =
                    gson.fromJson(response.toString(), ApiResponse.class);
            SessionUtils.set(this.application, DataStatic.SESSION.KEY.AUTH, res.getData());

            String strJson = SessionUtils.get(this.application, DataStatic.SESSION.KEY.AUTH, "{}");
            Device auth = new Gson().fromJson(strJson, Device.class);
            DataStatic.AUTHOR.ACCESS_TOKEN = auth.getAccessToken();
            DataStatic.AUTHOR.REFRESH_TOKEN = auth.getRefreshToken();
            DataStatic.AUTHOR.DEVICE_ID = auth.getDeviceId();

            // handle new token ...
            // save it to the sharedpreferences, storage bla bla ...
            return true;
        } else {
            //cannot refresh
            return false;
        }
    }
}
