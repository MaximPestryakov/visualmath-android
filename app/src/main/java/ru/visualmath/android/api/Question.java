package ru.visualmath.android.api;

import java.util.ArrayList;

public class Question {
    private String question;
    private ArrayList<String> answers;

    public Question(String question, ArrayList<String> answers) {
        this.question = question;
        this.answers = answers;
    }

    public String toString() {
        return this.question + '\n' + this.answers;
    }
}
