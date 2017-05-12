package ru.visualmath.android.api.model;

import com.google.gson.annotations.SerializedName;

public class SlideInfo {

    @SerializedName("type")
    private SlideType type;

    public SlideType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "SlideInfo{" +
                "type: " + type.name() +
                "}";
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
