package com.example.administrator.olddriverpromotionexam.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.olddriverpromotionexam.util.EventBusUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/26 0026.
 */

public abstract class BaseFragment extends Fragment {
    protected View rootView;
    protected String tag;
    protected Bundle bundle;
    private static final Map<Object, Bundle> map;
    static {
        map = new HashMap<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView == null){
            rootView = LayoutInflater.from(getContext()).inflate(getLayoutId(), container, false);
        }
        ButterKnife.inject(this, rootView);
        bundle = new Bundle();
        tag = getClass().getSimpleName();
        if(needToUseEventBus()){
            EventBusUtil.register(this);
        }
        init();

        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        loadData(getBundle(this));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        saveData(getBundle(this));
    }

    protected void saveData(Bundle bundle){

    }

    protected void loadData(Bundle bundle){

    }

    private static Bundle getBundle(Object obj){
        Bundle bundle = null;
        if(map.get(obj) == null){
            bundle = new Bundle();
            map.put(obj, bundle);
        }
        bundle = map.get(obj);
        return bundle;
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
        if(needToUseEventBus()){
            EventBusUtil.unRegister(this);
        }
    }



    protected boolean needToUseEventBus(){
        return false;
    }

    protected abstract void init();

    protected abstract int getLayoutId();
}
