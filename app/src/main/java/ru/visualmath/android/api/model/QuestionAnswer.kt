package ru.visualmath.android.api.model

import com.google.gson.annotations.SerializedName

data class QuestionAnswer(

        @SerializedName("stats")
        val stats: Stats = Stats(),

        @SerializedName("questionId")
        val questionId: String = "",

        @SerializedName("ended")
        val isEnded: Boolean = false,

        @SerializedName("activeQuestionId")
        val activeQuestionId: String = "",

        @SerializedName("didCurrentUserAnswer")
        val isDidCurrentUserAnswer: Boolean = false,

        @SerializedName("userAnsweredCorrectly")
        val isUserAnsweredCorrectly: Boolean = false
)
