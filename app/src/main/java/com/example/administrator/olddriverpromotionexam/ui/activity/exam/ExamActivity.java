package com.example.administrator.olddriverpromotionexam.ui.activity.exam;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.media.ImageReader;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.olddriverpromotionexam.R;
import com.example.administrator.olddriverpromotionexam.adapter.BottomQuestionAdapter;
import com.example.administrator.olddriverpromotionexam.adapter.QuestionFragmentPagerAdapter;
import com.example.administrator.olddriverpromotionexam.base.BaseActivity;
import com.example.administrator.olddriverpromotionexam.bean.Event;
import com.example.administrator.olddriverpromotionexam.bean.EventCode;
import com.example.administrator.olddriverpromotionexam.bean.Question;
import com.example.administrator.olddriverpromotionexam.bean.QuestionRecoder;
import com.example.administrator.olddriverpromotionexam.bean.QuestionState;
import com.example.administrator.olddriverpromotionexam.config.Config;
import com.example.administrator.olddriverpromotionexam.ui.activity.exam_result.ExamResultActivity;
import com.example.administrator.olddriverpromotionexam.util.Sp;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.InjectView;

public class ExamActivity extends BaseActivity implements ExamContract.View, ViewPager.OnPageChangeListener, BottomQuestionAdapter.OnClickListener {

    @InjectView(R.id.timer)
    TextView timer;
    @InjectView(R.id.view_pager)
    ViewPager viewPager;
    @InjectView(R.id.submit)
    LinearLayout submit;
    @InjectView(R.id.correct_number)
    TextView correctNumber;
    @InjectView(R.id.error_number)
    TextView errorNumber;
    @InjectView(R.id.current_index)
    TextView currentIndex;
    @InjectView(R.id.bottom_layout)
    CardView bottomLayout;
    @InjectView(R.id.bottom_question_list)
    RecyclerView bottomQuestionList;
    @InjectView(R.id.surface_view)
    SurfaceView mSurfaceView;

    private ExamContract.Presenter presenter;
    private List<Question> questionList;
    private QuestionFragmentPagerAdapter adapter;
    private int index = 0;
    private BottomQuestionAdapter bottomQuestionAdapter;
    private BottomSheetBehavior behavior;
    private ProgressDialog progressDialog;




    //以下是摄像头相关
    private SurfaceHolder mSurfaceHolder;
    private CameraManager mCameraManager;//摄像头管理器
    private Handler childHandler, mainHandler;
    private String mCameraID = "1";//摄像头Id 0 为后  1 为前
    private ImageReader mImageReader;
    private CameraCaptureSession mCameraCaptureSession;
    private CameraDevice mCameraDevice;
    //以上是摄像头相关

    private static final String QUESTION_LIST = "questionList";


    @Override
    protected int getLayout() {
        return R.layout.activity_exam;
    }

