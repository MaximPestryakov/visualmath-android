package ru.visualmath.android.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Question {

    @SerializedName("_id")
    private String id;

    @SerializedName("question")
    private String title;

    @SerializedName("multiple")
    private boolean multiple;

    @SerializedName("answers")
    private List<String> answers;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
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
                "title: " + title + ", " +
                "multiple: " + multiple + ", " +
                "answers: " + answers +
                "}";
    }
}
