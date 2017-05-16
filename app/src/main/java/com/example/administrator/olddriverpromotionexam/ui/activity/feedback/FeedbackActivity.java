package com.example.administrator.olddriverpromotionexam.ui.activity.feedback;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.olddriverpromotionexam.R;
import com.example.administrator.olddriverpromotionexam.base.BaseActivity;
import com.example.administrator.olddriverpromotionexam.util.Sp;

import java.util.List;

import butterknife.InjectView;

public class FeedbackActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.content_Input)
    EditText contentInput;
    @InjectView(R.id.send)
    AppCompatButton send;

    private static final String FEEDBACK = "feedback";

    @Override
    protected int getLayout() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        contentInput.setText(Sp.get(FEEDBACK, ""));
        send.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String content = contentInput.getText().toString();
        if(content.isEmpty()){
            Toast.makeText(this, R.string.feedback_can_not_be_empty, Toast.LENGTH_SHORT).show();
            return;
        }
        sendSMS(getString(R.string.feedback_address), content);
        Toast.makeText(this, R.string.feedback_send_succeed, Toast.LENGTH_SHORT).show();
        Sp.put(FEEDBACK, "");
    }

    @Override
    public void onBackPressed() {
        if(contentInput.getText().toString().isEmpty()){
            super.onBackPressed();
        }else{
            Sp.put(FEEDBACK, contentInput.getText().toString());
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle(R.string.prompt)
                    .setMessage(R.string.be_sure_to_exit)
                    .setNegativeButton(R.string.confirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setPositiveButton(R.string.cancel, null)
                    .create();
            dialog.show();

        }
    }


    public static void start(Context context) {
        Intent intent = new Intent(context, FeedbackActivity.class);
        context.startActivity(intent);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void sendSMS(String phoneNumber,String message){
        android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();
        List<String> divideContents = smsManager.divideMessage(message);
        for (String text : divideContents) {
            smsManager.sendTextMessage(phoneNumber, null, text, null, null);
        }
    }
}
