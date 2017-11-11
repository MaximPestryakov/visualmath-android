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
        val id: String = "",

        @SerializedName("name")
        val name: String = "",

        @SerializedName("mapping")
        val mapping: List<Page> = emptyList(),

        @SerializedName("modules")
        val modules: List<Module> = emptyList(),

        @SerializedName("questions")
        val questions: List<Question> = emptyList(),

        @SerializedName("questionBlocks")
        val questionBlocks: List<String> = emptyList(),

        @SerializedName("hidden")
        val isHidden: Boolean = false,

        @SerializedName("created")
        private val created: String = ""
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
