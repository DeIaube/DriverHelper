package com.example.administrator.olddriverpromotionexam.ui.activity.recoder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.administrator.olddriverpromotionexam.R;
import com.example.administrator.olddriverpromotionexam.adapter.RecoderArrayAdapter;
import com.example.administrator.olddriverpromotionexam.base.BaseActivity;
import com.example.administrator.olddriverpromotionexam.bean.QuestionRecoder;
import com.example.administrator.olddriverpromotionexam.db.QuestionRecoderOpenHelper;

import java.util.List;

import butterknife.InjectView;

public class RecoderActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.list_view)
    RecyclerView listView;

    RecoderArrayAdapter adapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_recoder;
    }

    @Override
    protected void init() {
        List<QuestionRecoder> questionRecoderList = QuestionRecoderOpenHelper.getQuestionRecoderList(this);
        if(questionRecoderList.isEmpty()){
            Toast.makeText(this, getString(R.string.no_data), Toast.LENGTH_SHORT).show();
            finish();
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        listView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RecoderArrayAdapter(this, questionRecoderList);
        listView.setAdapter(adapter);
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


    public static void start(Context context) {
        Intent intent = new Intent(context, RecoderActivity.class);
        context.startActivity(intent);
    }

}
