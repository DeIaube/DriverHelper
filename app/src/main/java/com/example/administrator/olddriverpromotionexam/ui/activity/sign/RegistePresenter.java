package com.example.administrator.olddriverpromotionexam.ui.activity.sign;

import android.content.Context;

import com.example.administrator.olddriverpromotionexam.util.UserUtil;

/**
 * Created by Administrator on 2017/5/12 0012.
 */

public class RegistePresenter extends RegisteContract.Presenter {

    RegistePresenter(RegisteContract.View view) {
        super(view);
    }

    @Override
    void sign(Context context, String username, String password, String rePassword) {
        view.showProgress();
        boolean isLegiti = testLegitimacy(context, username, password, rePassword);
        if(isLegiti){
            realRegiste(context, username, password);
            view.showMessage("注册成功");
            view.registeSucceed();
        }
        view.hideProgress();
    }

    private void realRegiste(Context context, String username, String password) {
        UserUtil.registe(context, username, password);
    }

    private boolean testLegitimacy(Context context, String username, String password, String rePassword) {
        if(username == null || username.isEmpty() || password == null || password.isEmpty() || rePassword == null || rePassword.isEmpty()){
            view.showMessage("用户名或者密码不能为空");
            return false;
        }
        if(!rePassword.equals(password)){
            view.showMessage("两次输入密码不唯一");
            return false;
        }
        if(UserUtil.hasUser(context, username)){
            view.showMessage("当前用户名已被注册");
            return false;
        }
        return true;

    }

    @Override
    protected void start() {

    }
}
