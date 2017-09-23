package ru.visualmath.android.api.model

import com.google.gson.annotations.SerializedName

data class User(

        @SerializedName("username")
        val username: String,

        @SerializedName("role")
        val role: String,

        @SerializedName("last_name")
        val lastName: String,

        @SerializedName("first_name")
        val firstName: String,

        @SerializedName("token")
        val token: String
)
