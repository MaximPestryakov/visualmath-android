package ru.visualmath.android.api.model

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName

import org.json.JSONException
import org.json.JSONObject

import java.lang.reflect.Type

data class QuestionBlockSlide(

        @SerializedName("questionBlock")
        var questionBlock: QuestionBlock,

        @SerializedName("index")
        var index: Int,

        @SerializedName("started")
        var isStarted: Boolean
) {
    class Deserializer : JsonDeserializer<QuestionBlockSlide?> {

        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): QuestionBlockSlide? {
            var slide: QuestionBlockSlide? = null

            try {
                val jsonObject = JSONObject(json.toString())

                val contentJson = jsonObject.getJSONObject("content").toString()
                val questionBlock = Gson().fromJson(contentJson, QuestionBlock::class.java)
                var index = 0
                var isStarted = !jsonObject.isNull("activeContent")
                if (isStarted) {
                    val activeContent = jsonObject.getJSONObject("activeContent")
                    isStarted = !activeContent.getBoolean("ended")
                    val currentQuestion = activeContent.optString("currentQuestion")
                    index = if ("done" == currentQuestion) -1 else Gson().fromJson(currentQuestion, Int::class.java)
                }
                slide = QuestionBlockSlide(questionBlock, index, isStarted)
            } catch (ignored: JSONException) {
            }

            return slide
        }
    }
}
