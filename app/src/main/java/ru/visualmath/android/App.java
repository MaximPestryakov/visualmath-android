package ru.visualmath.android;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ru.visualmath.android.api.VisualMathApi;
import ru.visualmath.android.api.model.QuestionBlockSlide;

public class App extends Application {

    private static Gson gson;

    public static App from(Context context) {
        return (App) context.getApplicationContext();
    }

    public static Gson getGson() {
        return gson;
    }

    @Override
    public void onCreate() {
        super.onCreate();


        gson = new GsonBuilder()
                .registerTypeAdapter(QuestionBlockSlide.class, new QuestionBlockSlide.Deserializer())
                .create();

        VisualMathApi.init(this);
    }
}
