package com.example.administrator.olddriverpromotionexam.ui.activity.experience_dentail;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.administrator.olddriverpromotionexam.R;
import com.example.administrator.olddriverpromotionexam.base.BaseActivity;
import com.example.administrator.olddriverpromotionexam.bean.Experience;

import butterknife.InjectView;

public class ExperienceDentailActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.tv)
    TextView tv;


    private Experience experience;
    private static final String EXPERIENCE = "experience";


    @Override
    protected int getLayout() {
        return R.layout.activity_experience_dentail;
    }

    @Override
    protected void init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        experience = getIntent().getParcelableExtra(EXPERIENCE);
        getSupportActionBar().setTitle(experience.getTitle());
        tv.setText(experience.getContent());
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

    public static void start(Context context, Experience experience){
        Intent intent = new Intent(context, ExperienceDentailActivity.class);
        intent.putExtra(EXPERIENCE, experience);
        context.startActivity(intent);
    }

}
