package ru.visualmath.android.api.model

import com.google.gson.annotations.SerializedName
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

data class SyncLecture(

        @SerializedName("_id")
        val id: String,

        @SerializedName("ongoingId")
        val ongoingId: String,

        @SerializedName("isSpeaker")
        val isSpeaker: Boolean,

        @SerializedName("isOngoing")
        val isOngoing: Boolean,

        @SerializedName("lecture")
        val lecture: String,

        @SerializedName("name")
        val name: String,

        @SerializedName("created")
        private val created: String
) {
    val createdDate: Date
        get() {
            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
            var date = Date(0)
            try {
                date = format.parse(created)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return date
        }
}
