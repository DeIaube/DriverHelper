package com.example.administrator.olddriverpromotionexam.ui.activity.choise_province;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.administrator.olddriverpromotionexam.R;
import com.example.administrator.olddriverpromotionexam.adapter.ProvinceAdapter;
import com.example.administrator.olddriverpromotionexam.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class ChoiseProvinceActivity extends BaseActivity implements ProvinceAdapter.OnClickListener {


    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.list_view)
    RecyclerView listView;

    private String province;
    private ProvinceAdapter adapter;
    private List<String> data;


    public static final String PROVINCE = "province";

    @Override
    protected int getLayout() {
        return R.layout.activity_choise_province;
    }

    @Override
    protected void init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        province = getIntent().getStringExtra(PROVINCE);
        data = getData();
        adapter = new ProvinceAdapter(this, data);
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(adapter);
        adapter.setOnClickListener(this);
    }

    public static void start(Activity activity, String province){
        Intent intent = new Intent(activity, ChoiseProvinceActivity.class);
        intent.putExtra(PROVINCE, province);
        activity.startActivityForResult(intent, 0);
    }

    public List<String> getData() {
        List<String> data = new ArrayList<>();
        data.add("安徽");
        data.add("澳门");
        data.add("北京");
        data.add("重庆");
        data.add("福建");
        data.add("广东");
        data.add("广西");
        data.add("贵州");
        data.add("甘肃");
        data.add("河北");
        data.add("黑龙江");
        data.add("河南");
        data.add("湖北");
        data.add("湖南");
        data.add("海南");
        data.add("吉林");
        data.add("江苏");
        data.add("江西");
        data.add("内蒙古");
        data.add("宁夏");
        data.add("青海");
        data.add("山西");
        data.add("上海");
        data.add("山东");
        data.add("四川");
        data.add("陕西");
        data.add("天津");
        data.add("台湾");
        data.add("西藏");
        data.add("新疆");
        data.add("云南");
        data.add("浙江");
        data.add("钓鱼岛");
        return data;
    }

    @Override
    public void click(View v, int position) {
        province = data.get(position);
        Intent intent = getIntent();
        intent.putExtra(PROVINCE, province);
        setResult(0, intent);
        finish();
    }
}
