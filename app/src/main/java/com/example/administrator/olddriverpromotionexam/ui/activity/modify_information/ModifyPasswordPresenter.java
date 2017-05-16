package com.example.administrator.olddriverpromotionexam.ui.activity.modify_information;

import com.example.administrator.olddriverpromotionexam.util.UserUtil;

/**
 * Created by Administrator on 2017/5/15 0015.
 */

public class ModifyPasswordPresenter extends ModifyInformationContract.Presenter {

    ModifyPasswordPresenter(ModifyInformationContract.View view) {
        super(view);
    }

    @Override
    void sunmit(String s1, String s2) {
        if(s1 == null || s1.isEmpty()){
            view.showMessage("原始密码不能为空");
            return;
        }
        if(s2 == null || s2.isEmpty()){
            view.showMessage("新密码不能为空");
            return;
        }
        if(s1.equals(s2)){
            view.showMessage("新密码不能与原始密码相同");
            return;
        }
        if(!s1.equals(UserUtil.getUser().getPassword())){
            view.showMessage("原始密码输入错误,请重试");
            return;
        }
        UserUtil.getUser().setPassword(s2);
        view.showMessage("修改密码成功");
        view.finish();
    }


    @Override
    protected void start() {
        view.showModifyPassword();
        view.setTitle("修改密码");
    }
}
