package ru.visualmath.android.api.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Results(

        @SerializedName("answers")
        val answers: List<Answer> = emptyList(),

        @SerializedName("sum")
        val sum: Double = 0.0
) : Parcelable
