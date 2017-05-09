package ru.visualmath.android.api.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Module implements Serializable {

    @SerializedName("name")
    private String name;

    @SerializedName("content")
    private String content;

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Module{" +
                "name: " + name + ", " +
                "content: " + content +
                "}";
    }
}
