package ru.visualmath.android.api.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Question(

        @SerializedName("_id")
        val id: String = "",

        @SerializedName("question")
        val title: String = "",

        @SerializedName("multiple")
        val isMultiple: Boolean = false,

        @SerializedName("answers")
        val answers: List<String> = emptyList(),

        @SerializedName("isAnswerSymbolic")
        val isSymbolic: Boolean = false
) : Parcelable
