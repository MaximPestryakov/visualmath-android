package ru.visualmath.android.api.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Answer(

        @SerializedName("correct")
        val correct: List<Int>,

        @SerializedName("given")
        val given: List<Int>,

        @SerializedName("mark")
        val mark: Double
) : Parcelable
