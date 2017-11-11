package ru.visualmath.android.api.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Module(

        @SerializedName("_id")
        val id: String = "",

        @SerializedName("name")
        val name: String = "",

        @SerializedName("content")
        val content: String = "",

        @SerializedName("images")
        val images: List<String> = emptyList()
) : Parcelable
