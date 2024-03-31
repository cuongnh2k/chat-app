package com.vn.chat.common.utils;

import android.app.Activity;
import android.content.Intent;

public class IntentUtils {

    public static final Integer CHOOSE_IMAGE = 301;

    public static class FRAGMENT {
        public static final String MESSAGE = "fragment_message";
        public static final String CONTACT = "fragment_contact";
    }

    public static class KEY {
        public static final String FRAGMENT = "fragment";
        public static final String FLAG = "flag";
    }

    public static void chooseImage(Activity context, String fragment){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(KEY.FRAGMENT, fragment);
        context.startActivityForResult(Intent.createChooser(intent, "Select Picture"), CHOOSE_IMAGE);
    }

}
