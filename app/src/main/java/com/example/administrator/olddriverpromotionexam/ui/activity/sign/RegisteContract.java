package com.example.administrator.olddriverpromotionexam.ui.activity.sign;

import android.content.Context;

import com.example.administrator.olddriverpromotionexam.base.MvpPresenter;
import com.example.administrator.olddriverpromotionexam.base.MvpView;

/**
 * Created by Administrator on 2017/5/12 0012.
 */

public interface RegisteContract {
    interface View extends MvpView {
        void showMessage(String msg);
        void showProgress();
        void hideProgress();
        void registeSucceed();
    }

    abstract class Presenter extends MvpPresenter {

        protected RegisteContract.View view;

        Presenter(RegisteContract.View view) {
            this.view = view;
            start();
        }

        abstract void sign(Context context, String username, String password, String rePassword);
    }
}
