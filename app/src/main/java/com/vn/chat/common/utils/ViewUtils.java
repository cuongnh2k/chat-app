package com.vn.chat.common.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class ViewUtils {
    public static void hideKeyboard(Activity activity){
        View tmpView = activity.getCurrentFocus();
        if (tmpView != null) {
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(tmpView.getWindowToken(), 0);
        }
    }
}
