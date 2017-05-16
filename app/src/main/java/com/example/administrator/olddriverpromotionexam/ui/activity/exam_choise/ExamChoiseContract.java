package com.example.administrator.olddriverpromotionexam.ui.activity.exam_choise;

import android.content.Context;

import com.example.administrator.olddriverpromotionexam.base.MvpPresenter;
import com.example.administrator.olddriverpromotionexam.base.MvpView;

/**
 * Created by Administrator on 2017/5/14 0014.
 */

public interface ExamChoiseContract {
    interface View extends MvpView {
        void finish();
        void showMsg(String msg);
        void showFingerprintDialog();
        void hideFingerprintDialog();
    }

    abstract class Presenter extends MvpPresenter {

        protected ExamChoiseContract.View view;

        Presenter(ExamChoiseContract.View view) {
            this.view = view;
            start();
        }

        protected abstract void startRealExam(Context context);
        protected abstract void startMockExam(Context context);
        protected abstract void cancelStartRealExam();

    }
}
