package com.example.administrator.olddriverpromotionexam.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.administrator.olddriverpromotionexam.bean.User;

/**
 * Created by Administrator on 2017/5/13 0013.
 */

public class UserHelper {
    private static MyDatabaseOpenHelper databaseOpenHelper;

    public static User login(Context context, String username, String password){
        if(databaseOpenHelper == null){
            databaseOpenHelper = new MyDatabaseOpenHelper(context);
        }
        User user = null;
        SQLiteDatabase reader = databaseOpenHelper.getReadableDatabase();
        String sql = "select * from user where username = ? and password = ?;";
        Cursor cursor = reader.rawQuery(sql, new String[]{username, password});
        if(cursor.moveToNext()){
            user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setMail(cursor.getString(cursor.getColumnIndex("mail")));
            user.setModels(cursor.getString(cursor.getColumnIndex("models")));
            user.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
            user.setProvince(cursor.getString(cursor.getColumnIndex("province")));
        }
        cursor.close();
        reader.close();
        return user;
    }

    public static boolean hasUser(Context context, String username){
        if(databaseOpenHelper == null){
            databaseOpenHelper = new MyDatabaseOpenHelper(context);
        }
        SQLiteDatabase reader = databaseOpenHelper.getReadableDatabase();
        String sql = "select * from user where username = ?;";
        Cursor cursor = reader.rawQuery(sql, new String[]{username});
        boolean hasUser = false;
        if(cursor.moveToNext()){
            hasUser = true;
        }
        cursor.close();
        reader.close();
        return hasUser;
    }

    public static void registe(Context context, String username, String password){
        if(databaseOpenHelper == null){
            databaseOpenHelper = new MyDatabaseOpenHelper(context);
        }
        SQLiteDatabase writer = databaseOpenHelper.getWritableDatabase();
        String sql = "insert into user(username, password) values(?, ?);";
        writer.execSQL(sql, new String[]{username, password});
        writer.close();
    }

    public static void updateUser(Context context, User user) {
        if(databaseOpenHelper == null){
            databaseOpenHelper = new MyDatabaseOpenHelper(context);
        }
        SQLiteDatabase writer = databaseOpenHelper.getWritableDatabase();
        String sql = "update user set password=?,mail=?,models=?,phone=?,province=? where username = ?;";
        writer.execSQL(sql, new String[]{user.getPassword(),user.getMail(),user.getModels(),user.getPhone(),user.getProvince(),user.getUsername()});
        writer.close();
    }

    public static void debug(Context context){
        if(databaseOpenHelper == null){
            databaseOpenHelper = new MyDatabaseOpenHelper(context);
        }
        User user = null;
        SQLiteDatabase reader = databaseOpenHelper.getReadableDatabase();
        String sql = "select * from user;";
        Cursor cursor = reader.rawQuery(sql, new String[]{});
        while(cursor.moveToNext()){
            user = new User();
            user.setUsername(cursor.getString(cursor.getColumnIndex("username")));
            user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            user.setMail(cursor.getString(cursor.getColumnIndex("mail")));
            user.setModels(cursor.getString(cursor.getColumnIndex("models")));
            user.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
            user.setProvince(cursor.getString(cursor.getColumnIndex("province")));
            Log.i("fuck", user.toString());
        }
        cursor.close();
        reader.close();
    }
}
