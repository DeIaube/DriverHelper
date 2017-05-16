package com.example.administrator.olddriverpromotionexam.ui.activity.splash;

import android.os.Handler;
import android.os.Message;

import com.example.administrator.olddriverpromotionexam.ui.activity.home.HomeActivity;
import com.example.administrator.olddriverpromotionexam.R;
import com.example.administrator.olddriverpromotionexam.base.BaseActivity;
import com.example.administrator.olddriverpromotionexam.util.ExperienceBank;
import com.example.administrator.olddriverpromotionexam.util.QuestionBank;

public class SplashActivity extends BaseActivity {


    @Override
    protected int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void init() {
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                HomeActivity.start(SplashActivity.this);
                onBackPressed();
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                QuestionBank.getDefault().init(SplashActivity.this, null);
                ExperienceBank.getDefault().init(SplashActivity.this, null);
                // 添加广告停留时间
                Message message = handler.obtainMessage();
                message.sendToTarget();
            }
        }).start();
    }

}
