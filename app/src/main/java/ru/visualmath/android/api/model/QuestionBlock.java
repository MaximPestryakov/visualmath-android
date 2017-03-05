package ru.visualmath.android.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuestionBlock {

    public String name;

    public boolean hidden;

    @SerializedName("questionsIds")
    public List<Lecture.Question> questions;
}
