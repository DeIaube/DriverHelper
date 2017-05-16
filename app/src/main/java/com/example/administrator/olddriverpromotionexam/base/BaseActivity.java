package com.example.administrator.olddriverpromotionexam.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.olddriverpromotionexam.util.EventBusUtil;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/25 0025.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected String tag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.inject(this);
        tag = getClass().getSimpleName();
        if(needToUseEventBus()){
            EventBusUtil.register(this);
        }

        init();
    }

    protected abstract int getLayout();
    protected abstract void init();

    protected void replaceFragment(int containerViewId, Fragment fragment, String tag){
        if(getSupportFragmentManager().findFragmentByTag(tag) == null){
            getSupportFragmentManager().beginTransaction()
                    .replace(containerViewId, fragment, tag)
                    .commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
        if(needToUseEventBus()){
            EventBusUtil.unRegister(this);
        }
    }

    protected boolean needToUseEventBus(){
        return false;
    }
}
