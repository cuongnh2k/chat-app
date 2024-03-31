package com.vn.chat.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.vn.chat.common.response.ApiResponse;
import com.vn.chat.data.Device;
import com.vn.chat.data.User;
import com.vn.chat.repository.common.DeviceRepository;
import com.vn.chat.repository.common.AuthRepository;

import java.util.Map;

public class AuthViewModel extends AndroidViewModel {

    private AuthRepository authRepository;
    private DeviceRepository deviceRepository;

    public AuthViewModel(@NonNull Application application) {
        super(application);
        this.authRepository = new AuthRepository(getApplication());
        this.deviceRepository = new DeviceRepository(getApplication());
    }

    public void refresh(){
        this.authRepository.refresh(getApplication());
        this.deviceRepository.refresh(getApplication());
    }

    public LiveData<ApiResponse<Device>> login(User user){
        return this.authRepository.login(user);
    }

    public LiveData<ApiResponse<User>> register(User user){
        return this.authRepository.register(user);
    }

    public LiveData<ApiResponse<User>> activeUser(User user){
        return this.authRepository.activeUser(user);
    }

    public LiveData<ApiResponse<Device>> activeDevice(Device device){
        return this.authRepository.activeDevice(device);
    }

    public LiveData<ApiResponse<Map<String, Object>>> getDevices(String activated){
        return this.deviceRepository.getDevices(activated);
    }

    public LiveData<ApiResponse<User>> userDetail(){
        return this.authRepository.userDetail();
    }
}