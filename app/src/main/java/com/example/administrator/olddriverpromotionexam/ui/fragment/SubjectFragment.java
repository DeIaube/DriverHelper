package com.example.administrator.olddriverpromotionexam.ui.fragment;

import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.example.administrator.olddriverpromotionexam.R;
import com.example.administrator.olddriverpromotionexam.base.BaseFragment;
import com.example.administrator.olddriverpromotionexam.config.Config;
import com.example.administrator.olddriverpromotionexam.ui.activity.classification.ClassificationActivity;
import com.example.administrator.olddriverpromotionexam.ui.activity.exam_choise.ExamChoiseActivity;
import com.example.administrator.olddriverpromotionexam.ui.activity.recoder.RecoderActivity;
import com.example.administrator.olddriverpromotionexam.ui.activity.show_questions.ShowQuestionsActivity;

import butterknife.InjectView;

public class SubjectFragment extends BaseFragment implements View.OnClickListener {

    @InjectView(R.id.random_practice)
    AppCompatButton randomPractice;
    @InjectView(R.id.order_practice)
    AppCompatButton orderPractice;
    @InjectView(R.id.knowledge_point_practice)
    AppCompatButton knowledgePointPractice;
    @InjectView(R.id.classification_practice)
    AppCompatButton classificationPractice;
    @InjectView(R.id.start_exam)
    AppCompatButton startExam;
    @InjectView(R.id.my_wrong_question)
    AppCompatButton myWrongQuestion;
    @InjectView(R.id.my_collection)
    AppCompatButton myCollection;
    @InjectView(R.id.exam_record)
    AppCompatButton examRecord;

    @Override
    protected void init() {
        randomPractice.setOnClickListener(this);
        orderPractice.setOnClickListener(this);
        knowledgePointPractice.setOnClickListener(this);
        classificationPractice.setOnClickListener(this);
        startExam.setOnClickListener(this);
        myWrongQuestion.setOnClickListener(this);
        myCollection.setOnClickListener(this);
        examRecord.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_subject;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.random_practice:
                startRandomPractice();
                break;
            case R.id.order_practice:
                startOrderPractice();
                break;
            case R.id.knowledge_point_practice:
                startKnowledgePointPractice();
                break;
            case R.id.classification_practice:
                startClassificationPractice();
                break;
            case R.id.my_wrong_question:
                startMyWrongQuestion();
                break;
            case R.id.start_exam:
                startExam();
                break;
            case R.id.my_collection:
                startMyCollection();
                break;
            case R.id.exam_record:
                startExamRecord();
                break;
        }
    }

    /**
     * 查看考试记录
     */
    private void startExamRecord() {
        RecoderActivity.start(getContext());
    }

    /**
     * 查看收藏
     */
    private void startMyCollection() {
        ShowQuestionsActivity.start(getContext(), Config.COLLECTION_PRACTICE,null);
    }

    /**
     * 选择考试
     */
    private void startExam() {
        ExamChoiseActivity.start(getContext());
    }

    /**
     * 查看我的错题
     */
    private void startMyWrongQuestion() {
        ShowQuestionsActivity.start(getContext(), Config.WRONG_PRACTICE,null);
    }

    /**
     * 开始分类练习
     */
    private void startClassificationPractice() {
        ClassificationActivity.start(getContext(), Config.CLASSIFICATION_PRACTICE);
    }

    /**
     * 开始知识点练习
     */
    private void startKnowledgePointPractice() {
        ClassificationActivity.start(getContext(), Config.KNOWLEDGE_POINT_PRACTICE);
    }

    /**
     * 开始顺序练习
     */
    private void startOrderPractice() {
        ShowQuestionsActivity.start(getContext(), Config.ORDER_PRACTICE,null);
    }

    /**
     * 开始随机练习
     */
    private void startRandomPractice() {
        ShowQuestionsActivity.start(getContext(), Config.RANDOM_PRACTICE,null);
    }
}
