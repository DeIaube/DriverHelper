package com.example.administrator.olddriverpromotionexam.util;

import android.content.Context;
import android.util.Log;

import com.example.administrator.olddriverpromotionexam.App;
import com.example.administrator.olddriverpromotionexam.bean.Question;
import com.example.administrator.olddriverpromotionexam.db.CollectionQuestionHelper;
import com.example.administrator.olddriverpromotionexam.db.WrongQuestionHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * Created by Administrator on 2017/5/9 0009.
 */
public final class QuestionBank {

    private List<Question> allQuestion;
    private List<Question> allRabdomQuestion;
    private List<Question> allWrongQuestion;
    private List<Question> allCollectionQuestion;


    private Map<String, Question> quickMap;


    private Map<String, List<Question>> knowledgePointMap;
    private Map<String, List<Question>> questionsAndAnswerMap;



    private Set<String> wrongList;
    private Set<String> collectionList;




    public List<Question> getAllQuestion() {
        return allQuestion;
    }

    public Map<String, List<Question>> getKnowledgePointMap() {
        return knowledgePointMap;
    }

    public Map<String, List<Question>> getQuestionsAndAnswerMap() {
        return questionsAndAnswerMap;
    }

    public Map<String, Question> getQuickMap() {
        return quickMap;
    }

    private QuestionBank() {
        allQuestion = new ArrayList<>();
        knowledgePointMap = new HashMap<>();
        questionsAndAnswerMap = new HashMap<>();
        allRabdomQuestion = new ArrayList<>();
        quickMap = new HashMap<>();
        allWrongQuestion = new ArrayList<>();
        allCollectionQuestion = new ArrayList<>();
    }

    public static QuestionBank getDefault(){
        return QuestionBankHolder.questionBank;
    }

    public boolean isWrong(String id){
        return wrongList.contains(id);
    }

    public boolean isCollection(String id){
        return collectionList.contains(id);
    }

    public void addCollection(String id){
        collectionList.add(id);
        if(!allCollectionQuestion.contains(quickMap.get(id))){
            allCollectionQuestion.add(quickMap.get(id));
        }
    }

    public List<Question> getAllCollectionQuestion() {
        List<Question> copyList = new ArrayList<>();
        for (Question question : allCollectionQuestion) {
            copyList.add(question);
        }
        return copyList;
    }

    public List<Question> getAllWrongQuestion() {
        return allWrongQuestion;
    }

    public void addWrong(String id){
        wrongList.add(id);
        allWrongQuestion.add(quickMap.get(id));
    }

    public void removeCollection(String id){
        collectionList.remove(id);
        allCollectionQuestion.remove(quickMap.get(id));
    }

    private static class QuestionBankHolder{
        public static QuestionBank questionBank = new QuestionBank();
    }

    public List<Question> getAllRabdomQuestion(int length) {
        Collections.shuffle(allRabdomQuestion, new Random());
        List<Question> resule = new ArrayList<>();
        for(int i = 0; i < length; i++){
            resule.add(allRabdomQuestion.get(i));
        }
        return resule;
    }

    public void init(Context context, String fileName){
        if(fileName == null || fileName.isEmpty()){
            fileName = "题库.xls";
        }
        Workbook wb = null;
        try {
            wb = Workbook.getWorkbook(context.getAssets().open(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
        Sheet sheet = wb.getSheet(0);
        int n = sheet.getRows();

        Question question = null;
        for(int i = 1; i < n;i++){
            question = getQuestion(i,sheet);
            initQuestionList(question);
        }

        initWrongAndCollection();
    }

    private void initWrongAndCollection() {
        wrongList = WrongQuestionHelper.loadWrongQuestion(App.getAppContext());
        collectionList = CollectionQuestionHelper.loadCollectionQuestion(App.getAppContext());

        allWrongQuestion.clear();
        allCollectionQuestion.clear();

        for (String s : wrongList) {
            Log.i("fuck", s);
            allWrongQuestion.add(quickMap.get(s));
        }
        Log.i("fuck", allWrongQuestion.toString());
        for (String s : collectionList) {
            allCollectionQuestion.add(quickMap.get(s));
        }
    }

    private void initQuestionList(Question question) {
        allQuestion.add(question);
        allRabdomQuestion.add(question);
        quickMap.put(question.getId(), question);

        for (String key : question.getKnowledgePoints()) {
            if(key.isEmpty()){
                continue;
            }
            if(knowledgePointMap.get(key) == null){
                List<Question> questionList = new ArrayList<>();
                questionList.add(question);
                knowledgePointMap.put(key, questionList);
            }else{
                knowledgePointMap.get(key).add(question);
            }
        }

        for (String key : question.getQuestionsAndAnswers()) {
            if(key.isEmpty()){
                continue;
            }
            if(questionsAndAnswerMap.get(key) == null){
                List<Question> questionList = new ArrayList<>();
                questionList.add(question);
                questionsAndAnswerMap.put(key, questionList);
            }else{
                questionsAndAnswerMap.get(key).add(question);
            }
        }

    }


    private static Question getQuestion(int row, Sheet sheet) {
        Question question = new Question();

        //编号
        question.setId(sheet.getCell(1, row).getContents());

        //内容
        String content = sheet.getCell(2,row).getContents();
        if(content.contains("<br/>")){
            content = content.substring(0, content.indexOf("<br/>"));
        }
        question.setContent(content);

        //考试类型 0:选择 3:判断
        question.setType(sheet.getCell(3,row).getContents());

        //选项内容
        if(question.getType().equals("3")){
            //判断题 不需要额外初始化选项
            question.addOption("正确");
            question.addOption("错误");
        }else{
            //选择题 初始化选项
            question.addOption(sheet.getCell(4,row).getContents());
            question.addOption(sheet.getCell(5,row).getContents());
            question.addOption(sheet.getCell(6,row).getContents());
            question.addOption(sheet.getCell(7,row).getContents());
        }

        //正确选项
        question.setCorrectAnswer(sheet.getCell(8,row).getContents());

        //详解
        question.setAnalysis(sheet.getCell(12,row).getContents());

        //试题所属知识点
        String[] knowledgePoints = sheet.getCell(13,row).getContents().split("、");
        for (String knowledgePoint : knowledgePoints) {
            question.addknowledgePoint(knowledgePoint);
        }

        //试题内容（按公安部分类）
        String[] questionsAndAnswers = sheet.getCell(14,row).getContents().split("、");
        for (String questionsAndAnswer : questionsAndAnswers) {
            question.addquestionsAndAnswers(questionsAndAnswer);
        }

        return question;
    }


    public void destory(){
        WrongQuestionHelper.saveWrongQuestion(App.getAppContext(), wrongList);
        CollectionQuestionHelper.saveCollectionQuestion(App.getAppContext(), collectionList);
    }
}
