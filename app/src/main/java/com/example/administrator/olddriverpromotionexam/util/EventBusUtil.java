package com.example.administrator.olddriverpromotionexam.util;

import com.example.administrator.olddriverpromotionexam.bean.Event;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2017/5/11 0011.
 */

public final class EventBusUtil {
    public static void register(Object obj){
        if(!EventBus.getDefault().isRegistered(obj)){
            EventBus.getDefault().register(obj);
        }
    }

    public static void unRegister(Object obj){
        if(EventBus.getDefault().isRegistered(obj)){
            EventBus.getDefault().unregister(obj);
        }
    }

    public static void postEvent(Event event){
        EventBus.getDefault().post(event);
    }


}
