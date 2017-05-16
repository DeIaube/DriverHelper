package com.example.administrator.olddriverpromotionexam.ui.activity.home;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.administrator.olddriverpromotionexam.R;
import com.example.administrator.olddriverpromotionexam.base.BaseActivity;
import com.example.administrator.olddriverpromotionexam.bean.Event;
import com.example.administrator.olddriverpromotionexam.bean.EventCode;
import com.example.administrator.olddriverpromotionexam.bean.User;
import com.example.administrator.olddriverpromotionexam.ui.activity.feedback.FeedbackActivity;
import com.example.administrator.olddriverpromotionexam.ui.activity.setting.SettingActivity;
import com.example.administrator.olddriverpromotionexam.ui.fragment.ExperienceFragment;
import com.example.administrator.olddriverpromotionexam.ui.fragment.SubjectFragment;
import com.example.administrator.olddriverpromotionexam.util.QuestionBank;
import com.example.administrator.olddriverpromotionexam.util.UserUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.InjectView;

public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.drawer_layout)
    DrawerLayout drawer;
    @InjectView(R.id.nav_view)
    NavigationView navigationView;

    TextView mailTv;
    TextView usernameTv;

    private SubjectFragment subject1Fragment;
    private SubjectFragment subject4Fragment;
    private ExperienceFragment experienceFragment;
    private String subject1Tag;
    private String subject2Tag;
    private String subject4Tag;

    @Override
    protected int getLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected void init() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        subject1Fragment = new SubjectFragment();
        replaceFragment(R.id.content_home, subject1Fragment, subject1Tag);
        experienceFragment = new ExperienceFragment();
        subject4Fragment = new SubjectFragment();

        navigationView.setNavigationItemSelectedListener(this);
        mailTv = (TextView) navigationView.getHeaderView(0).findViewById(R.id.mail_tv);
        usernameTv = (TextView) navigationView.getHeaderView(0).findViewById(R.id.username_tv);

        subject1Tag = getResources().getString(R.string.subject_1);
        subject2Tag = getResources().getString(R.string.subject_2);
        subject4Tag = getResources().getString(R.string.subject_4);

//        UserHelper.debug(this);
        refreshUsername();
        refreshUserMail();
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_subject_1) {

            setTitle(R.string.subject_1);
            replaceFragment(R.id.content_home, subject1Fragment, subject1Tag);
        } else if (id == R.id.nav_subject_2) {

            setTitle(R.string.subject_2);
            replaceFragment(R.id.content_home, experienceFragment, subject2Tag);
        } else if (id == R.id.nav_subject_3) {

            setTitle(R.string.subject_3);
            replaceFragment(R.id.content_home, experienceFragment, subject2Tag);
        } else if (id == R.id.nav_subject_4) {
            replaceFragment(R.id.content_home, subject4Fragment, subject4Tag);
            setTitle(R.string.subject_4);

        } else if (id == R.id.nav_share) {
            Intent intent=new Intent(Intent.ACTION_SEND);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.share));
            intent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.share_reason));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(Intent.createChooser(intent, getTitle()));
        } else if (id == R.id.nav_setting) {
            SettingActivity.start(HomeActivity.this);
        } else if (id == R.id.nav_about) {
            new AlertDialog.Builder(this)
                    .setTitle(getResources().getString(R.string.about))
                    .setMessage(getResources().getString(R.string.about_message))
                    .setNegativeButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }else if(id == R.id.nav_feedback){
            FeedbackActivity.start(this);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setTitle(String content){
        getSupportActionBar().setTitle(content);
    }

    public void setTitle(int res){
        getSupportActionBar().setTitle(res);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        QuestionBank.getDefault().destory();
        UserUtil.destory(this);
    }

    private void refreshUsername() {
        User user = UserUtil.getUser();
        if(user != null){
            usernameTv.setText(user.getUsername());
        }
    }

    private void refreshUserMail(){
        User user = UserUtil.getUser();
        if(user != null){
            mailTv.setText(user.getMail());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveEvent(Event event){
        int code = event.getCode();
        switch (code){
            case EventCode.J:
                refreshUserMail();
                break;
        }
    }

    @Override
    protected boolean needToUseEventBus() {
        return true;
    }
}
