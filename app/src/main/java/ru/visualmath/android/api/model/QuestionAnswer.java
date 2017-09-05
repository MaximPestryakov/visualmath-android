package ru.visualmath.android.api.model;

import com.google.gson.annotations.SerializedName;

public class QuestionAnswer {

    @SerializedName("stats")
    private Stats stats;

    @SerializedName("questionId")
    private String questionId;

    @SerializedName("ended")
    private boolean ended;

    @SerializedName("activeQuestionId")
    private String activeQuestionId;

    @SerializedName("didCurrentUserAnswer")
    private boolean didCurrentUserAnswer;

    @SerializedName("userAnsweredCorrectly")
    private boolean userAnsweredCorrectly;

    public Stats getStats() {
        return stats;
    }

    public String getQuestionId() {
        return questionId;
    }

    public boolean isEnded() {
        return ended;
    }

    public String getActiveQuestionId() {
        return activeQuestionId;
    }

    public boolean isDidCurrentUserAnswer() {
        return didCurrentUserAnswer;
    }

    public boolean isUserAnsweredCorrectly() {
        return userAnsweredCorrectly;
    }
}
