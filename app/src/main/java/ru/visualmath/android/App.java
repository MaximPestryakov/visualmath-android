package ru.visualmath.android;

import android.app.Application;
import android.content.Context;

import ru.visualmath.android.api.VisualMathApi;

public class App extends Application {

    public static App from(Context context) {
        return (App) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        VisualMathApi.init(this);
    }
}
