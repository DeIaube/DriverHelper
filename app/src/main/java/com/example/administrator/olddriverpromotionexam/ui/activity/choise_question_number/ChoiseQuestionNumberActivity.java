package com.example.administrator.olddriverpromotionexam.ui.activity.choise_question_number;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.administrator.olddriverpromotionexam.R;
import com.example.administrator.olddriverpromotionexam.adapter.ChoiseQuestionNumberAdapter;
import com.example.administrator.olddriverpromotionexam.base.BaseActivity;
import com.example.administrator.olddriverpromotionexam.config.Config;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class ChoiseQuestionNumberActivity extends BaseActivity implements ChoiseQuestionNumberAdapter.OnClickListener {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.list_view)
    RecyclerView listView;

    private ChoiseQuestionNumberAdapter adapter;
    private List<Integer> data;

    @Override
    protected int getLayout() {
        return R.layout.activity_choise_question_number;
    }

    @Override
    protected void init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        listView.setLayoutManager(new LinearLayoutManager(this));
        data = getData();
        adapter = new ChoiseQuestionNumberAdapter(this, data, Config.getMaxExercisesQuestions());
        listView.setAdapter(adapter);
        adapter.setOnClickListener(this);
    }

    public static void start(Context context){
        Intent intent = new Intent(context, ChoiseQuestionNumberActivity.class);
        context.startActivity(intent);
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


    public List<Integer> getData() {
        List<Integer> data = new ArrayList<>();
        for(int i = 5; i <= 25; i+=1){
            data.add(i);
        }
        return data;
    }

    @Override
    public void click(View v, int position) {
        Config.setMaxExercisesQuestions(data.get(position));
        finish();
    }
}
