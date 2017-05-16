package com.example.administrator.olddriverpromotionexam.util;

/**
 * Created by Administrator on 2017/5/10 0010.
 */

public final class CollectionUtil {

    public static void changeCollection(String id){
        boolean isCollection = isCollection(id);
        if(isCollection){
            QuestionBank.getDefault().removeCollection(id);
        }else{
            QuestionBank.getDefault().addCollection(id);
        }
    }

    public static boolean isCollection(String id){
        return QuestionBank.getDefault().isCollection(id);
    }

}
