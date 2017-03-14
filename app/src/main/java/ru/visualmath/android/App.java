package ru.visualmath.android;

import android.app.Application;
import android.content.Context;

import ru.visualmath.android.api.VisualMathApi;

public class App extends Application {

    private VisualMathApi api;

    public static App from(Context context) {
        return (App) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        api = new VisualMathApi(this);
    }

    public VisualMathApi getApi() {
        return api;
    }
}
