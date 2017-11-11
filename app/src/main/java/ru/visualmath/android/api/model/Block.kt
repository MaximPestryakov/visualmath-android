package ru.visualmath.android.api.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Block(

        @SerializedName("ended")
        val isEnded: Boolean = false,

        @SerializedName("results")
        val results: Results = Results()
) : Parcelable
