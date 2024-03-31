package com.vn.chat.common.view.toolbar;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.vn.chat.R;
import com.vn.chat.common.view.icon.TextViewAwsSo;

public class MyToolbar {
    private Activity activity;
    private TextViewAwsSo twaMenuMore, twaBtnBack, twaBtnConfig;
    private TextView tvNamePage;

    public MyToolbar(Activity activity){
        this.activity = activity;
        this.init();
    }

    private void init(){
        this.twaMenuMore = this.activity.findViewById(R.id.twa_menumore);
        this.twaBtnBack = this.activity.findViewById(R.id.twa_btn_back);
        this.twaBtnConfig = this.activity.findViewById(R.id.twa_config);
        this.tvNamePage = this.activity.findViewById(R.id.tv_name_page);
    }

    public TextViewAwsSo getTwaMenuMore(){
        return this.twaMenuMore;
    }

    public TextViewAwsSo getTwaBtnBack(){
        return this.twaBtnBack;
    }

    public TextViewAwsSo getTwaBtnConfig() {
        return twaBtnConfig;
    }

    public void showBtnBack(){
        this.twaBtnBack.setVisibility(View.VISIBLE);
    }

    public void showBtnConfig(){
        this.twaBtnConfig.setVisibility(View.VISIBLE);
    }

    public void hideBtnBack(){
        this.twaBtnBack.setVisibility(View.GONE);
    }

    public void hideBtnConfig(){
        this.twaBtnConfig.setVisibility(View.GONE);
    }

    public void setNamePage(String text){
        this.tvNamePage.setText(text);
    }
}
