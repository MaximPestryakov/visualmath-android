package ru.visualmath.android.api.model

import com.google.gson.annotations.SerializedName

data class SlideInfo(

        @SerializedName("type")
        val type: SlideType
) {

    enum class SlideType {
        @SerializedName("slide")
        MODULE,

        @SerializedName("question")
        QUESTION,

        @SerializedName("questionBlock")
        QUESTION_BLOCK,

        @SerializedName("visual")
        VISUAL
    }
}
