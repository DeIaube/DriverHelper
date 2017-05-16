package com.example.administrator.olddriverpromotionexam.ui.activity.login;

import android.content.Context;

import com.example.administrator.olddriverpromotionexam.util.UserUtil;

/**
 * Created by Administrator on 2017/5/12 0012.
 */

public class LoginPresenter extends LoginContract.Presenter {

    LoginPresenter(LoginContract.View view) {
        super(view);
    }

    @Override
    void login(Context context, String username, String password) {
        boolean isLegit = testLegitimacy(username, password);
        if(isLegit){
            realLogin(context, username, password);
        }
    }

    private void realLogin(Context context, String username, String password) {
        view.showProgress();
        boolean isLogin = UserUtil.login(context, username, password);
        view.hideProgress();
        if(isLogin){
            view.loginSucceed();
            view.showMessage("登陆成功");
        }else{
            view.showMessage("用户名或密码错误,请重试");
        }
    }

    private boolean testLegitimacy(String username, String password) {
        if(username == null || username.isEmpty() || password == null || password.isEmpty()){
            view.showMessage("用户名或者密码不能为空");
            return false;
        }
        return true;
    }

    @Override
    protected void start() {

    }
}
