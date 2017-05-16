package com.example.administrator.olddriverpromotionexam.ui.activity.modify_information;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.olddriverpromotionexam.R;
import com.example.administrator.olddriverpromotionexam.base.BaseActivity;

import butterknife.InjectView;

public class ModifyInformationActivity extends BaseActivity implements ModifyInformationContract.View, View.OnClickListener {

    public static final String TYPE = "type";
    public static final int MAIL = 0;
    public static final int PASSWORD = 1;
    public static final int PHONE_NUMBER = 2;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.first_input)
    EditText firstInput;
    @InjectView(R.id.second_input)
    EditText secondInput;
    @InjectView(R.id.submit)
    AppCompatButton submit;
    @InjectView(R.id.first_input_layout)
    TextInputLayout firstInputLayout;
    @InjectView(R.id.second_input_layout)
    TextInputLayout secondInputLayout;

    private ModifyInformationContract.Presenter presenter;
    private int currentType;

    @Override
    protected int getLayout() {
        return R.layout.activity_modify_information;
    }

    @Override
    protected void init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        currentType = getIntent().getIntExtra(TYPE, PASSWORD);

        if (currentType == PHONE_NUMBER) {
            presenter = new ModifyPhoneNumberPresenter(this);
        } else if (currentType == MAIL) {
            presenter = new ModifyMailPresenter(this);
        } else {
            presenter = new ModifyPasswordPresenter(this);
        }

        submit.setOnClickListener(this);
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

    public static void start(Context context, int type) {
        Intent intent = new Intent(context, ModifyInformationActivity.class);
        intent.putExtra(TYPE, type);
        context.startActivity(intent);
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void setTitle(String msg) {
        getSupportActionBar().setTitle(msg);
    }

    @Override
    public void showModifyPassword() {
        firstInputLayout.setHint("输入原始密码");
        secondInputLayout.setHint("输入新密码");
    }

    @Override
    public void showModifyMail() {
        firstInput.setHint("请输入邮箱");
        firstInput.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        secondInputLayout.setVisibility(View.GONE);
    }

    @Override
    public void showModifyPhoneNumber() {
        firstInput.setHint("请输入手机号");
        firstInput.setInputType(InputType.TYPE_CLASS_PHONE);
        secondInputLayout.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                submit();
                break;
        }
    }

    private void submit() {
        String s1 = firstInput.getText().toString();
        String s2 = secondInput.getText().toString();
        presenter.sunmit(s1, s2);
    }

}
