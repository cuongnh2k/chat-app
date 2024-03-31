package com.vn.chat.common.utils;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.vn.chat.common.DataStatic;

public class SessionUtils {

    public static void set(Context context, String key, Object obj){
        SharedPreferences sp = context.getSharedPreferences(DataStatic.SESSION.NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, new Gson().toJson(obj));
        editor.apply();
    }

    public static void set(Context context, String key, Integer value){
        SharedPreferences sp = context.getSharedPreferences(DataStatic.SESSION.NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static void set(Context context, String key, boolean value){
        SharedPreferences sp = context.getSharedPreferences(DataStatic.SESSION.NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static void set(Context context, String key, String value){
        SharedPreferences sp = context.getSharedPreferences(DataStatic.SESSION.NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String get(Context context, String key, String def){
        if(def == null) def = "";
        SharedPreferences sp = context.getSharedPreferences(DataStatic.SESSION.NAME, MODE_PRIVATE);
        return sp.getString(key, def);
    }

    public static Integer get(Context context, String key, Integer def){
        if(def == null) def = 0;
        SharedPreferences sp = context.getSharedPreferences(DataStatic.SESSION.NAME, MODE_PRIVATE);
        return sp.getInt(key, def);
    }

    public static boolean get(Context context, String key, boolean def){
        SharedPreferences sp = context.getSharedPreferences(DataStatic.SESSION.NAME, MODE_PRIVATE);
        return sp.getBoolean(key, def);
    }

    public static void remove(Context context, String key){

        SharedPreferences sp = context.getSharedPreferences(DataStatic.SESSION.NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.apply();
    }
}
