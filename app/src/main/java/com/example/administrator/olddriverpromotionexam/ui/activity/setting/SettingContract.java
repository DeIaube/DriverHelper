package com.example.administrator.olddriverpromotionexam.ui.activity.setting;

import android.content.Context;

import com.example.administrator.olddriverpromotionexam.base.MvpPresenter;
import com.example.administrator.olddriverpromotionexam.base.MvpView;

/**
 * Created by Administrator on 2017/5/14 0014.
 */

public interface SettingContract {
    interface View extends MvpView {
        void setFingerprintChecked(boolean check);
        void setFaceChecked(boolean check);
        void showMessage(String msg);
    }

    abstract class Presenter extends MvpPresenter {

        protected SettingContract.View view;

        Presenter(SettingContract.View view) {
            this.view = view;
            start();
        }

        abstract void fingerprintCheckedChanged(boolean check);
        abstract void faceCheckedChanged(boolean check);
        abstract void signOut(Context context);
    }
}
