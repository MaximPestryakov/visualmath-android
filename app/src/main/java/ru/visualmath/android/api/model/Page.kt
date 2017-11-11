package ru.visualmath.android.api.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Page(

        @SerializedName("index")
        val index: Int = 0,

        @SerializedName("type")
        val type: String = ""
) : Parcelable
