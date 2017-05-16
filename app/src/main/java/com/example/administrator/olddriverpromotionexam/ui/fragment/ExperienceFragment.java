package com.example.administrator.olddriverpromotionexam.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.administrator.olddriverpromotionexam.R;
import com.example.administrator.olddriverpromotionexam.adapter.SimpleListViewAdapter;
import com.example.administrator.olddriverpromotionexam.base.BaseFragment;
import com.example.administrator.olddriverpromotionexam.bean.Experience;
import com.example.administrator.olddriverpromotionexam.ui.activity.experience_dentail.ExperienceDentailActivity;
import com.example.administrator.olddriverpromotionexam.util.ExperienceBank;

import java.util.List;

import butterknife.InjectView;

public class ExperienceFragment extends BaseFragment implements SimpleListViewAdapter.OnClockListener {


    @InjectView(R.id.list_view)
    RecyclerView listView;

    private SimpleListViewAdapter adapter;
    private List<Experience> experienceList;

    @Override
    protected void init() {
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        experienceList = ExperienceBank.getDefault().getAllExperience();
        adapter = new SimpleListViewAdapter(getContext(), experienceList);
        listView.setAdapter(adapter);
        adapter.setOnClockListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_experience;
    }

    @Override
    public void click(View v, int position) {
        ExperienceDentailActivity.start(getContext(), experienceList.get(position));
    }
}
