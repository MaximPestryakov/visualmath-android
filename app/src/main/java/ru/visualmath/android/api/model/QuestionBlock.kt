package ru.visualmath.android.api.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class QuestionBlock(

        @SerializedName("_id")
        val id: String,

        @SerializedName("name")
        val name: String,

        @SerializedName("hidden")
        val isHidden: Boolean,

        @SerializedName("questionsIds")
        val questions: List<Question>
) : Parcelable
