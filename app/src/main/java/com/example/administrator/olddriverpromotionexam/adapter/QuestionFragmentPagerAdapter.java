package com.example.administrator.olddriverpromotionexam.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.administrator.olddriverpromotionexam.bean.Question;
import com.example.administrator.olddriverpromotionexam.ui.fragment.QuestionFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/10 0010.
 */

public class QuestionFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Question> questionList;
    private List<Fragment> fragmentList;

    public QuestionFragmentPagerAdapter(FragmentManager fm, List<Question> questionList) {
        super(fm);
        this.questionList = questionList;
        this.fragmentList = new ArrayList<>();
        initFragmentList();
    }

    private void initFragmentList() {
        for (Question question : questionList) {
            Fragment fragment = QuestionFragment.newInstance(question);
            fragmentList.add(fragment);
        }
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return questionList.size();
    }


}
