package ru.visualmath.android.api.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class QuestionBlock implements Serializable {

    @SerializedName("_id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("hidden")
    private boolean hidden;

    @SerializedName("questionsIds")
    private List<Question> questions;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isHidden() {
        return hidden;
    }

    public List<Question> getQuestions() {
        return new ArrayList<>(questions);
    }

    @Override
    public String toString() {
        return "QuestionBlock{" +
                "name: " + name + ", " +
                "hidden: " + hidden + ", " +
                "questions: " + questions +
                "}";
    }
}
