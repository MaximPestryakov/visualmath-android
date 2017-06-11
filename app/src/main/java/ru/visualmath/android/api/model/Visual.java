package ru.visualmath.android.api.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Visual implements Serializable{


    @SerializedName("_id")
    private String id;

    @SerializedName("name")
    private String name;

    public String getId() {
        return id;
    }

    public String getName() {
        return "test";
    }

    public String getUrl() {
        return "www.google.com";
    }
}
