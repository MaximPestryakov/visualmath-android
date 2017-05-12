package ru.visualmath.android.api.model;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

public class QuestionBlockSlide {

    private QuestionBlock questionBlock;

    private int index;

    private boolean started;

    public QuestionBlock getQuestionBlock() {
        return questionBlock;
    }

    public int getIndex() {
        return index;
    }

    public boolean isStarted() {
        return started;
    }

    public static class Deserializer implements JsonDeserializer<QuestionBlockSlide> {

        @Override
        public QuestionBlockSlide deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
            QuestionBlockSlide slide = new QuestionBlockSlide();

            try {
                JSONObject jsonObject = new JSONObject(json.toString());

                String contentJson = jsonObject.getJSONObject("content").toString();
                slide.questionBlock = new Gson().fromJson(contentJson, QuestionBlock.class);
                slide.started = !jsonObject.isNull("activeContent");
                if (slide.started) {
                    JSONObject activeContent = jsonObject.getJSONObject("activeContent");
                    slide.started = !activeContent.getBoolean("ended");
                    String currentQuestion = activeContent.optString("currentQuestion");
                    if ("done".equals(currentQuestion)) {
                        slide.index = -1;
                    } else {
                        slide.index = new Gson().fromJson(currentQuestion, Integer.class);
                    }
                }
            } catch (JSONException ignored) {
            }
            return slide;
        }
    }
}
