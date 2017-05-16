package com.example.administrator.olddriverpromotionexam.ui.activity.login;

import android.content.Context;

import com.example.administrator.olddriverpromotionexam.base.MvpPresenter;
import com.example.administrator.olddriverpromotionexam.base.MvpView;

/**
 * Created by Administrator on 2017/5/12 0012.
 */

public interface LoginContract {
    interface View extends MvpView {
        void showMessage(String msg);
        void showProgress();
        void hideProgress();
        void loginSucceed();
    }

    abstract class Presenter extends MvpPresenter {

        protected LoginContract.View view;

        Presenter(LoginContract.View view) {
            this.view = view;
            start();
        }

        abstract void login(Context context,String username, String password);
    }
}
