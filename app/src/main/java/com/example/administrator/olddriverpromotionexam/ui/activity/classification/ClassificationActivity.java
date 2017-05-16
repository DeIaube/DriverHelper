package com.example.administrator.olddriverpromotionexam.ui.activity.classification;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.administrator.olddriverpromotionexam.R;
import com.example.administrator.olddriverpromotionexam.adapter.ClassificationAdapter;
import com.example.administrator.olddriverpromotionexam.base.BaseActivity;
import com.example.administrator.olddriverpromotionexam.bean.Question;
import com.example.administrator.olddriverpromotionexam.config.Config;
import com.example.administrator.olddriverpromotionexam.ui.activity.show_questions.ShowQuestionsActivity;
import com.example.administrator.olddriverpromotionexam.util.QuestionBank;

import java.util.List;
import java.util.Map;

import butterknife.InjectView;

public class ClassificationActivity extends BaseActivity implements ClassificationAdapter.OnClickListener {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.list_view)
    RecyclerView listView;

    private Map<String, List<Question>> questionMap;
    private ClassificationAdapter adapter;
    private int currentType = Config.CLASSIFICATION_PRACTICE;

    private static final String TYPE = "type";

    @Override
    protected int getLayout() {
        return R.layout.activity_classification;
    }

    @Override
    protected void init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        currentType = getIntent().getIntExtra(TYPE, Config.CLASSIFICATION_PRACTICE);

        questionMap = getMap();
        listView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ClassificationAdapter(this, questionMap);
        adapter.setOnClickListener(this);
        listView.setAdapter(adapter);
    }

    public static void start(Context context, int type) {
        Intent intent = new Intent(context, ClassificationActivity.class);
        intent.putExtra(TYPE, type);
        context.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v, String key) {
        ShowQuestionsActivity.start(this, currentType, key);
        onBackPressed();
    }

    public Map<String,List<Question>> getMap() {
        if(currentType == Config.CLASSIFICATION_PRACTICE){
            return QuestionBank.getDefault().getQuestionsAndAnswerMap();
        }
        return QuestionBank.getDefault().getKnowledgePointMap();
    }
}
