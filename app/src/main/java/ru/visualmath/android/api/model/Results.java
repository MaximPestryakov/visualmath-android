package ru.visualmath.android.api.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Results implements Serializable {

    @SerializedName("answers")
    private List<Answer> answers;

    @SerializedName("sum")
    private double sum;

    public List<Answer> getAnswers() {
        return answers;
    }

    public double getSum() {
        return sum;
    }

    @Override
    public String toString() {
        return "Results{" +
                "answers=" + answers +
                ", sum=" + sum +
                '}';
    }
}
