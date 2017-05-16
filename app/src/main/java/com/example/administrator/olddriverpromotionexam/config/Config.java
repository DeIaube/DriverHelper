package com.example.administrator.olddriverpromotionexam.config;

import com.example.administrator.olddriverpromotionexam.util.Sp;

/**
 * Created by Administrator on 2017/5/9 0009.
 */

public class Config {
    private static int maxExamQuestions;
    private static int maxExercisesQuestions;

    public static final int PRACTICE_MODE = 0;
    public static final int RECITE_MODE = 1;
    public static final int EXAM_MODE = 1;

    public static final int ORDER_PRACTICE = 0;
    public static final int RANDOM_PRACTICE = 1;
    public static final int KNOWLEDGE_POINT_PRACTICE = 2;
    public static final int CLASSIFICATION_PRACTICE = 3;
    public static final int WRONG_PRACTICE = 4;
    public static final int COLLECTION_PRACTICE = 5;

    public static final String FINGERPRINT = "fingerprint";
    public static final String FACE = "face";

    public static String ORDER_PRICATICE_INDEX = "order";

    private static String MAX_EXAM_QUESTIONS = "max_exam_questions";
    private static String MAX_EXERCISES_QUESTIONS = "max_exercises_questions";


    public static void init(){
        maxExamQuestions = Sp.get(MAX_EXAM_QUESTIONS, 100);
        maxExercisesQuestions = Sp.get(MAX_EXERCISES_QUESTIONS, 10);
    }

    public static int getMaxExamQuestions() {
        return maxExamQuestions;
    }

    public static int getMaxExercisesQuestions() {
        return maxExercisesQuestions;
    }

    public static void setMaxExamQuestions(int maxExamQuestions) {
        Config.maxExamQuestions = maxExamQuestions;
        Sp.put(MAX_EXAM_QUESTIONS, maxExamQuestions);
    }

    public static void setMaxExercisesQuestions(int maxExercisesQuestions) {
        Config.maxExercisesQuestions = maxExercisesQuestions;
        Sp.put(MAX_EXERCISES_QUESTIONS, maxExercisesQuestions);
    }

}
