package ru.visualmath.android.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Stats {

    @SerializedName("votes")
    @Expose
    private List<Integer> votes;

    public List<Integer> getVotes() {
        return votes;
    }
}
