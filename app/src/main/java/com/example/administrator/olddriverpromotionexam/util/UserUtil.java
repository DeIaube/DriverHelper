package com.example.administrator.olddriverpromotionexam.util;

import android.content.Context;
import android.util.Log;

import com.example.administrator.olddriverpromotionexam.bean.User;
import com.example.administrator.olddriverpromotionexam.db.UserHelper;
import com.google.gson.Gson;

/**
 * Created by Administrator on 2017/5/13 0013.
 */

public final class UserUtil {

    private static User user;
    private static final String USER = "user";

    public static User getUser() {
        return user;
    }

    public static void init(){
        user = new Gson().fromJson(Sp.get(USER,""), User.class);
    }

    public static boolean login(Context context, String username, String password){
        user = UserHelper.login(context, username, password);
        if(user == null){
            return false;
        }
        Sp.put(USER, new Gson().toJson(user));
        return true;
    }

    public static boolean hasUser(Context context, String username){
        boolean hasUser = UserHelper.hasUser(context, username);
        return hasUser;
    }

    public static void registe(Context context, String username, String password){
        UserHelper.registe(context, username, password);
        user = new User(username, password);
        Sp.put(USER, new Gson().toJson(user));
    }

    public static void destory(Context context){
        if(user != null){
            UserHelper.updateUser(context, user);
            Sp.put(USER, new Gson().toJson(user));
            Log.i("fuck", user.toString());
        }
    }

    public static void signOut(Context context) {
        UserHelper.updateUser(context, user);
        Sp.put(USER, null);
        user = null;
    }
}
