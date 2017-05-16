package com.example.administrator.olddriverpromotionexam.ui.activity.exam_choise;

import android.content.Context;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.os.CancellationSignal;

import com.example.administrator.olddriverpromotionexam.bean.Question;
import com.example.administrator.olddriverpromotionexam.config.Config;
import com.example.administrator.olddriverpromotionexam.ui.activity.exam.ExamActivity;
import com.example.administrator.olddriverpromotionexam.ui.activity.show_questions.ShowQuestionsActivity;
import com.example.administrator.olddriverpromotionexam.util.QuestionBank;
import com.example.administrator.olddriverpromotionexam.util.Sp;

import java.util.List;

/**
 * Created by Administrator on 2017/5/14 0014.
 */

public class ExamChoisePresenter extends ExamChoiseContract.Presenter {

    private CancellationSignal cancellationSignal;

    ExamChoisePresenter(ExamChoiseContract.View view) {
        super(view);
    }

    /**
     * 查看是否需要验证指纹
     * @param context
     */
    @Override
    protected void startRealExam(Context context) {
        if(!Sp.get(Config.FINGERPRINT, false)){
            fingerprintVerification(context);
            return;
        }
        view.showFingerprintDialog();
        FingerprintManagerCompat manager = FingerprintManagerCompat.from(context);
        if (cancellationSignal == null){
            cancellationSignal = new CancellationSignal();
        }
        manager.authenticate(null, 0, cancellationSignal,  new FingerCallBack(context), null);
    }

    private void fingerprintVerification(Context context) {
        List<Question> questionList = QuestionBank.getDefault().getAllRabdomQuestion(Config.getMaxExamQuestions());
        ExamActivity.start(context, questionList);
        view.finish();
    }

    @Override
    protected void startMockExam(Context context) {
        ShowQuestionsActivity.start(context, Config.RANDOM_PRACTICE,null);
        view.finish();
    }

    @Override
    protected void cancelStartRealExam() {
        cancellationSignal.cancel();
    }

    @Override
    protected void start() {

    }



    public class FingerCallBack extends FingerprintManagerCompat.AuthenticationCallback{
        private Context context;

        public FingerCallBack(Context context) {
            this.context = context;
        }

        @Override
        public void onAuthenticationFailed() {
            //指纹登陆失败
            view.showMsg("指纹验证失败,请重试");
        }

        @Override
        public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
            //指纹登陆成功
            view.showMsg("验证通过,开始考试");
            view.hideFingerprintDialog();
            fingerprintVerification(context);
        }

        @Override
        public void onAuthenticationError(int errMsgId, CharSequence errString) {
            //错误次数太多
            view.showMsg("请等待30秒后重新验证指纹");
            view.hideFingerprintDialog();
        }
    }
}
