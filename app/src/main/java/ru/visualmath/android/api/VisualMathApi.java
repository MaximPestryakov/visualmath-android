package ru.visualmath.android.api;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.CookieJar;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.visualmath.android.App;
import ru.visualmath.android.api.model.Lecture;
import ru.visualmath.android.api.model.LoginParams;
import ru.visualmath.android.api.model.User;

public class VisualMathApi {

    private static VisualMathApi api = new VisualMathApi();
    VisualMathService service;

    private VisualMathApi() {
        CookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(),
                new SharedPrefsCookiePersistor(App.getContext()));

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://visualmath.ru/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        service = retrofit.create(VisualMathService.class);
    }

    public static VisualMathApi getApi() {
        return api;
    }

    public Observable<User> login(String name, String password) {
        return service.login(new LoginParams(name, password));
    }

    public Observable<List<Lecture>> lecturesList() {
        return service.lecturesList();
    }
}
