package ru.visualmath.android.api.model;

import com.google.gson.annotations.SerializedName;

public class Block {

    @SerializedName("ended")
    private boolean ended;

    @SerializedName("results")
    private Results results;

    public Results getResults() {
        return results;
    }

    public boolean isEnded() {
        return ended;
    }
}
