package ru.visualmath.android.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class UserInfo {

    @SerializedName("questionAnswers")
    private Map<String, QuestionAnswer> questionAnswers;

    public Map<String, QuestionAnswer> getQuestionAnswers() {
        return questionAnswers;
    }
}
