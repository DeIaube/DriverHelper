package com.example.administrator.olddriverpromotionexam;

import android.app.Application;
import android.content.Context;

import com.example.administrator.olddriverpromotionexam.config.Config;
import com.example.administrator.olddriverpromotionexam.util.Sp;
import com.example.administrator.olddriverpromotionexam.util.UserUtil;

/**
 * Created by Administrator on 2017/5/9 0009.
 */

public class App extends Application {
    private static Context appContext;
    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        Sp.init(this);
        UserUtil.init();
        Config.init();
    }

    public static Context getAppContext() {
        return appContext;
    }
}
