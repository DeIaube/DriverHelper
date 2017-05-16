package com.example.administrator.olddriverpromotionexam.ui.activity.modify_information;

import com.example.administrator.olddriverpromotionexam.util.PatternUtil;
import com.example.administrator.olddriverpromotionexam.util.UserUtil;

/**
 * Created by Administrator on 2017/5/15 0015.
 */

public class ModifyPhoneNumberPresenter extends ModifyInformationContract.Presenter {

    ModifyPhoneNumberPresenter(ModifyInformationContract.View view) {
        super(view);
    }

    @Override
    void sunmit(String s1, String s2) {
        if(s1 == null || s1.isEmpty()){
            view.showMessage("手机号码不能为空");
            return;
        }
        if(!PatternUtil.checkPhoneNumber(s1)){
            view.showMessage("请输入正确的手机号码");
            return;
        }
        UserUtil.getUser().setPhone(s1);
        view.showMessage("修改手机号码成功");
        view.finish();
    }


    @Override
    protected void start() {
        view.showModifyPhoneNumber();
        view.setTitle("修改手机号码");
    }
}
