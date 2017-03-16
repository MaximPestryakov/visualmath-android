package ru.visualmath.android.api.model;

import com.google.gson.annotations.SerializedName;

public class SlideInfo {

    @SerializedName("index")
    private int index;

    @SerializedName("type")
    private SlideType type;

    public int getIndex() {
        return index;
    }

    public SlideType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "SlideInfo{" +
                "index: " + index + ", " +
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
