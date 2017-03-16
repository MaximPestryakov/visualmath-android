package ru.visualmath.android.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuestionBlock {

    @SerializedName("name")
    private String name;

    @SerializedName("hidden")
    private boolean hidden;

    @SerializedName("questionsIds")
    private List<Question> questions;

    @Override
    public String toString() {
        return "QuestionBlock{" +
                "name: " + name + ", " +
                "hidden: " + hidden + ", " +
                "questions: " + questions +
                "}";
    }
}
