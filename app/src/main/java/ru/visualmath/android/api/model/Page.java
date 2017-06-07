package ru.visualmath.android.api.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Page implements Serializable {

    @SerializedName("index")
    private int index;

    @SerializedName("type")
    private String type;

    public int getIndex() {
        return index;
    }

    public String getType() {
        return type;
    }
}
