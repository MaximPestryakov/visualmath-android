package ru.visualmath.android.api.model;

import com.google.gson.annotations.SerializedName;

public class SlideInfo {

    @SerializedName("type")
    private SlideType type;

    public SlideType getType() {
        return type;
    }

    public enum SlideType {
        @SerializedName("slide")
        MODULE,

        @SerializedName("question")
        QUESTION,

        @SerializedName("questionBlock")
        QUESTION_BLOCK
    }
}
