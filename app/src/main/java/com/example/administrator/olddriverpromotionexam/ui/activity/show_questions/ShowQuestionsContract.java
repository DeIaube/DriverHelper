package com.example.administrator.olddriverpromotionexam.ui.activity.show_questions;

import com.example.administrator.olddriverpromotionexam.base.MvpPresenter;
import com.example.administrator.olddriverpromotionexam.base.MvpView;
import com.example.administrator.olddriverpromotionexam.bean.Question;

import java.util.List;

/**
 * Created by Administrator on 2017/5/10 0010.
 */

public interface ShowQuestionsContract {
    interface View extends MvpView {
        void finish();
        void showMessage(String msg);
        void showQuestions(List<Question> data);
        void showQuestions(List<Question> data, int index);
    }

    abstract class Presenter extends MvpPresenter {
        protected View view;
        Presenter(View view) {
            this.view = view;
            start();
        }
        protected abstract void init(int type, String key);
    }
}
