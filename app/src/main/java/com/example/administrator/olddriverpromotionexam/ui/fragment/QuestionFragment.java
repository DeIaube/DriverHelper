package com.example.administrator.olddriverpromotionexam.ui.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.olddriverpromotionexam.R;
import com.example.administrator.olddriverpromotionexam.adapter.OptionsArrayAdapter;
import com.example.administrator.olddriverpromotionexam.base.BaseFragment;
import com.example.administrator.olddriverpromotionexam.bean.Event;
import com.example.administrator.olddriverpromotionexam.bean.EventCode;
import com.example.administrator.olddriverpromotionexam.bean.Question;
import com.example.administrator.olddriverpromotionexam.config.Config;
import com.example.administrator.olddriverpromotionexam.util.BitmapLoader;
import com.example.administrator.olddriverpromotionexam.util.EventBusUtil;
import com.example.administrator.olddriverpromotionexam.util.QuestionBank;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.InjectView;

/**
 * Created by Administrator on 2017/5/10 0010.
 */

public class QuestionFragment extends BaseFragment implements OptionsArrayAdapter.OnClickListener {

    @InjectView(R.id.question_type)
    TextView questionType;
    @InjectView(R.id.question_content)
    TextView questionContent;
    @InjectView(R.id.question_img)
    ImageView questionImg;
    @InjectView(R.id.question_options)
    RecyclerView questionOptions;
    @InjectView(R.id.correct_answer)
    TextView correctAnswer;
    @InjectView(R.id.your_answer)
    TextView yourAnswer;
    @InjectView(R.id.answer_layout)
    LinearLayout answerLayout;
    @InjectView(R.id.test_center)
    TextView testCenter;
    @InjectView(R.id.question_analysis)
    TextView questionAnalysis;
    @InjectView(R.id.analysis_layout)
    LinearLayout analysisLayout;

    private static final String CHOISE = "choise";
    private static final String CORRET = "choise";
    private static final String IS_SAVE = "isSave";

    private static final String QUESTION = "question";
    private Question question;
    private OptionsArrayAdapter adapter;
    private int currentMode = Config.PRACTICE_MODE;
    private Bitmap bitmap;
    private int choise;
    private int corret;
    private boolean isSave;

    @Override
    protected void init() {
        question = getArguments().getParcelable(QUESTION);
        questionType.setText(question.getType().equals("0") ? "判断题" : "选择题");
        questionContent.setText(question.getContent());
        testCenter.setText(question.getKnowledgePoints().get(0));
        questionAnalysis.setText(question.getAnalysis());
        answerLayout.setVisibility(View.GONE);
        bitmap = BitmapLoader.getBitmap(getContext(), question.getId());
        if(bitmap == null){
            questionImg.setVisibility(View.GONE);
        }else{
            questionImg.setImageBitmap(bitmap);
        }

        adapter = new OptionsArrayAdapter(getContext(), question.getOptions());
        questionOptions.setLayoutManager(new LinearLayoutManager(getContext()));
        questionOptions.setAdapter(adapter);


        choise = -1;
        corret = -1;
        isSave = false;

        judgmentState();
    }

    /**
     * 根据当前模式 选择不同的视图
     */
    private void judgmentState() {
        if(currentMode == Config.PRACTICE_MODE){
            switchPracticeMode();
        }else if(currentMode == Config.RECITE_MODE){
            switchReciteMode();
        }else{
            switchExam();
        }
    }

    private void switchExam() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveEvent(Event event){
        int code = event.getCode();
        switch (code){
            case EventCode.A:
                changeMode();
                break;
        }
    }

    public void changeMode(){
        currentMode = currentMode == Config.PRACTICE_MODE ? Config.RECITE_MODE : Config.PRACTICE_MODE;
        judgmentState();
    }

    /**
     * 切换到背题模式
     */
    private void switchReciteMode() {
        Event<Integer> event = new Event<Integer>(EventCode.B);
        event.setData(R.string.recite_mode);
        EventBusUtil.postEvent(event);
//        ((ShowQuestionsActivity)getActivity()).setTitle(R.string.recite_mode);

        analysisLayout.setVisibility(View.VISIBLE);
        answerLayout.setVisibility(View.GONE);
        adapter.setOnClickListener(null);
        adapter.setChoise(Integer.valueOf(question.getCorrectAnswer())-1);
        adapter.notifyDataSetChanged();
    }

    /**
     * 切换到练习模式
     */
    private void switchPracticeMode() {
        Event<Integer> event = new Event<Integer>(EventCode.B);
        event.setData(R.string.practice_mode);
        EventBusUtil.postEvent(event);
//        ((ShowQuestionsActivity)getActivity()).setTitle(R.string.practice_mode);

        analysisLayout.setVisibility(View.GONE);
        adapter.setOnClickListener(this);
        adapter.clearChoise();


//        adapter.setChoise(choise);
//        refreshOption();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_question;
    }

    public static Fragment newInstance(Question question){
        QuestionFragment fragment = new QuestionFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(QUESTION, question);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onClick(View view, int choise) {
        corret = Integer.valueOf(question.getCorrectAnswer())-1;
        this.choise = choise;
        this.isSave = true;
        judgmentOption(choise);
    }

    private void judgmentOption(int choise) {
        refreshOption();
        if(choise == corret){
            //答题正确应该自动进入下一题
            answerCorrect();
        }else{
            //答错应该将错题加入错题库
            yourAnswer.setTextColor(getResources().getColor(R.color.red));
            answerError();
        }

    }

    private void refreshOption(){
        adapter.setChoise(choise);
        adapter.notifyDataSetChanged();
        answerLayout.setVisibility(View.VISIBLE);
        analysisLayout.setVisibility(View.VISIBLE);
        correctAnswer.setText(String.format("正确答案:%c", corret + 'A'));
        yourAnswer.setText(String.format("您的答案:%c", choise + 'A'));
        adapter.setOnClickListener(null);
    }

    @Override
    protected boolean needToUseEventBus() {
        return true;
    }

    /**
     * 答题错误 错题加入错题库
     */
    private void answerError() {
        QuestionBank.getDefault().addWrong(question.getId());

        Event event = new Event(EventCode.C);
        EventBusUtil.postEvent(event);
//      ((ShowQuestionsActivity)getActivity()).errorNumberIncrease();
    }

    /**
     * 答题正确 自动进入下一题
     */
    private void answerCorrect() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                ((ShowQuestionsActivity)getActivity()).nextQuestion();

                Event event = new Event(EventCode.E);
                EventBusUtil.postEvent(event);
            }
        }, 300);

        Event event = new Event(EventCode.D);
        EventBusUtil.postEvent(event);
//      ((ShowQuestionsActivity)getActivity()).correctNumberIncrease();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(bitmap != null){
            bitmap.recycle();
            bitmap = null;
        }

    }

    @Override
    protected void saveData(Bundle bundle) {
        bundle.putInt(CORRET, corret);
        bundle.putInt(CHOISE, choise);
        bundle.putBoolean(IS_SAVE, isSave);
    }

    @Override
    protected void loadData(Bundle bundle) {
        isSave = bundle.getBoolean(IS_SAVE);
        if(isSave){
            corret = bundle.getInt(CORRET);
            choise = bundle.getInt(CHOISE);
            isSave = true;
            refreshOption();
        }
    }
}
