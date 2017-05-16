package com.example.administrator.olddriverpromotionexam.ui.activity.show_questions;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.olddriverpromotionexam.R;
import com.example.administrator.olddriverpromotionexam.adapter.QuestionFragmentPagerAdapter;
import com.example.administrator.olddriverpromotionexam.base.BaseActivity;
import com.example.administrator.olddriverpromotionexam.bean.Event;
import com.example.administrator.olddriverpromotionexam.bean.EventCode;
import com.example.administrator.olddriverpromotionexam.bean.Question;
import com.example.administrator.olddriverpromotionexam.config.Config;
import com.example.administrator.olddriverpromotionexam.util.CollectionUtil;
import com.example.administrator.olddriverpromotionexam.util.EventBusUtil;
import com.example.administrator.olddriverpromotionexam.util.Sp;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.InjectView;

public class ShowQuestionsActivity extends BaseActivity implements ShowQuestionsContract.View, View.OnClickListener, ViewPager.OnPageChangeListener {


    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    public static final String TYPE = "type";
    public static final String KEY = "key";

    @InjectView(R.id.view_pager)
    ViewPager viewPager;
    @InjectView(R.id.collection_img)
    ImageView collectionImg;
    @InjectView(R.id.collection_tv)
    TextView collectionTv;
    @InjectView(R.id.collection)
    LinearLayout collection;
    @InjectView(R.id.chage_model)
    LinearLayout chageModel;
    @InjectView(R.id.correct_number)
    TextView correctNumber;
    @InjectView(R.id.error_number)
    TextView errorNumber;
    @InjectView(R.id.current_index)
    TextView currentIndex;
    @InjectView(R.id.max_index)
    TextView maxIndex;

    private ShowQuestionsContract.Presenter presenter;
    private int type;
    private String key;
    private List<Question> questionList;
    private QuestionFragmentPagerAdapter adapter;
    private int index;

    private int correct = 0;
    private int error = 0;

    @Override
    protected int getLayout() {
        return R.layout.activity_show_questions;
    }

    @Override
    protected void init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        presenter = new ShowQuestionsPresenter(this);
        type = getIntent().getIntExtra(TYPE, Config.RANDOM_PRACTICE);
        key = getIntent().getStringExtra(KEY);
        presenter.init(type, key);



        collection.setOnClickListener(this);
        chageModel.setOnClickListener(this);
        viewPager.setOnPageChangeListener(this);
        viewPager.setOffscreenPageLimit(Config.getMaxExercisesQuestions());
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

    public static void start(Context context, int type, String key) {
        Intent intent = new Intent(context, ShowQuestionsActivity.class);
        intent.putExtra(TYPE, type);
        switch (type) {
            case Config.CLASSIFICATION_PRACTICE:
            case Config.KNOWLEDGE_POINT_PRACTICE:
                intent.putExtra(KEY, key);
                break;
        }
        context.startActivity(intent);
    }




    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showQuestions(List<Question> data) {
        showQuestions(data, 0);
    }

    @Override
    public void showQuestions(List<Question> data, int index) {
        this.questionList = data;
        this.index = index;
        adapter = new QuestionFragmentPagerAdapter(getSupportFragmentManager(), data);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(index);
        changeUiByIndex();
    }

    private void changeUiByIndex() {
        currentIndex.setText(String.valueOf(index+1));
        maxIndex.setText(String.valueOf(questionList.size()));
        if(CollectionUtil.isCollection(questionList.get(index).getId())){
            setCollectionTrue();
        }else{
            setCollectionfalse();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiverEvent(Event event){
        int code = event.getCode();
        switch (code){
            case EventCode.B:
                int res = (int) event.getData();
                this.setTitle(res);
                break;
            case EventCode.C:
                errorNumberIncrease();
                break;
            case EventCode.D:
                correctNumberIncrease();
                break;
            case EventCode.E:
                nextQuestion();
                break;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.chage_model:
                changeModel();
                break;
            case R.id.collection:
                collection();
                break;
        }
    }

    /**
     * 点击收藏
     */
    private void collection() {
        String id = questionList.get(index).getId();
        if(CollectionUtil.isCollection(id)){
            setCollectionfalse();
        }else{
            setCollectionTrue();
        }
        CollectionUtil.changeCollection(id);
    }

    /**
     * 未收藏状态
     */
    private void setCollectionfalse() {
        collectionImg.setImageResource(R.drawable.collection_off);
        collectionTv.setText(getString(R.string.collection));
    }


    /**
     * 已收藏状态
     */
    private void setCollectionTrue() {
        collectionImg.setImageResource(R.drawable.collection_on);
        collectionTv.setText(getString(R.string.already_collection));
    }

    /**
     * 点击改变模式
     */
    private void changeModel() {
        Event event = new Event(EventCode.A);
        EventBusUtil.postEvent(event);
    }

    private void nextQuestion(){
        index = index >= questionList.size()-1 ? questionList.size()-1 : index+1;
        viewPager.setCurrentItem(index);
    }

    private void setTitle(String title){
        getSupportActionBar().setTitle(title);
    }

    private void correctNumberIncrease(){
        correct++;
        correctNumber.setText(String.valueOf(correct));
    }

    private void errorNumberIncrease(){
        error++;
        errorNumber.setText(String.valueOf(error));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        this.index = position;
        changeUiByIndex();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected boolean needToUseEventBus() {
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Sp.put(Config.ORDER_PRICATICE_INDEX, index);
    }
}
