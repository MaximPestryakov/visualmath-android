package ru.visualmath.android.api.model;

import com.google.gson.annotations.SerializedName;

public class User {

    String username;

    String role;

    @SerializedName("last_name")
    String lastName;

    @SerializedName("first_name")
    String firstName;

    String token;

    public String getUsername() {
        return username;
    }
}