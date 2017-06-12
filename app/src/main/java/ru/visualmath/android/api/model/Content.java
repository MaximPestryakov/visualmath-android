package ru.visualmath.android.api.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Content implements Serializable{


    @SerializedName("_id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
