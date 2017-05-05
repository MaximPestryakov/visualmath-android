package ru.visualmath.android.api;

import android.content.Context;

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
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.visualmath.android.api.model.AnswerRequest;
import ru.visualmath.android.api.model.Lecture;
import ru.visualmath.android.api.model.QuestionBlock;
import ru.visualmath.android.api.model.SyncLecture;
import ru.visualmath.android.api.model.User;

public class VisualMathApi {

    private static VisualMathApi api;
    private VisualMathService service;

    private VisualMathApi(Context context) {
        CookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(),
                new SharedPrefsCookiePersistor(context));

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

    public static void init(Context context) {
        api = new VisualMathApi(context);
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

    public Observable<List<SyncLecture>> syncLecturesList() {
        return service.syncLectureList();
    }

    public Observable<QuestionBlock> loadQuestionBlock(String id) {
        Map<String, String> data = new HashMap<>();
        data.put("id", id);
        return service.loadQuestionBlock(data);
    }

    public Observable<User> createUser(String login, String password, String institution, String group) {
        Map<String, String> data = new HashMap<>();
        data.put("login", login);
        data.put("password", password);
        data.put("institution", institution);
        data.put("group", group);
        data.put("access", "student");
        return service.createUser(data);
    }

    public Observable<ResponseBody> loadSyncSlide(String activeLectureId) {
        Map<String, String> data = new HashMap<>();
        data.put("activeLectureId", activeLectureId);
        return service.loadSyncSlide(data);
    }

    public Observable<AnswerRequest> answerQuestion(String activeLectureId, List<Integer> answer, String questionId) {
        Map<String, Object> data = new HashMap<>();
        data.put("activeLectureId", activeLectureId);
        data.put("answer", answer);
        data.put("questionId", questionId);
        return service.answerQuestion(data);
    }
}
