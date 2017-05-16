package com.example.administrator.olddriverpromotionexam.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/5/9 0009.
 */

public final class Sp {
    private static SharedPreferences sp;
    private static SharedPreferences.Editor editor;

    public static void init(Context context){
        sp = context.getSharedPreferences(context.getPackageName(), context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public static void put(String tag, int value){
        editor.putInt(tag, value);
        editor.apply();
    }

    public static int get(String tag, int value){
        return sp.getInt(tag, value);
    }

    public static void put(String tag, String value){
        editor.putString(tag, value);
        editor.apply();
    }

    public static String get(String tag, String value){
        return sp.getString(tag, value);
    }

    public static boolean get(String tag, boolean value){
        return sp.getBoolean(tag, value);
    }

    public static void put(String tag, boolean value){
        editor.putBoolean(tag, value);
        editor.apply();
    }


}
