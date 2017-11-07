package ru.visualmath.android.api.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class Lecture(

        @SerializedName("_id")
        val id: String,

        @SerializedName("name")
        val name: String,

        @SerializedName("mapping")
        val mapping: List<Page>,

        @SerializedName("modules")
        val modules: List<Module>,

        @SerializedName("questions")
        val questions: List<Question>,

        @SerializedName("questionBlocks")
        val questionBlocks: List<String>,

        @SerializedName("hidden")
        val isHidden: Boolean,

        @SerializedName("created")
        private val created: String
) : Parcelable {

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
