package com.example.administrator.olddriverpromotionexam.ui.activity.setting;

import android.content.Context;

import com.example.administrator.olddriverpromotionexam.bean.User;
import com.example.administrator.olddriverpromotionexam.config.Config;
import com.example.administrator.olddriverpromotionexam.util.Sp;
import com.example.administrator.olddriverpromotionexam.util.UserUtil;

/**
 * Created by Administrator on 2017/5/14 0014.
 */

public class SettingPresenter extends SettingContract.Presenter {

    SettingPresenter(SettingContract.View view) {
        super(view);
    }

    @Override
    void fingerprintCheckedChanged(boolean check) {
        Sp.put(Config.FINGERPRINT, check);
    }

    @Override
    void faceCheckedChanged(boolean check) {
        Sp.put(Config.FACE, check);
    }

    @Override
    void signOut(Context context) {
        User user = UserUtil.getUser();
        if(user == null){
            view.showMessage("抱歉,请先登录");
        }else{
            UserUtil.signOut(context);
            view.showMessage("账户已登出");
        }
    }

    @Override
    protected void start() {
        view.setFingerprintChecked(Sp.get(Config.FINGERPRINT, false));
        view.setFaceChecked(Sp.get(Config.FACE, false));
    }
}
