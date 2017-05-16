package com.example.administrator.olddriverpromotionexam.ui.activity.modify_information;

import com.example.administrator.olddriverpromotionexam.bean.Event;
import com.example.administrator.olddriverpromotionexam.bean.EventCode;
import com.example.administrator.olddriverpromotionexam.util.EventBusUtil;
import com.example.administrator.olddriverpromotionexam.util.PatternUtil;
import com.example.administrator.olddriverpromotionexam.util.UserUtil;

/**
 * Created by Administrator on 2017/5/15 0015.
 */

public class ModifyMailPresenter extends ModifyInformationContract.Presenter {

    ModifyMailPresenter(ModifyInformationContract.View view) {
        super(view);
    }

    @Override
    void sunmit(String s1, String s2) {
        if(s1 == null || s1.isEmpty()){
            view.showMessage("邮箱不能为空");
            return;
        }
        if(!PatternUtil.checkEmail(s1)){
            view.showMessage("请输入正确的邮箱");
            return;
        }
        UserUtil.getUser().setMail(s1);
        view.showMessage("修改邮箱成功");
        EventBusUtil.postEvent(new Event(EventCode.J));
        view.finish();
    }


    @Override
    protected void start() {
        view.showModifyMail();
        view.setTitle("修改邮箱");
    }
}
