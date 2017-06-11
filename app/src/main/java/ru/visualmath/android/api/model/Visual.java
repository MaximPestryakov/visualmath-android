package ru.visualmath.android.api.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Visual implements Serializable{
    @SerializedName("_id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("visuals")
    private List<String> urls;

    public String getId() {
        return id;
    }

    public String getName() {
        return "test";
    }

    public String getUrl() {
        //return urls.get(0);
        return "https://www.google.com";
    }
}
