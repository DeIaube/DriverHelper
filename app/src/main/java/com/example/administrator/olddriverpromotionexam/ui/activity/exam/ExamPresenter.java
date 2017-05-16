package com.example.administrator.olddriverpromotionexam.ui.activity.exam;

import android.os.Handler;
import android.os.Message;

import com.example.administrator.olddriverpromotionexam.App;
import com.example.administrator.olddriverpromotionexam.bean.QuestionRecoder;
import com.example.administrator.olddriverpromotionexam.bean.QuestionState;
import com.example.administrator.olddriverpromotionexam.config.Config;
import com.example.administrator.olddriverpromotionexam.db.QuestionRecoderOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/5/11 0011.
 */

public class ExamPresenter extends ExamContract.Presenter {
    private boolean isRun;
    private Handler handler;
    private int errorNumber;
    private int correctNumber;
    private QuestionState[] questionStates;
    private int timeConsum;

    public ExamPresenter(ExamContract.View view) {
        super(view);
        errorNumber = 0;
        correctNumber = 0;
        timeConsum = 0;
        questionStates = new QuestionState[Config.getMaxExamQuestions()];
    }

    @Override
    protected void destory() {
        isRun = false;
    }

    @Override
    protected void doUpdateBottomSheet() {
        view.updateBottomSheet(questionStates);
    }

    @Override
    protected void errorIncrease(int index) {
        errorNumber++;
        questionStates[index] = QuestionState.ERROR;
        view.updateErrorNumber(String.valueOf(errorNumber));
        if(errorNumber >= 10){
            forcedToSubmit();
        }
    }

    @Override
    protected void correctIncrease(int index) {
        correctNumber++;
        questionStates[index] = QuestionState.CORRECT;
        view.updateCorrectNumber(String.valueOf(correctNumber));
    }

    @Override
    protected void alterIndex(int index) {
        view.hideBottomSheet();
        view.jumpPagerIndex(index);
    }

    @Override
    protected void queryToSubmit() {
        String msg = getExamMessage();
        view.showQuerySubmitMessage(msg);
    }

    @Override
    protected void forcedToSubmit() {
        String msg = getExamMessage();
        view.showForcedSubmitMessage(msg);
    }

    private String getExamMessage() {
        int maxExamQuestions = Config.getMaxExamQuestions();
        int grade = 0;
        int answered = 0;
        for (QuestionState questionState : questionStates) {
            if(questionState == QuestionState.CORRECT){
                grade++;
                answered++;
            }else if(questionState == QuestionState.ERROR){
                answered++;
            }
        }
        String msg = String.format("您已回答了%d道题(共%d题),考试得分%d分,确定交卷?", answered, maxExamQuestions, grade);
        return msg;
    }



    @Override
    protected void submitExam() {
        // 获取 耗费时间 考试分数 当前时间
        String time = String.format("%02d:%02d", timeConsum / 60, timeConsum % 60);
        int grade = 0;
        String currentDate = null;
        for (QuestionState questionState : questionStates) {
            if(questionState == QuestionState.CORRECT){
                grade++;
            }
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        currentDate = formatter.format(new Date());
        QuestionRecoder questionRecode = new QuestionRecoder(String.valueOf(grade), time, currentDate);
        QuestionRecoderOpenHelper.saveQuestionRecoder(App.getAppContext(), questionRecode);
        view.showExamResule(questionRecode);
    }

    @Override
    protected void start() {
        initTimer();
    }

    private void initTimer() {
        isRun = true;
        handler  = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                String time = (String) msg.obj;
                if(time == null){
                    view.forcedSubmit();
                }else{
                    view.updateTimer(time);
                }
            }
        };
        new Thread(new Runnable() {
            int min = 45;
            int sec = 0;
            @Override
            public void run() {
                while(isRun){
                    Message msg = handler.obtainMessage();
                    if(sec == 0){
                        if(min == 0){
                            isRun = false;
                        }else{
                            sec = 59;
                            min--;
                        }
                    }else{
                        sec--;
                    }
                    String time = String.format("%02d:%02d", min, sec);
                    msg.obj = time;
                    if(!isRun){
                        msg.obj = null;
                    }
                    msg.sendToTarget();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    timeConsum++;
                }
            }
        }).start();
    }
}
