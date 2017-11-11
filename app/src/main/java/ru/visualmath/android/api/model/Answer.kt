package ru.visualmath.android.api.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Answer(

        @SerializedName("correct")
        val correct: List<Int> = emptyList(),

        @SerializedName("given")
        val given: List<Int> = emptyList(),

        @SerializedName("mark")
        val mark: Double = 0.0
) : Parcelable
