package ru.visualmath.android.api.sync;

import java.util.ArrayList;

public class Question {

    private String question;
    private ArrayList<String> answers;
    private ArrayList<String> correctAnswers;
    private String id;

    public String getQuestion() {
        return question;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public ArrayList<String> getCorrectAnswers() {
        return correctAnswers;
    }

    public String getId() {
        return id;
    }

    public Question(String question, ArrayList<String> answers, ArrayList<String> correctAnswers, String id) {
        this.question = question;
        this.answers = answers;
        this.correctAnswers = correctAnswers;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", answers=" + answers +
                ", correctAnswers=" + correctAnswers +
                '}';
    }
}



