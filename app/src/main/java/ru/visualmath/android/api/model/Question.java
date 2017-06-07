package ru.visualmath.android.api.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Question implements Serializable {

    @SerializedName("_id")
    private String id;

    @SerializedName("question")
    private String title;

    @SerializedName("multiple")
    private boolean multiple;

    @SerializedName("answers")
    private List<String> answers;

    @SerializedName("isAnswerSymbolic")
    private boolean symbolic;

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

    public boolean isSymbolic() {
        return symbolic;
    }
}
