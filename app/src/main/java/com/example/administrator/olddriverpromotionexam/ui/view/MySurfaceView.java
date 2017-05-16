package com.example.administrator.olddriverpromotionexam.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;

/**
 * Created by Administrator on 2017/5/16 0016.
 */

public class MySurfaceView extends SurfaceView {

    private int lastX;
    private int lastY;

    public MySurfaceView(Context context) {
        super(context);
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 获取当前触摸的绝对坐标
        int rawX = (int) event.getRawX();
        int rawY = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 上一次离开时的坐标
                lastX = rawX;
                lastY = rawY;
                break;
            case MotionEvent.ACTION_MOVE:
                // 两次的偏移量
                int offsetX = rawX - lastX;
                int offsetY = rawY - lastY;
                moveView(offsetX, offsetY);
                // 不断修改上次移动完成后坐标
                lastX = rawX;
                lastY = rawY;
                break;
            default:
                break;
        }
        return true;
    }

    private void moveView(int offsetX, int offsetY) {
         offsetLeftAndRight(offsetX);
         offsetTopAndBottom(offsetY);
    }
}
