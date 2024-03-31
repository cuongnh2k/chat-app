package com.vn.chat.views.activity;

import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.vn.chat.R;
import com.vn.chat.common.DataStatic;
import com.vn.chat.common.response.WsResponse;
import com.vn.chat.common.utils.FileUtils;
import com.vn.chat.common.view.icon.TextViewAwsRe;
import com.vn.chat.common.view.toolbar.MyToolbar;
import com.vn.chat.data.Channel;
import com.vn.chat.data.File;
import com.vn.chat.viewmodel.HomeViewModel;
import com.vn.chat.views.fragment.home.FragmentContact;
import com.vn.chat.views.fragment.home.FragmentGallery;
import com.vn.chat.views.fragment.home.FragmentHome;
import com.vn.chat.views.fragment.home.FragmentMessage;
import com.vn.chat.views.fragment.home.FragmentMessageConfig;
import com.vn.chat.views.fragment.home.FragmentProfile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;
import ua.naiksoftware.stomp.dto.StompHeader;

public class HomeActivity extends CommonActivity {
    private static final String TAG = "HomeActivity";
    private StompClient stomp;
    private CompositeDisposable compositeDisposable;
    private MyToolbar toolbar;
    private HomeViewModel homeViewModel;
    private FragmentHome fragmentHome;
    private FragmentContact fragmentContact;
    private FragmentProfile fragmentProfile;
    private FragmentMessage fragmentMessage;
    private FragmentMessageConfig fragmentMessageConfig;
    private FragmentGallery fragmentGallery;
    private Fragment fragmentTarget;
    private TextViewAwsRe icoChanel, icoContact, icoProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.init();
        this.actionView();
        loadDetailInfo();
        setFragmentHome();
    }

    private void init(){
        this.homeViewModel = ViewModelProviders.of(HomeActivity.this).get(HomeViewModel.class);
        this.toolbar = new MyToolbar(HomeActivity.this);
        this.icoChanel = findViewById(R.id.twa_btn_chanel);
        this.icoContact = findViewById(R.id.twa_btn_contact);
        this.icoProfile = findViewById(R.id.twa_btn_profile);
    }

    public MyToolbar getToolbar() {
        return toolbar;
    }

    private void actionView(){
        this.icoChanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragmentHome();
            }
        });

        this.icoProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragmentProfile();
            }
        });

        this.icoContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragmentContact();
            }
        });

        this.toolbar.getTwaBtnBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void initWs(){
        stomp = Stomp.over(Stomp.ConnectionProvider.OKHTTP, DataStatic.BASE_WS+DataStatic.AUTHOR.ACCESS_TOKEN);
//        stomp = Stomp.over(Stomp.ConnectionProvider.OKHTTP, DataStatic.BASE_WS+"/ws");
        resetSubscriptions();
        connect();
    }

    private void resetSubscriptions() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
        compositeDisposable = new CompositeDisposable();
    }

    public void connect(){
//        String uid = SessionUtils.get(this, DataStatic.SESSION.KEY.USER_ID, "");
        Map<String, Object> jwtDecode = DataStatic.JWT_Decoded(DataStatic.AUTHOR.ACCESS_TOKEN);
        String uid = (String) jwtDecode.get("sub");
//        String uid = "5";
        List<StompHeader> headers = new ArrayList<>();
        headers.add(new StompHeader("id", uid));

        stomp.withClientHeartbeat(2000).withServerHeartbeat(2000);
        resetSubscriptions();

        Disposable dispLifecycle = stomp.lifecycle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(lifecycleEvent -> {
                    switch (lifecycleEvent.getType()) {
                        case OPENED:
                            Log.d(TAG, "Stomp connection opened");
                            break;
                        case ERROR:
                            Log.e(TAG, "Stomp connection error");
                            break;
                        case CLOSED:
                            Log.d(TAG, "Stomp connection closed");
                            resetSubscriptions();
                            break;
                        case FAILED_SERVER_HEARTBEAT:
                            Log.d(TAG, "Stomp failed server heartbeat");
                            break;
                    }
                });

        compositeDisposable.add(dispLifecycle);

        // Receive greetings
        Disposable dispTopic = stomp.topic("/user/"+uid+"/private")
//        Disposable dispTopic = stomp.topic("/user/"+uid+"/queue/messages")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(topicMessage -> {
                    WsResponse res = new Gson().fromJson(topicMessage.getPayload(), WsResponse.class);
                    if(res.getType().equalsIgnoreCase("CREATE_MESSAGE")){
                        if(fragmentTarget == fragmentMessage){
                            if(fragmentMessage.getChannel().getId().equalsIgnoreCase(res.getChannel().getId())){
                                fragmentMessage.pushMessage(res.getChannel().getLatestMessage());
                            }
                        }else if(fragmentTarget == fragmentHome){
                            fragmentHome.updateMessage(res.getChannel());
                        }
                    }
                    Log.d(TAG, topicMessage.getPayload());
                    //                    Message messageInfo = new Gson().fromJson(topicMessage.getPayload(), Message.class);
//                    if(fragmentActive instanceof ChatFragment){
//                        chatFragment.updateItem(messageInfo);
//                    }else if(fragmentActive instanceof MessageFragment){
//                        if(messageFragment.getIdGroup().equals(messageInfo.getIdGroup())){
//                            messageFragment.pushMessage(messageInfo);
//                        }
//                    }
                }, throwable -> {
                    Log.e(TAG, throwable.getMessage());
                });

        compositeDisposable.add(dispTopic);
        stomp.connect(headers);
    }

    public HomeViewModel getHomeViewModel() {
        return homeViewModel;
    }

    private void loadDetailInfo(){
        homeViewModel.userDetail().observe(this, res -> {
            if(res.getCode().equals(1)){
                DataStatic.AUTHOR.USER_INFO = res.getData();
                this.initWs();
            }
        });
    }

    private void selectorMenu(TextViewAwsRe tvSelected){
        this.icoChanel.setTextColor(getResources().getColor(R.color.black));
        this.icoContact.setTextColor(getResources().getColor(R.color.black));
        this.icoProfile.setTextColor(getResources().getColor(R.color.black));
        tvSelected.setTextColor(getResources().getColor(R.color.color_blue));
    }

    public void visibleMenu(int v){
        this.icoChanel.setVisibility(v);
        this.icoContact.setVisibility(v);
        this.icoProfile.setVisibility(v);
    }

    public void setFragmentHome(){
        selectorMenu(icoChanel);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if(fragmentHome == null){
            fragmentHome = new FragmentHome(HomeActivity.this);
        }
        fragmentTarget = fragmentHome;
        transaction.replace(R.id.layout_content, fragmentHome).addToBackStack(DataStatic.STACK_APP);
        transaction.commitAllowingStateLoss();
    }

    public void setFragmentContact(){
        selectorMenu(icoContact);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if(fragmentContact == null){
            fragmentContact = new FragmentContact(HomeActivity.this);
        }
        fragmentTarget = fragmentContact;
        transaction.replace(R.id.layout_content, fragmentContact).addToBackStack(DataStatic.STACK_APP);
        transaction.commitAllowingStateLoss();
    }

    public void setFragmentProfile(){
        selectorMenu(icoProfile);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if(fragmentProfile == null){
            fragmentProfile = new FragmentProfile(HomeActivity.this);
        }
        fragmentTarget = fragmentProfile;
        transaction.replace(R.id.layout_content, fragmentProfile).addToBackStack(DataStatic.STACK_APP);
        transaction.commitAllowingStateLoss();
    }

    public void setFragmentMessage(Channel channel){
        visibleMenu(View.GONE);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        fragmentMessage = new FragmentMessage(HomeActivity.this, channel);
        fragmentTarget = fragmentMessage;
        transaction.replace(R.id.layout_content, fragmentMessage).addToBackStack(DataStatic.STACK_APP);
        transaction.commitAllowingStateLoss();
    }

    public void setFragmentMessageConfig(Channel channel){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        fragmentMessageConfig = new FragmentMessageConfig(HomeActivity.this, channel);
        fragmentTarget = fragmentMessageConfig;
        transaction.replace(R.id.layout_content, fragmentMessageConfig).addToBackStack(DataStatic.STACK_APP);
        transaction.commitAllowingStateLoss();
    }

    public void setFragmentGallery(Channel channel){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        fragmentGallery = new FragmentGallery(HomeActivity.this, channel);
        fragmentTarget = fragmentGallery;
        transaction.replace(R.id.layout_content, fragmentGallery).addToBackStack(DataStatic.STACK_APP);
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Uri selectedImageUri = data.getData();
//            String fragment = data.getStringExtra(IntentUtils.KEY.FRAGMENT);
            if (null != selectedImageUri) {
                //Upload image
                File file = new File(FileUtils.getPath(HomeActivity.this, selectedImageUri));
                homeViewModel.postFile(file).observe(this, res -> {
                    if(res.getCode().equals(1)){
                        if(fragmentTarget != null){
                            if(fragmentTarget instanceof FragmentMessage){
                                fragmentMessage.setFileInfo(res.getData());
                            }
                            if(fragmentTarget instanceof FragmentContact){
                                fragmentContact.getDialogCreateGroup().setImageAvatar(res.getData());
                            }
                            if(fragmentTarget instanceof FragmentProfile){

                            }
                        }
                    }
                });
            }
        }
    }

    public Fragment getFragmentTarget() {
        return fragmentTarget;
    }

    public FragmentMessage getFragmentMessage() {
        return fragmentMessage;
    }

    public FragmentContact getFragmentContact() {
        return fragmentContact;
    }

    public FragmentMessageConfig getFragmentMessageConfig() {
        return fragmentMessageConfig;
    }
}