package com.example.administrator.olddriverpromotionexam.ui.activity.show_questions;

import com.example.administrator.olddriverpromotionexam.App;
import com.example.administrator.olddriverpromotionexam.R;
import com.example.administrator.olddriverpromotionexam.bean.Question;
import com.example.administrator.olddriverpromotionexam.config.Config;
import com.example.administrator.olddriverpromotionexam.util.QuestionBank;
import com.example.administrator.olddriverpromotionexam.util.Sp;

import java.util.List;

/**
 * Created by Administrator on 2017/5/10 0010.
 */

public class ShowQuestionsPresenter extends ShowQuestionsContract.Presenter {
    ShowQuestionsPresenter(ShowQuestionsContract.View view) {
        super(view);
    }

    @Override
    protected void init(int type, String key) {
        switch (type){
            case Config.ORDER_PRACTICE:
                orderPractice();
                break;
            case Config.RANDOM_PRACTICE:
                randomPractice();
                break;
            case Config.KNOWLEDGE_POINT_PRACTICE:
                knowledgePointPractice(key);
                break;
            case Config.CLASSIFICATION_PRACTICE:
                classificationPractice(key);
                break;
            case Config.COLLECTION_PRACTICE:
                collectionPractice();
                break;
            case Config.WRONG_PRACTICE:
                wrongPractice();
                break;
        }
    }

    private void wrongPractice() {
        List<Question> data = QuestionBank.getDefault().getAllWrongQuestion();
        if(data.isEmpty()){
            view.showMessage(App.getAppContext().getString(R.string.no_data));
            view.finish();
            return;
        }
        view.showQuestions(data);
    }

    private void collectionPractice() {
        List<Question> data = QuestionBank.getDefault().getAllCollectionQuestion();
        if(data.isEmpty()){
            view.showMessage(App.getAppContext().getString(R.string.no_data));
            view.finish();
            return;
        }
        view.showQuestions(data);
    }

    private void classificationPractice(String key) {
        if(key == null || key.isEmpty()){
            view.showMessage(App.getAppContext().getString(R.string.sorry_please_check_the_network));
            return;
        }
        List<Question> data = QuestionBank.getDefault().getQuestionsAndAnswerMap().get(key);
        view.showQuestions(data);
    }

    private void knowledgePointPractice(String key) {
        if(key == null || key.isEmpty()){
            view.showMessage(App.getAppContext().getString(R.string.sorry_please_check_the_network));
            return;
        }
        List<Question> data = QuestionBank.getDefault().getKnowledgePointMap().get(key);
        view.showQuestions(data);
    }

    private void randomPractice() {
        List<Question> data = QuestionBank.getDefault().getAllRabdomQuestion(Config.getMaxExercisesQuestions());
        view.showQuestions(data);
    }

    private void orderPractice() {
        int currentIndex = Sp.get(Config.ORDER_PRICATICE_INDEX, 0);
        List<Question> data = QuestionBank.getDefault().getAllQuestion();
        view.showQuestions(data, currentIndex);
    }

    @Override
    protected void start() {

    }
}
