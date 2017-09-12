package ru.visualmath.android.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Answer {

    @SerializedName("correct")
    private List<Integer> correct;

    @SerializedName("given")
    private List<Integer> given;

    @SerializedName("mark")
    private double mark;

    public List<Integer> getCorrect() {
        return correct;
    }

    public List<Integer> getGiven() {
        return given;
    }

    public double getMark() {
        return mark;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "correct=" + correct +
                ", given=" + given +
                ", mark=" + mark +
                '}';
    }
}
