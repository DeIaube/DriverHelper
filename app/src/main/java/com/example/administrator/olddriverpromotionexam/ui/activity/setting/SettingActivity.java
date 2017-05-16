package com.example.administrator.olddriverpromotionexam.ui.activity.setting;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.olddriverpromotionexam.R;
import com.example.administrator.olddriverpromotionexam.base.BaseActivity;
import com.example.administrator.olddriverpromotionexam.ui.activity.change_model_province.ChangeModelOrProvinceActivity;
import com.example.administrator.olddriverpromotionexam.ui.activity.choise_question_number.ChoiseQuestionNumberActivity;
import com.example.administrator.olddriverpromotionexam.ui.activity.modify_information.ModifyInformationActivity;

import butterknife.InjectView;

public class SettingActivity extends BaseActivity implements SettingContract.View, View.OnClickListener {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.change_phone_number)
    LinearLayout changePhoneNumber;
    @InjectView(R.id.change_mail)
    LinearLayout changeMail;
    @InjectView(R.id.change_password)
    LinearLayout changePassword;
    @InjectView(R.id.change_car_model)
    LinearLayout changeCarModel;
    @InjectView(R.id.change_province)
    LinearLayout changeProvince;
    @InjectView(R.id.choise_question_number)
    LinearLayout choiseQuetionNumber;
    @InjectView(R.id.fingerprint_verification)
    Switch fingerprintVerification;
    @InjectView(R.id.face_verification)
    Switch faceVerification;
    @InjectView(R.id.sign_out)
    TextView signOut;

    private SettingContract.Presenter presenter;

    @Override
    protected int getLayout() {
        return R.layout.activity_setting;
    }

    @Override
    protected void init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        changeCarModel.setOnClickListener(this);
        changeProvince.setOnClickListener(this);
        changeMail.setOnClickListener(this);
        changePhoneNumber.setOnClickListener(this);
        changePassword.setOnClickListener(this);
        signOut.setOnClickListener(this);
        choiseQuetionNumber.setOnClickListener(this);

        presenter = new SettingPresenter(this);
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

    public static void start(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void setFingerprintChecked(boolean check) {
        fingerprintVerification.setChecked(check);
    }

    @Override
    public void setFaceChecked(boolean check) {
        faceVerification.setChecked(check);
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.faceCheckedChanged(faceVerification.isChecked());
        presenter.fingerprintCheckedChanged(fingerprintVerification.isChecked());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.change_car_model:
            case R.id.change_province:
                jumpChangeModelOrProvince();
                break;
            case R.id.sign_out:
                signOut();
                break;
            case R.id.change_password:
                changePassword();
                break;
            case R.id.change_phone_number:
                changePhoneNumber();
                break;
            case R.id.change_mail:
                changeMail();
                break;
            case R.id.choise_question_number:
                choiseQuetionNumber();
                break;
        }
    }

    private void choiseQuetionNumber() {
        ChoiseQuestionNumberActivity.start(this);
    }

    private void changeMail() {
        ModifyInformationActivity.start(this, ModifyInformationActivity.MAIL);
    }

    private void changePhoneNumber() {
        ModifyInformationActivity.start(this, ModifyInformationActivity.PHONE_NUMBER);
    }

    private void changePassword() {
        ModifyInformationActivity.start(this, ModifyInformationActivity.PASSWORD);
    }

    private void signOut() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.prompt))
                .setMessage("确定要登出么?")
                .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.signOut(SettingActivity.this);
                    }
                })
                .setNegativeButton(getString(R.string.cancel), null)
                .show();
    }

    private void jumpChangeModelOrProvince() {
        ChangeModelOrProvinceActivity.start(this);
    }
}
