package com.example.administrator.olddriverpromotionexam.ui.activity.sign;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.olddriverpromotionexam.R;
import com.example.administrator.olddriverpromotionexam.base.BaseActivity;
import com.example.administrator.olddriverpromotionexam.ui.activity.login.LoginActivity;

import butterknife.InjectView;

public class RegisteActivity extends BaseActivity implements View.OnClickListener, RegisteContract.View {

    @InjectView(R.id.input_username)
    EditText inputUsername;
    @InjectView(R.id.input_password)
    EditText inputPassword;
    @InjectView(R.id.input_re_password)
    EditText inputRePassword;
    @InjectView(R.id.sign)
    AppCompatButton sign;
    @InjectView(R.id.link_login)
    TextView linkLogin;

    private RegisteContract.Presenter presenter;
    private ProgressDialog progressDialog;

    public static void start(Context context) {
        Intent intent = new Intent(context, RegisteActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_registe;
    }

    @Override
    protected void init() {
        sign.setOnClickListener(this);
        linkLogin.setOnClickListener(this);

        presenter = new RegistePresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign:
                sign();
                break;
            case R.id.link_login:
                linkLogin();
                break;
        }
    }

    private void linkLogin() {
        LoginActivity.start(this);
        finish();
    }

    private void sign() {
        String username = inputUsername.getText().toString();
        String password = inputPassword.getText().toString();
        String rePassword = inputRePassword.getText().toString();
        presenter.sign(this, username, password, rePassword);
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        if(progressDialog == null){
            progressDialog = new ProgressDialog(this,R.style.AppTheme);
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        if(progressDialog != null){
            progressDialog.hide();
        }
    }

    @Override
    public void registeSucceed() {
        finish();
    }
}
