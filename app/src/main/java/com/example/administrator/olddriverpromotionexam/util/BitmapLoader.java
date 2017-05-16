package com.example.administrator.olddriverpromotionexam.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;

/**
 * Created by Administrator on 2017/5/10 0010.
 */

public final class BitmapLoader {
    public static Bitmap getBitmap(Context context, String name){
        try {
            return BitmapFactory.decodeStream(context.getResources().getAssets().open(name + ".jpg"));
        } catch (IOException e) {
            return null;
        }
    }
}
