package ru.visualmath.android.api.model

import com.google.gson.annotations.SerializedName

data class QuestionAnswer(

        @SerializedName("stats")
        val stats: Stats,

        @SerializedName("questionId")
        val questionId: String,

        @SerializedName("ended")
        val isEnded: Boolean,

        @SerializedName("activeQuestionId")
        val activeQuestionId: String,

        @SerializedName("didCurrentUserAnswer")
        val isDidCurrentUserAnswer: Boolean,

        @SerializedName("userAnsweredCorrectly")
        val isUserAnsweredCorrectly: Boolean
)
