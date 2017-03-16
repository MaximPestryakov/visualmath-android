package ru.visualmath.android.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Question {

    @SerializedName("question")
    private String question;

    @SerializedName("multiple")
    private boolean multiple;

    @SerializedName("answers")
    private List<String> answers;

    public String getQuestion() {
        return question;
    }

    public boolean isMultiple() {
        return multiple;
    }

    public List<String> getAnswers() {
        return new ArrayList<>(answers);
    }

    @Override
    public String toString() {
        return "Question{" +
                "question: " + question + ", " +
                "multiple: " + multiple + ", " +
                "answers: " + answers +
                "}";
    }
}
