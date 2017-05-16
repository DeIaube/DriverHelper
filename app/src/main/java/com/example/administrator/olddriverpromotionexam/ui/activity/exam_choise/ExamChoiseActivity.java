package com.example.administrator.olddriverpromotionexam.ui.activity.exam_choise;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.olddriverpromotionexam.R;
import com.example.administrator.olddriverpromotionexam.base.BaseActivity;
import com.example.administrator.olddriverpromotionexam.bean.User;
import com.example.administrator.olddriverpromotionexam.util.UserUtil;

import butterknife.InjectView;

public class ExamChoiseActivity extends BaseActivity implements View.OnClickListener, ExamChoiseContract.View {

    @InjectView(R.id.province)
    TextView province;
    @InjectView(R.id.car_model)
    TextView carModel;
    @InjectView(R.id.real_exam)
    AppCompatButton realExam;
    @InjectView(R.id.mock_exam)
    AppCompatButton mockExam;

    private ExamChoiseContract.Presenter presenter;
    private AlertDialog fingerprintDialog;

    @Override
    protected int getLayout() {
        return R.layout.activity_exam_choise;
    }

    @Override
    protected void init() {
        //Todo 关联用户的省份与车型
        User user = UserUtil.getUser();
        if(user != null){
            province.setText(user.getProvince());
            carModel.setText(user.getModels());
        }
        realExam.setOnClickListener(this);
        mockExam.setOnClickListener(this);
        presenter = new ExamChoisePresenter(this);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, ExamChoiseActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.real_exam:
                realExam();
                break;
            case R.id.mock_exam:
                mockExam();
                break;
        }
    }

    private void mockExam() {
        presenter.startMockExam(this);
    }

    /**
     * 进入真正的考试之前要进行指纹验证
     */
    private void realExam() {
        presenter.startRealExam(this);
    }


    @Override
    public void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showFingerprintDialog() {
        if(fingerprintDialog == null){
            fingerprintDialog = new AlertDialog.Builder(this)
                    .setTitle("请进行指纹验证")
                    .setView(R.layout.item_finger)
                    .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            presenter.cancelStartRealExam();
                            dialog.dismiss();
                        }
                    })
                    .create();
        }
        fingerprintDialog.show();
    }

    @Override
    public void hideFingerprintDialog() {
        if(fingerprintDialog != null){
            fingerprintDialog.hide();
        }
    }
}
