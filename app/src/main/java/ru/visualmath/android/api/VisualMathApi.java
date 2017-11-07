package ru.visualmath.android.api;

import android.content.Context;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import okhttp3.CookieJar;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.visualmath.android.App;
import ru.visualmath.android.api.model.Lecture;
import ru.visualmath.android.api.model.QuestionBlock;
import ru.visualmath.android.api.model.SyncLecture;
import ru.visualmath.android.api.model.User;
import ru.visualmath.android.api.model.UserInfo;

public class VisualMathApi {

    private static VisualMathApi api;
    private static Picasso picasso;
    private VisualMathService service;

    private VisualMathApi(Context context) {
        CookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(VisualMathService.URL)
                .addConverterFactory(GsonConverterFactory.create(App.getGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        service = retrofit.create(VisualMathService.class);

        picasso = new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(okHttpClient))
                .build();
    }

    public static void init(Context context) {
        api = new VisualMathApi(context);
    }

    public static VisualMathApi getApi() {
        return api;
    }

    public static Picasso getPicasso() {
        return picasso;
    }

    public Single<User> login(String name, String password) {
        Map<String, String> data = new HashMap<>();
        data.put("name", name);
        data.put("password", password);
        return service.login(data);
    }

    public Single<List<Lecture>> lecturesList() {
        return service.lecturesList();
    }

    public Single<List<SyncLecture>> syncLecturesList() {
        return service.syncLectureList();
    }

    public Single<QuestionBlock> loadQuestionBlock(String id) {
        Map<String, String> data = new HashMap<>();
        data.put("id", id);
        return service.loadQuestionBlock(data);
    }

    public Single<User> createUser(String login, String password, String institution, String group) {
        Map<String, String> data = new HashMap<>();
        data.put("login", login);
        data.put("password", password);
        data.put("institution", institution);
        data.put("group", group);
        data.put("access", "student");
        return service.createUser(data);
    }

    public Single<ResponseBody> loadSyncLecture(String lectureId) {
        Map<String, String> data = new HashMap<>();
        data.put("activeLectureId", lectureId);
        return service.loadSyncLecture(data);
    }

    public Single<ResponseBody> loadSyncSlide(String lectureId) {
        Map<String, String> data = new HashMap<>();
        data.put("activeLectureId", lectureId);
        return service.loadSyncSlide(data);
    }

    public Single<ResponseBody> answerQuestion(String lectureId, List answer, String questionId) {
        Map<String, Object> data = new HashMap<>();
        data.put("activeLectureId", lectureId);
        data.put("answer", answer);
        data.put("questionId", questionId);
        return service.answerQuestion(data);
    }

    public Single<ResponseBody> answerBlock(String lectureId, List answer, String blockId, String questionId) {
        Map<String, Object> data = new HashMap<>();
        data.put("activeLectureId", lectureId);
        data.put("answer", answer);
        data.put("blockId", blockId);
        data.put("questionId", questionId);
        return service.answerBlock(data);
    }

    public Single<UserInfo> userInfo(String lectureId) {
        Map<String, Object> data = new HashMap<>();
        data.put("activeLectureId", lectureId);
        return service.userInfo(data);
    }
}
