package ru.visualmath.android.api.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Visual implements Serializable{
    @SerializedName("content")
    private Content content;

    public String getId() {
        return content.getId();
    }

    public String getName() {
        return content.getName();
    }

    public String getDescription() {
        return content.getDescription();
    }
}
