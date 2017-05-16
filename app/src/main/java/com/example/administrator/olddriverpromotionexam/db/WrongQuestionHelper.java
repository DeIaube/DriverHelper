package com.example.administrator.olddriverpromotionexam.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2017/5/10 0010.
 */

public class WrongQuestionHelper {

    private static MyDatabaseOpenHelper dbHelper;

    public static void saveWrongQuestion(Context context, Set<String> wrongList){
        SQLiteDatabase writer = dbHelper.getWritableDatabase();
        Set<String> rawData = loadWrongQuestion(context);
        for (String s : rawData) {
            wrongList.remove(s);
        }
        String sql = "insert into wrong(id) values(?);";
        for (String s : wrongList) {
            writer.execSQL(sql,new String[]{s});
        }
        writer.close();
    }

    public static Set<String> loadWrongQuestion(Context context){
        if(dbHelper == null){
            dbHelper = new MyDatabaseOpenHelper(context);
        }
        SQLiteDatabase reader = dbHelper.getReadableDatabase();
        Set<String> resule = new HashSet<>();
        Cursor cursor = reader.rawQuery("select id from wrong;", new String[]{});
        if(cursor != null && cursor.getCount() > 0){
            while(cursor.moveToNext()){
                resule.add(cursor.getString(cursor.getColumnIndex("id")));
            }
        }
        reader.close();
        cursor.close();
        return resule;
    }
}
