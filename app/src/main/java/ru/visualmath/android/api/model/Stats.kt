package ru.visualmath.android.api.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Stats(

        @SerializedName("votes")
        @Expose
        val votes: List<Int> = emptyList()
)
