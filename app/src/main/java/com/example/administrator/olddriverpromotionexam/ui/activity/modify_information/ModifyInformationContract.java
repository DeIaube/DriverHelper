package com.example.administrator.olddriverpromotionexam.ui.activity.modify_information;

import android.content.Context;

import com.example.administrator.olddriverpromotionexam.base.MvpPresenter;
import com.example.administrator.olddriverpromotionexam.base.MvpView;
import com.example.administrator.olddriverpromotionexam.ui.activity.login.LoginActivity;
import com.example.administrator.olddriverpromotionexam.util.UserUtil;

/**
 * Created by Administrator on 2017/5/15 0015.
 */

public interface ModifyInformationContract {
    interface View extends MvpView {
        void showMessage(String msg);
        void setTitle(String msg);
        void showModifyPassword();
        void showModifyMail();
        void showModifyPhoneNumber();
        void finish();

    }

    abstract class Presenter extends MvpPresenter {

        protected ModifyInformationContract.View view;
        protected Context context;

        Presenter(ModifyInformationContract.View view) {
            this.view = view;
            this.context = (Context) view;
            if(UserUtil.getUser() == null){
                view.showMessage("请先登录");
                view.finish();
                LoginActivity.start(context);
            }
            start();
        }

        abstract void sunmit(String s1, String s2);
    }
}