    @Override
    protected void init() {
        presenter = new ExamPresenter(this);
        questionList = getIntent().getParcelableArrayListExtra(QUESTION_LIST);
        adapter = new QuestionFragmentPagerAdapter(getSupportFragmentManager(), questionList);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(this);
        initBottomSheet();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryToSubmit();
            }
        });
        initCameraView();
    }

    private void initBottomSheet() {
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.cl);
        View bottomSheet = coordinatorLayout.findViewById(R.id.bottom_sheet);
        behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if(newState == 1){
                    presenter.doUpdateBottomSheet();
                }
            }
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
        bottomQuestionList.setLayoutManager(new GridLayoutManager(this, 6));
        bottomQuestionAdapter = new BottomQuestionAdapter(this, new QuestionState[0]);
        bottomQuestionList.setAdapter(bottomQuestionAdapter);
        bottomQuestionAdapter.setOnClickListener(this);
    }

    public static void start(Context context, List<Question> questionList){
        Intent intent = new Intent(context, ExamActivity.class);
        intent.putParcelableArrayListExtra(QUESTION_LIST, (ArrayList<? extends Parcelable>) questionList);
        context.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        queryToSubmit();
    }

    /**
     * 询问是否交卷
     */
    private void queryToSubmit() {
        presenter.queryToSubmit();
    }

    @Override
    public void updateTimer(String time) {
        timer.setText(time);
    }

    /**
     * 时间到了 强制交卷
     */
    @Override
    public void forcedSubmit() {
        presenter.forcedToSubmit();
    }

    @Override
    public void updateErrorNumber(String number) {
        errorNumber.setText(number);
    }

    @Override
    public void updateCorrectNumber(String number) {
        correctNumber.setText(number);
    }

    @Override
    public void updateBottomSheet(QuestionState[] data) {
        bottomQuestionAdapter.update(index, data);
    }

    @Override
    public void hideBottomSheet() {
        if(behavior != null){
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

    @Override
    public void jumpPagerIndex(int index) {
        this.index = index;
        viewPager.setCurrentItem(index);
    }

    @Override
    public void showQuerySubmitMessage(String msg) {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.tips))
                .setMessage(msg)
                .setNegativeButton(getString(R.string.cancel), null)
                .setPositiveButton(getString(R.string.submit), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.submitExam();
                    }
                })
                .show();
    }

    @Override
    public void showForcedSubmitMessage(String msg) {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.tips))
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.submit), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.submitExam();
                    }
                })
                .show();
    }

    @Override
    public void showExamResule(QuestionRecoder recoder) {
        ExamResultActivity.start(this, recoder);
        finish();
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiverEvent(Event event){
        int code = event.getCode();
        switch (code){
            case EventCode.C:
                presenter.errorIncrease(index);
                break;
            case EventCode.D:
                presenter.correctIncrease(index);
                break;
            case EventCode.E:
                nextQuestion();
                break;
        }
    }


    private void nextQuestion() {
        index = index >= questionList.size()-1 ? questionList.size()-1 : index+1;
        viewPager.setCurrentItem(index);
    }

    @Override
    protected boolean needToUseEventBus() {
        return true;
    }




    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        this.index = position;
        changeUiByIndex();
    }

    private void changeUiByIndex() {
        currentIndex.setText(String.valueOf(index+1));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destory();
    }

    /**
     * BottomSheetClickListener
     * @param view
     * @param position
     */
    @Override
    public void click(View view, int position) {
        presenter.alterIndex(position);
    }


    private void showProgress() {
        if(progressDialog == null){
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle(getString(R.string.prompt));
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
    }

    private void hideProgress() {
        if(progressDialog != null){
            progressDialog.dismiss();
        }
    }








    //以下是摄像头相关
    /**
     * 初始化
     */
    private void initCameraView() {
        Log.i("fuck", String.valueOf(Sp.get(Config.FACE, false)));
        if(!Sp.get(Config.FACE, false)){
            mSurfaceView.setVisibility(View.GONE);
            return;
        }
        //mSurfaceView
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.setKeepScreenOn(true);
        // mSurfaceView添加回调
        mSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) { //SurfaceView创建
                // 初始化Camera
                initCamera2();
            }






            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) { //SurfaceView销毁
                // 释放Camera资源
                if (null != mCameraDevice) {
                    mCameraDevice.close();
                    ExamActivity.this.mCameraDevice = null;
                }
            }
        });
    }

    /**
     * 初始化Camera2
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initCamera2() {
        HandlerThread handlerThread = new HandlerThread("Camera2");
        handlerThread.start();
        childHandler = new Handler(handlerThread.getLooper());
        mainHandler = new Handler(getMainLooper());
        mCameraID = "" + CameraCharacteristics.LENS_FACING_BACK;//后摄像头
        mImageReader = ImageReader.newInstance(1080, 1920, ImageFormat.JPEG,1);
        //获取摄像头管理
        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            //打开摄像头
            mCameraManager.openCamera(mCameraID, stateCallback, mainHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }



    /**
     * 摄像头创建监听
     */
    private CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(CameraDevice camera) {//打开摄像头
            mCameraDevice = camera;
            //开启预览
            takePreview();
        }

        @Override
        public void onDisconnected(CameraDevice camera) {//关闭摄像头
            if (null != mCameraDevice) {
                mCameraDevice.close();
                ExamActivity.this.mCameraDevice = null;
            }
        }

        @Override
        public void onError(CameraDevice camera, int error) {//发生错误
            Toast.makeText(ExamActivity.this, "摄像头开启失败", Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * 开始预览
     */
    private void takePreview() {
        try {
            // 创建预览需要的CaptureRequest.Builder
            final CaptureRequest.Builder previewRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            // 将SurfaceView的surface作为CaptureRequest.Builder的目标
            previewRequestBuilder.addTarget(mSurfaceHolder.getSurface());
            // 创建CameraCaptureSession，该对象负责管理处理预览请求和拍照请求
            mCameraDevice.createCaptureSession(Arrays.asList(mSurfaceHolder.getSurface(), mImageReader.getSurface()), new CameraCaptureSession.StateCallback() // ③
            {
                @Override
                public void onConfigured(CameraCaptureSession cameraCaptureSession) {
                    if (null == mCameraDevice) return;
                    // 当摄像头已经准备好时，开始显示预览
                    mCameraCaptureSession = cameraCaptureSession;
                    try {
                        // 自动对焦
                        previewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
                        // 打开闪光灯
                        previewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
                        // 显示预览
                        CaptureRequest previewRequest = previewRequestBuilder.build();
                        mCameraCaptureSession.setRepeatingRequest(previewRequest, null, childHandler);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onConfigureFailed(CameraCaptureSession cameraCaptureSession) {
                    Toast.makeText(ExamActivity.this, "配置失败", Toast.LENGTH_SHORT).show();
                }
            }, childHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    //以上是摄像头相关


}
