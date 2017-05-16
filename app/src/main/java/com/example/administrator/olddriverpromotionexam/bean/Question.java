package com.example.administrator.olddriverpromotionexam.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/9 0009.
 */
public class Question implements Parcelable {
    private String id;
    private String content;
    private String type;
    private List<String> options;
    private String correctAnswer;
    private String analysis;
    private List<String> knowledgePoints;
    private List<String>  questionsAndAnswers;

    public Question() {
        options = new ArrayList<>();
        knowledgePoints= new ArrayList<>();
        questionsAndAnswers= new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Question{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", type='" + type + '\'' +
                ", options=" + options +
                ", correctAnswer='" + correctAnswer + '\'' +
                ", analysis='" + analysis + '\'' +
                ", knowledgePoints=" + knowledgePoints +
                ", questionsAndAnswers=" + questionsAndAnswers +
                '}';
    }

    public List<String> getOptions() {
        return options;
    }

    public List<String> getKnowledgePoints() {
        return knowledgePoints;
    }

    public List<String> getQuestionsAndAnswers() {
        return questionsAndAnswers;
    }

    public String getId() {
        return id;
    }

    public void addOption(String option){
        options.add(option);
    }

    public void addknowledgePoint(String knowledgePoint){
        knowledgePoints.add(knowledgePoint);
    }

    public void addquestionsAndAnswers(String questionsAndAnswer){
        questionsAndAnswers.add(questionsAndAnswer);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.content);
        dest.writeString(this.type);
        dest.writeStringList(this.options);
        dest.writeString(this.correctAnswer);
        dest.writeString(this.analysis);
        dest.writeStringList(this.knowledgePoints);
        dest.writeStringList(this.questionsAndAnswers);
    }

    protected Question(Parcel in) {
        this.id = in.readString();
        this.content = in.readString();
        this.type = in.readString();
        this.options = in.createStringArrayList();
        this.correctAnswer = in.readString();
        this.analysis = in.readString();
        this.knowledgePoints = in.createStringArrayList();
        this.questionsAndAnswers = in.createStringArrayList();
    }

    public static final Parcelable.Creator<Question> CREATOR = new Parcelable.Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel source) {
            return new Question(source);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };
}

