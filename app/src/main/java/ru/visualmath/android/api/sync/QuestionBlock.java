package ru.visualmath.android.api.sync;

import java.util.ArrayList;

public class QuestionBlock {
    private String id;
    private String name;
    private ArrayList<Question> questions;


    public QuestionBlock(String name, String id) {
        this.questions = new ArrayList<>();
        this.name = name;
        this.id =  id;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }


    @Override
    public String toString() {
        return "QuestionBlock{" +
                "name='" + name + '\'' +
                ", questions=" + questions +
                '}';
    }


}
