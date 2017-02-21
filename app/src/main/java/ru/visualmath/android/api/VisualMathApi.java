package ru.visualmath.android.api;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.CookieJar;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.visualmath.android.App;
import ru.visualmath.android.api.model.Lecture;
import ru.visualmath.android.api.model.QuestionBlock;
import ru.visualmath.android.api.model.User;

public class VisualMathApi {

    private static VisualMathApi api = new VisualMathApi();

    private VisualMathService service;

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
        Map<String, String> data = new HashMap<>();
        data.put("name", name);
        data.put("password", password);
        return service.login(data);
    }

    public Observable<List<Lecture>> lecturesList() {
        return service.lecturesList();
    }

    public Observable<QuestionBlock> loadQuestionBlock(String id) {
        Map<String, String> data = new HashMap<>();
        data.put("id", id);
        return service.loadQuestionBlock(data);
    }
}
