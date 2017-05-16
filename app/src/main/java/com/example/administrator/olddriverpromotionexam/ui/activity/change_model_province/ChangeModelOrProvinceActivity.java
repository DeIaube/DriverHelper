package com.example.administrator.olddriverpromotionexam.ui.activity.change_model_province;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.olddriverpromotionexam.R;
import com.example.administrator.olddriverpromotionexam.base.BaseActivity;
import com.example.administrator.olddriverpromotionexam.bean.User;
import com.example.administrator.olddriverpromotionexam.ui.activity.choise_province.ChoiseProvinceActivity;
import com.example.administrator.olddriverpromotionexam.ui.activity.login.LoginActivity;
import com.example.administrator.olddriverpromotionexam.util.UserUtil;

import butterknife.InjectView;

public class ChangeModelOrProvinceActivity extends BaseActivity implements View.OnClickListener {


    @InjectView(R.id.province)
    TextView province;
    @InjectView(R.id.choise_province)
    RelativeLayout choiseProvince;
    @InjectView(R.id.car)
    TextView car;
    @InjectView(R.id.truck)
    TextView truck;
    @InjectView(R.id.bus)
    TextView bus;
    @InjectView(R.id.submit)
    AppCompatButton submit;

    private User user;
    private String sModel;
    private String sProvince;

    @Override
    protected int getLayout() {
        return R.layout.activity_change_model_or_province;
    }

    @Override
    protected void init() {
        user = UserUtil.getUser();
        sModel = user.getModels();
        sProvince = user.getProvince();
        province.setText(user.getProvince());
        refreshModel();

        submit.setOnClickListener(this);
        choiseProvince.setOnClickListener(this);
        car.setOnClickListener(this);
        truck.setOnClickListener(this);
        bus.setOnClickListener(this);
    }

    public static void start(Context context){
        User user = UserUtil.getUser();
        if(user == null){
            context.startActivity(new Intent(context, LoginActivity.class));
            Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
        }else{
            context.startActivity(new Intent(context, ChangeModelOrProvinceActivity.class));
        }
    }


    private void choiseTruck() {
        truck.setTextColor(getResources().getColor(R.color.colorAccent));
        bus.setTextColor(getResources().getColor(R.color.white));
        car.setTextColor(getResources().getColor(R.color.white));
        sModel = "货车";
    }

    private void choiseBus() {
        bus.setTextColor(getResources().getColor(R.color.colorAccent));
        car.setTextColor(getResources().getColor(R.color.white));
        truck.setTextColor(getResources().getColor(R.color.white));
        sModel = "客车";
    }

    private void choiseCar() {
        car.setTextColor(getResources().getColor(R.color.colorAccent));
        truck.setTextColor(getResources().getColor(R.color.white));
        bus.setTextColor(getResources().getColor(R.color.white));
        sModel = "小车";
    }

    private void choiseProvince() {
        ChoiseProvinceActivity.start(this, sProvince);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == 0 && requestCode == 0){
            sProvince = data.getStringExtra(ChoiseProvinceActivity.PROVINCE);
            province.setText(sProvince);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void refreshModel() {
        truck.setTextColor(getResources().getColor(R.color.white));
        bus.setTextColor(getResources().getColor(R.color.white));
        car.setTextColor(getResources().getColor(R.color.white));
        if(sModel.equals("小车")){
            car.setTextColor(getResources().getColor(R.color.colorAccent));
        }else if(sModel.equals("货车")){
            truck.setTextColor(getResources().getColor(R.color.colorAccent));
        }else{
            bus.setTextColor(getResources().getColor(R.color.colorAccent));
        }
    }

    private void submit() {
        user.setProvince(sProvince);
        user.setModels(sModel);

        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit:
                submit();
                break;
            case R.id.choise_province:
                choiseProvince();
                break;
            case R.id.car:
                choiseCar();
                break;
            case R.id.bus:
                choiseBus();
                break;
            case R.id.truck:
                choiseTruck();
                break;
        }
    }


}
