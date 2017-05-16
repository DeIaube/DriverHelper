package com.example.administrator.olddriverpromotionexam.ui.activity.exam;

import com.example.administrator.olddriverpromotionexam.base.MvpPresenter;
import com.example.administrator.olddriverpromotionexam.base.MvpView;
import com.example.administrator.olddriverpromotionexam.bean.QuestionRecoder;
import com.example.administrator.olddriverpromotionexam.bean.QuestionState;

/**
 * Created by Administrator on 2017/5/11 0011.
 */

public interface ExamContract {
    interface View extends MvpView {
        void updateTimer(String time);
        void forcedSubmit();
        void updateErrorNumber(String number);
        void updateCorrectNumber(String number);
        void updateBottomSheet(QuestionState[] data);
        void hideBottomSheet();
        void jumpPagerIndex(int index);
        void showQuerySubmitMessage(String msg);
        void showForcedSubmitMessage(String msg);
        void showExamResule(QuestionRecoder recoder);

    }

    abstract class Presenter extends MvpPresenter {

        protected ExamContract.View view;

        Presenter(ExamContract.View view) {
            this.view = view;
            start();
        }

        protected abstract void destory();
        protected abstract void doUpdateBottomSheet();
        protected abstract void errorIncrease(int index);
        protected abstract void correctIncrease(int index);
        protected abstract void alterIndex(int index);
        protected abstract void queryToSubmit();
        protected abstract void forcedToSubmit();
        protected abstract void submitExam();

    }
}
