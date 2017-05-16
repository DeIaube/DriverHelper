package com.example.administrator.olddriverpromotionexam.ui.activity.exam_result;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.olddriverpromotionexam.R;
import com.example.administrator.olddriverpromotionexam.base.BaseActivity;
import com.example.administrator.olddriverpromotionexam.bean.QuestionRecoder;
import com.example.administrator.olddriverpromotionexam.config.Config;
import com.example.administrator.olddriverpromotionexam.ui.activity.recoder.RecoderActivity;
import com.example.administrator.olddriverpromotionexam.ui.activity.show_questions.ShowQuestionsActivity;

import butterknife.InjectView;

public class ExamResultActivity extends BaseActivity implements View.OnClickListener {


    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    private static final String QUESTION_CODER = "question_recoder";
    @InjectView(R.id.timeConsum)
    TextView timeConsum;
    @InjectView(R.id.nick_name)
    TextView nickName;
    @InjectView(R.id.grade)
    TextView grade;
    @InjectView(R.id.exam_resule)
    TextView examResule;
    @InjectView(R.id.show_recoder)
    LinearLayout showRecoder;
    @InjectView(R.id.order_practice)
    LinearLayout orderPractice;
    @InjectView(R.id.rest)
    LinearLayout rest;

    private QuestionRecoder recoder;

    @Override
    protected int getLayout() {
        return R.layout.activity_exam_result;
    }

    @Override
    protected void init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        recoder = getIntent().getParcelableExtra(QUESTION_CODER);
        initResult();
        showRecoder.setOnClickListener(this);
        orderPractice.setOnClickListener(this);
        rest.setOnClickListener(this);
    }

    private void initResult() {
        timeConsum.setText(recoder.getTimeConsum());
        grade.setText(recoder.getGrade());
        if(Integer.valueOf(recoder.getGrade()) >= 90){
            nickName.setText("秋叶原の车神");
            examResule.setText("及格");
        }else{
            nickName.setText("马路杀手");
            examResule.setText("不及格");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void start(Context context, QuestionRecoder recoder) {
        Intent intent = new Intent(context, ExamResultActivity.class);
        intent.putExtra(QUESTION_CODER, recoder);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.order_practice:
                orderPractice();
                break;
            case R.id.show_recoder:
                showRecoder();
                break;
            case R.id.rest:
                rest();
                break;
        }
        finish();
    }

    private void orderPractice() {
        ShowQuestionsActivity.start(this, Config.ORDER_PRACTICE,null);
    }

    private void showRecoder() {
        RecoderActivity.start(this);
    }

    private void rest() {

    }
}
