package com.example.administrator.olddriverpromotionexam.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/5/10 0010.
 */

public class MyDatabaseOpenHelper extends SQLiteOpenHelper {

    public static final String CREATE_WRONG_QUESTIONS = "create table wrong (_id integer primary key autoincrement, id varchar(20))";
    public static final String CREATE_QUESTIONS_CODER = "create table coder (_id integer primary key autoincrement, grade integer, timeConsum varchar(20), date varchar(20))";
    public static final String CREATE_COLLECTION_QUESTIONS = "create table collection (_id integer primary key autoincrement, id varchar(20))";
    public static final String CREATE_USER = "create table user (username varchar(20) primary key, password varchar(20), mail varchar(40), phone varchar(20), province varchar(20), models varchar(20))";
    public MyDatabaseOpenHelper(Context context) {
        super(context, context.getPackageName(), null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_COLLECTION_QUESTIONS);
        db.execSQL(CREATE_WRONG_QUESTIONS);
        db.execSQL(CREATE_QUESTIONS_CODER);
        db.execSQL(CREATE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
