package com.example.administrator.olddriverpromotionexam.ui.activity.login;

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
import com.example.administrator.olddriverpromotionexam.ui.activity.sign.RegisteActivity;

import butterknife.InjectView;

public class LoginActivity extends BaseActivity implements LoginContract.View, View.OnClickListener {

    @InjectView(R.id.input_username)
    EditText inputUsername;
    @InjectView(R.id.input_password)
    EditText inputPassword;
    @InjectView(R.id.login)
    AppCompatButton login;
    @InjectView(R.id.link_sign)
    TextView linkSign;

    private LoginContract.Presenter presenter;
    private ProgressDialog progressDialog;

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
        login.setOnClickListener(this);
        linkSign.setOnClickListener(this);
        presenter = new LoginPresenter(this);
    }

    public static void start(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        if(progressDialog == null){
            progressDialog = new ProgressDialog(this, R.style.AppTheme);
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
    public void loginSucceed() {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.link_sign:
                linkSign();
                break;
            case R.id.login:
                login();
                break;
        }
    }

    private void login() {
        String username = inputUsername.getText().toString();
        String password = inputPassword.getText().toString();
        presenter.login(this, username, password);
    }

    private void linkSign() {
        RegisteActivity.start(this);
        finish();
    }
}
