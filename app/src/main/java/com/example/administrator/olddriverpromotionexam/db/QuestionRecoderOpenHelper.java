package com.example.administrator.olddriverpromotionexam.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.olddriverpromotionexam.bean.QuestionRecoder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/12 0012.
 */

public class QuestionRecoderOpenHelper {

    private static MyDatabaseOpenHelper dbHelper;

    public static void saveQuestionRecoder(Context context, QuestionRecoder recoder){
        String sql = "insert into coder(grade, timeConsum, date) values(?, ?, ?);";
        if(dbHelper == null){
            dbHelper = new MyDatabaseOpenHelper(context);
        }
        SQLiteDatabase writer = dbHelper.getWritableDatabase();
        writer.execSQL(sql,new String[]{recoder.getGrade(), recoder.getTimeConsum(), recoder.getDate()});
        writer.close();
    }

    public static List<QuestionRecoder> getQuestionRecoderList(Context context){
        List<QuestionRecoder> recoderList = new ArrayList<>();
        if(dbHelper == null){
            dbHelper = new MyDatabaseOpenHelper(context);
        }
        SQLiteDatabase reader = dbHelper.getReadableDatabase();
        Cursor cursor = reader.rawQuery("select * from coder;", new String[]{});
        if(cursor != null && cursor.getCount() > 0){
            while(cursor.moveToNext()){
                QuestionRecoder recoder = new QuestionRecoder();
                recoder.setGrade(cursor.getString(cursor.getColumnIndex("grade")));
                recoder.setDate(cursor.getString(cursor.getColumnIndex("date")));
                recoder.setTimeConsum(cursor.getString(cursor.getColumnIndex("timeConsum")));
                recoderList.add(recoder);
            }
        }
        reader.close();
        cursor.close();
        return recoderList;
    }
}
