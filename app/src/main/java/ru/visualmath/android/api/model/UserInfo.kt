package ru.visualmath.android.api.model

import com.google.gson.annotations.SerializedName

data class UserInfo(

        @SerializedName("blocks")
        val blocks: Map<String, Block> = emptyMap(),

        @SerializedName("questionAnswers")
        val questionAnswers: Map<String, QuestionAnswer> = emptyMap()
)
