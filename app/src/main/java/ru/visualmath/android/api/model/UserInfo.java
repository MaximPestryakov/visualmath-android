package ru.visualmath.android.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class UserInfo {

    @SerializedName("blocks")
    private Map<String, Block> blocks;

    @SerializedName("questionAnswers")
    private Map<String, QuestionAnswer> questionAnswers;

    public Map<String, Block> getBlocks() {
        return blocks;
    }

    public Map<String, QuestionAnswer> getQuestionAnswers() {
        return questionAnswers;
    }
}
